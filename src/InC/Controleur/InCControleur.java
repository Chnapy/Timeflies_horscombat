/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Controleur.Listener.OnActionState;
import InC.Controleur.Listener.OnButSAaction;
import InC.Controleur.Listener.OnTileHover;
import InC.Controleur.Listener.OnTilePressed;
import InC.Modele.ActionState;
import static InC.Modele.ActionState.DEPLACEMENT;
import static InC.Modele.ActionState.WAIT;
import InC.Modele.Binding.SortBinding;
import InC.Vue.InCVue;
import Main.Controleur.Controleur;
import InC.Modele.Donnees.Combat;
import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Map;
import InC.Modele.Map.Tuile;
import InC.Modele.Pathfinding.AStarPathFinder;
import InC.Modele.Pathfinding.ClosestHeuristic;
import InC.Modele.Timer.TourTimer;
import InC.Modele.Binding.TuileBinding;
import InC.Modele.Donnees.Envoutement;
import InC.Modele.Timer.PileAction;
import InC.Vue.HUD.Module.Sorts.ButSortActif;
import InC.Vue.HUD.Module.Timeline.RowEntiteTL;
import InC.Vue.Map.VueEntite;
import Main.Vue.DataVue;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.TypeCarac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import InC.Vue.Map.VueTuile;
import static Main.Controleur.MainControleur.EXEC;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.action.AddEnvoutement;
import Serializable.InCombat.action.AlterCarac;
import Serializable.InCombat.action.Invocation;
import Serializable.InCombat.action.Rotation;
import Serializable.InCombat.action.Teleportation;
import Serializable.InCombat.tour.Tour;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.tour.DebutTour;
import Serializable.InCombat.tour.FinTour;
import Serializable.Position;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * InCControleur.java
 *
 */
public class InCControleur extends Controleur<InCVue> {

	public Combat combatActu;
	public Map map;
	public final TourTimer timer;
	public final PileAction pileAction;
	public final ObjectProperty<ActionState> actionState;
	public final AStarPathFinder pathFinder;
	public final ObjectProperty<EntiteActive> entiteEnCours;
	public final SortBinding monSortEnCours;
	public final ArrayList<Position> path;
	public final ArrayList<Position> zoneAction;

	public InCControleur() {
		super(new InCVue());
		timer = new TourTimer();
		pileAction = new PileAction(this);
		pathFinder = new AStarPathFinder(500, false, new ClosestHeuristic());
		entiteEnCours = new SimpleObjectProperty();
		monSortEnCours = new SortBinding();
		path = new ArrayList();
		zoneAction = new ArrayList();
		actionState = new SimpleObjectProperty();
		actionState.addListener(new OnActionState(this));
	}

	@Override
	public void start() {
		System.out.println("START COMBAT");
		actionState.set(WAIT);
		defVue();
	}

	private void defVue() {
		placerTuiles(map.tuiles);
		combatActu.entites.values().forEach((e) -> {
			ajoutEntite(e);
		});
	}

	private void placerTuiles(Tuile[][] tuiles) {
		ecran.maps.setMapSize(tuiles.length, tuiles[0].length);
		for (int x = 0; x < tuiles.length; x++) {
			for (int y = 0; y < tuiles[0].length; y++) {
				VueTuile[] ts = ecran.maps.ajoutTuile(tuiles[x][y], x, y);
				TuileBinding tb = new TuileBinding(ts);
				tb.hover.addListener(new OnTileHover(this, x, y));
				tb.pressed.addListener(new OnTilePressed(this, x, y));
				tuiles[x][y].setBinding(tb);
			}
		}
		ecran.maps.initScale();
	}

	private void ajoutEntite(EntitePassive e) {
		map.listEntitesBinding.add(e.getBinding());
		VueEntite[] ent = ecran.maps.ajoutEntite(e);
		RowEntiteTL re = ecran.hud.timeline.addRow(e.idEntite,
				(int) e.idClasse, e.equipe.colorCode,
				e.nomDonne, e.niveau, e.caracs.get(TypeCarac.VITALITE),
				e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP),
				e.caracs.get(TypeCarac.VITESSE), e.caracs.get(TypeCarac.FATIGUE),
				e.caracs.get(TypeCarac.INITIATIVE));
		if (combatActu.dansMonEquipe(e)) {
			e.sortsP.forEach((sa) -> {
				ecran.hud.timeline.addSortPassif(e.idEntite, sa);
			});
			if (e instanceof EntiteActive) {
				((EntiteActive) e).sortsA.forEach((sa) -> {
					ecran.hud.timeline.addSortActif(e.idEntite, sa);
				});
			}
		}
		List<VueEntite> entite = new ArrayList(Arrays.asList(ent));
		entite.add(re);
		e.getBinding().addAll(entite);
	}

	private void startTour(EntiteActive e, int idTourGlobal, int idTour) {
		entiteEnCours.set(e);
		combatActu.tourGlobalActu.set(idTourGlobal);
		combatActu.tourActu.set(idTour);
		System.out.println("Start tour, idJ:" + e.idJoueur + " idE:" + e.idEntite);
		if (combatActu.dansMonEquipe(e)) {
			ecran.hud.entiteCours.setData(DataVue.getEntiteIcone(e.idClasse), e.equipe.colorCode, e.nomDonne, e.niveau, e.caracs.get(TypeCarac.VITALITE), e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP), e.caracs.get(TypeCarac.VITESSE), e.caracs.get(TypeCarac.FATIGUE), e.caracs.get(TypeCarac.INITIATIVE));
			e.sortsA.forEach((sa) -> {
				ecran.hud.entiteCours.addSA(sa);
				ButSortActif bsa = ecran.hud.barreSA.addSA(sa);
				bsa.setOnAction(new OnButSAaction(this, sa));
			});
			e.sortsP.forEach((sp) -> {
				ecran.hud.entiteCours.addSP(sp);
				ecran.hud.barreSP.addSP(sp);
			});
			e.envoutements.forEach((env) -> {
				ecran.hud.entiteCours.addE(DataVue.getSortIcone(env.idClasse), env.nbrTours);
				ecran.hud.barreE.addE(env);
			});
			ecran.hud.pileA.lancer(e.caracs.get(TypeCarac.TEMPSACTION));
			actionState.set(DEPLACEMENT);
		} else {
			ecran.hud.timeline.setForceOpen(e.idEntite, true);
		}

		timer.start(e.caracs.get(TypeCarac.TEMPSACTION));
	}

	private void stopTour() {
		actionState.set(WAIT);
		timer.stop();
		pileAction.clear();
		map.clearMap();
		ecran.hud.timeline.setForceOpen(entiteEnCours.get().idEntite, false);
	}

	public void waitState() {
		setDisable(true,
				ecran.hud.pileA,
				ecran.hud.barreSA
		);
	}

	public void deplacementState() {
		setDisable(false,
				ecran.hud.pileA,
				ecran.hud.barreSA
		);
	}

	private void setDisable(boolean disable, Node... nodes) {
		for (Node n : nodes) {
			n.setDisable(disable);
		}
	}

	public void setMap(MapSerializable mapS) {
		map = new Map(mapS);
		pathFinder.setMap(map);
	}

	private void debutCombat(DebutCombat pack) {
		if (pack.beginTime <= System.currentTimeMillis()) {
			startTour((EntiteActive) combatActu.entites.get(pack.idEntite), pack.idTourGlobal, pack.idTour);
		} else {
			SimpleIntegerProperty bind = new SimpleIntegerProperty();
			DoubleBinding pIndBind = new DoubleBinding() {
				{
					super.bind(bind);
				}

				@Override
				protected double computeValue() {
					return bind.doubleValue() % 1000 / 1000;
				}
			};
			ecran.compteur.jc.pInd.progressProperty().bind(pIndBind);
			ecran.compteur.jc.label.textProperty().bind(bind.divide(1000).asString());
			long begin = System.nanoTime() - System.currentTimeMillis() * 1000000
					+ pack.beginTime * 1000000;
			new AnimationTimer() {
				@Override
				public void handle(long l) {
					if (l < begin) {
						bind.set((int) ((begin - l) / 1000000));
					} else {
						stop();
						ecran.compteur.setVisible(false);
						startTour((EntiteActive) combatActu.entites.get(pack.idEntite), pack.idTourGlobal, pack.idTour);
					}
				}
			}.start();
		}
	}

	private void debutTour(DebutTour pack) {
		long now = System.currentTimeMillis();
		if (pack.beginTime <= now) {
			startTour((EntiteActive) combatActu.entites.get(pack.idEntite), pack.idTourGlobal, pack.idTour);
		} else {
			EXEC.submit(() -> {
				try {
					System.out.println("Debut du tour dans " + (pack.beginTime - now) + "ms");
					Thread.sleep(pack.beginTime - now);
					startTour((EntiteActive) combatActu.entites.get(pack.idEntite), pack.idTourGlobal, pack.idTour);
				} catch (InterruptedException ex) {
					Logger.getLogger(InCControleur.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
		}
	}

	private void finTour(FinTour pack) {
		if (pack.idTour != combatActu.tourActu.get()
				|| pack.idTourGlobal != combatActu.tourGlobalActu.get()
				|| pack.idEntite != entiteEnCours.get().idEntite) {
			return;
		}
		long now = System.currentTimeMillis();
		if (pack.beginTime <= now) {
			stopTour();
		} else {
			EXEC.submit(() -> {
				try {
					Thread.sleep(pack.beginTime - now);
					stopTour();
				} catch (InterruptedException ex) {
					Logger.getLogger(InCControleur.class.getName()).log(Level.SEVERE, null, ex);
				}
			});
		}
	}

	private void tour(Tour pack) {
		if (pack instanceof DebutCombat) {
			debutCombat((DebutCombat) pack);
		} else if (pack instanceof DebutTour) {
			debutTour((DebutTour) pack);
		} else if (pack instanceof FinTour) {
			finTour((FinTour) pack);
		}
	}

	private void action(Action pack) {
		EntitePassive ep = combatActu.entites.get(pack.idCible);
		if (pack instanceof AlterCarac) {
			ep.caracs.get(((AlterCarac) pack).type).first.set(
					ep.caracs.get(((AlterCarac) pack).type).first.get()
					+ ((AlterCarac) pack).valeur
			);
		} else if (pack instanceof Rotation) {
			ep.getBinding().orientation.set(((Rotation) pack).direction);
		} else if (pack instanceof Teleportation) {
			ep.getBinding().position.set(((Teleportation) pack).posCible);
		} else if (pack instanceof AddEnvoutement) {
			ep.envoutements.add(new Envoutement(((AddEnvoutement) pack).idClasseSort,
					"", "", ((AddEnvoutement) pack).nbrTours));
		} else if(pack instanceof Invocation) {
			//TODO
		}
	}

	@Override
	public void packetRecu(Object pack) {
		if (pack instanceof ChargementCombat) {
			combatActu = new Combat((ChargementCombat) pack);
		} else if (pack instanceof Tour) {
			tour((Tour) pack);
		} else if (pack instanceof Action) {
			action((Action) pack);
		}
	}

}
