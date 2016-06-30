/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Controleur.Listener.OnActionState;
import InC.Controleur.Listener.OnTileHover;
import InC.Controleur.Listener.OnTilePressed;
import InC.Modele.ActionState;
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
import InC.Modele.Donnees.SortPassif;
import InC.Modele.Timer.PileAction;
import InC.Vue.HUD.Module.Timeline.RowEntiteATL;
import InC.Vue.Map.VueEntite;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.TypeCarac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import InC.Vue.Map.VueTuile;
import static Main.Controleur.MainControleur.EXEC;
import Main.Modele.Data;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.ListInCombat;
import Serializable.InCombat.Orientation;
import static Serializable.InCombat.TypeCarac.VITESSE;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.sort.ActionsToSort;
import Serializable.InCombat.action.ListActions;
import Serializable.InCombat.sort.AnnulerSort;
import Serializable.InCombat.sort.DeclencherSortPassif;
import Serializable.InCombat.sort.Deplacement;
import Serializable.InCombat.sort.ListDeplacement;
import Serializable.InCombat.tour.Tour;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.Position;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.input.KeyCode;

/**
 * InCControleur.java
 *
 */
public class InCControleur extends Controleur<InCVue, InCombat> {

	public final Combat combatActu;
	public final Map map;
	public final TourTimer timer;
	public final PileAction pileAction;
	public final ObjectProperty<ActionState> actionState;
	public final AStarPathFinder pathFinder;
	public final ObjectProperty<EntiteActive> entiteEnCours;
	public final SortBinding monSortEnCours;
	public final ArrayList<Position> path;
	public final ArrayList<Position> zoneAction;

	public final ActionControleur actionControleur;
	public final TourControleur tourControleur;
	public final SortControleur sortControleur;

	public InCControleur(ChargementCombat pack, MapSerializable mapS) {
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
		ecran.maps.grille.effetsMap.setControleur(this);

		combatActu = new Combat(pack);
		map = new Map(mapS);
		pathFinder.setMap(map);

		actionControleur = new ActionControleur(this);
		tourControleur = new TourControleur(this);
		sortControleur = new SortControleur(this);
		ecran.root.setOnKeyPressed((e) -> onKeyPressed(e.getCode()));
		ecran.root.setOnKeyReleased((e) -> onKeyReleased(e.getCode()));
	}
	
	private void onKeyPressed(KeyCode kc) {
		if(kc == Data.HIDE_HUD) {
			ecran.hud.hide();
			ecran.hud.setMouseTransparent(true);
		}
	}
	
	private void onKeyReleased(KeyCode kc) {
		if(kc == Data.HIDE_HUD) {
			ecran.hud.show();
			ecran.hud.setMouseTransparent(false);
		}
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
		ecran.camera.initScale();
	}
	
	public void ajoutEntite(EntitePassive e) {
		map.listEntitesBinding.add(e.getBinding());

		List<VueEntite> ventite = new ArrayList();

		VueEntite[] ent = ecran.maps.ajoutEntite(e);
		ventite.addAll(Arrays.asList(ent));

		if (e instanceof EntiteActive) {
			RowEntiteATL re = ecran.hud.timeline.addEntiteActive((EntiteActive) e);
			ventite.add(re);
		} else {
			ecran.hud.timeline.addEntitePassive(e);
		}
		e.sortsP.addListener((MapChangeListener.Change<? extends Integer, ? extends SortPassif> change) -> {
			SortPassif sp;
			if (change.wasAdded()) {
				sp = change.getValueAdded();
				ecran.hud.timeline.addSortPassif(e.idEntite, sp);
				if (entiteEnCours.get() == e && combatActu.dansMonEquipe(e)) {
					ecran.hud.entiteCours.addSP(sp);
				}
			}
		});
		e.envoutements.addListener((ListChangeListener.Change<? extends Envoutement> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach((env) -> {
						ecran.hud.timeline.addEnvoutement(e.idEntite, env);
						if (entiteEnCours.get() == e && combatActu.dansMonEquipe(e)) {
							ecran.hud.entiteCours.addE(env);
						}
					});
				}
			}
		});

		e.sortsP.values().forEach((sp) -> {
			ecran.hud.timeline.addSortPassif(e.idEntite, sp);
		});
		if (e instanceof EntiteActive) {
			((EntiteActive) e).sortsA.values().forEach((sa) -> {
				sa.tempsAction.first.bind(
						sa.tempsAction.second.divide(
								((EntiteActive) e).caracs.get(VITESSE).first.divide(100d)
						)
				);
				ecran.hud.timeline.addSortActif(e.idEntite, sa);
			});
			bindTempsToFatigue((EntiteActive) e);
		}

		e.getBinding().addAll(ventite);
	}

	private void bindTempsToFatigue(EntiteActive e) {
		e.caracs.get(TypeCarac.TEMPSACTION).second.addListener((ov, t, t1) -> {
			if (e.caracs.get(TypeCarac.TEMPSACTION).first
					.greaterThan(e.caracs.get(TypeCarac.TEMPSACTION).second).get()) {
				e.caracs.get(TypeCarac.TEMPSACTION).first
						.set(e.caracs.get(TypeCarac.TEMPSACTION).second.get());
			}
		});
		e.caracs.get(TypeCarac.TEMPSACTION).second.bind(
				e.caracs.get(TypeCarac.FATIGUE).first.divide(-100d).add(1d).multiply(e.maxTempsAction)
		);
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

	@Override
	public void packetRecu(InCombat pack) {
		if (pack instanceof DebutCombat) {
			tourControleur.debutCombat((DebutCombat) pack);
		} else {
			long now = System.currentTimeMillis();
			if (pack.beginTime <= now) {
				if (pack instanceof Tour) {
					tourControleur.tour((Tour) pack);
				} else if (pack instanceof Action) {
					actionControleur.action((Action) pack);
				} else if (pack instanceof ListActions) {
					((ListActions) pack).actions.forEach((a) -> actionControleur.action(a));
				} else if (pack instanceof ListInCombat) {
					((ListInCombat) pack).listInCombat.forEach((a) -> packetRecu(a));
				} else if (pack instanceof Deplacement) {
					sortControleur.ajouterDeplacement((Deplacement) pack);
				} else if (pack instanceof LancerSort) {
					sortControleur.ajouterSort((LancerSort) pack);
				} else if (pack instanceof ActionsToSort) {
					sortControleur.actionsToSort((ActionsToSort) pack);
				} else if (pack instanceof AnnulerSort) {
					sortControleur.annulerSort((AnnulerSort) pack);
				} else if (pack instanceof ListDeplacement) {
					sortControleur.listDeplacement((ListDeplacement) pack);
				} else if (pack instanceof DeclencherSortPassif) {
					sortControleur.declencherSortPassif((DeclencherSortPassif) pack);
				} else {
					System.err.println("PAQUET NON RECONNU : " + pack);
				}
			} else {
				EXEC.submit(() -> {
					try {
						Thread.sleep(pack.beginTime - now);
						packetRecu(pack);
					} catch (InterruptedException ex) {
						Logger.getLogger(InCControleur.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
			}
		}
	}

	public Position getPositionReference() {
		return entiteEnCours.get().getBinding().positionReference.get();
	}

	public Orientation getOrientationReference() {
		return entiteEnCours.get().getBinding().orientationReference.get();
	}

	public void stop() {
		tourControleur.stopTour();
	}

}
