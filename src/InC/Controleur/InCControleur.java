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
import InC.Modele.Camera.Camera;
import InC.Modele.Donnees.Envoutement;
import InC.Modele.Donnees.Equipe;
import InC.Modele.Donnees.SortActif;
import InC.Modele.Donnees.SortPassif;
import InC.Modele.Timer.ActionSort;
import InC.Modele.Timer.PileAction;
import InC.Vue.HUD.Module.Sorts.ButSortActif;
import InC.Vue.HUD.Module.Timeline.RowEntiteATL;
import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.Grille.NotificationMap.Notification.AddEnvoutNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.AddSPNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.AlterCNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.MortNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.Notification;
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
import Main.Vue.Vue;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.ListInCombat;
import Serializable.InCombat.Orientation;
import static Serializable.InCombat.TypeCarac.VITESSE;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.sort.ActionsToSort;
import Serializable.InCombat.action.AddEnvoutement;
import Serializable.InCombat.action.AddSortPassif;
import Serializable.InCombat.action.AlterCarac;
import Serializable.InCombat.action.Invocation;
import Serializable.InCombat.action.ListActions;
import Serializable.InCombat.action.Mort;
import Serializable.InCombat.action.Rotation;
import Serializable.InCombat.action.Teleportation;
import Serializable.InCombat.donnee.InEntiteActive;
import Serializable.InCombat.donnee.InEntitePassive;
import Serializable.InCombat.sort.AnnulerSort;
import Serializable.InCombat.sort.DeclencherSortPassif;
import Serializable.InCombat.sort.Deplacement;
import Serializable.InCombat.sort.ListDeplacement;
import Serializable.InCombat.tour.Tour;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.tour.DebutTour;
import Serializable.InCombat.tour.DebutTourGlobal;
import Serializable.InCombat.tour.FinTour;
import Serializable.InCombat.tour.FinTourGlobal;
import Serializable.Position;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;

/**
 * InCControleur.java
 *
 */
public class InCControleur extends Controleur<InCVue, InCombat> {

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
		ecran.maps.grille.effetsMap.setControleur(this);
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

	private void ajoutEntite(EntitePassive e) {
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

	private void startTour(EntiteActive e, int idTour, long beginTime) {
		entiteEnCours.set(e);
		combatActu.tourActu.set(idTour);
		entiteEnCours.get().getBinding().positionReference.set(e.getBinding().position.get());
		entiteEnCours.get().getBinding().orientationReference.set(e.getBinding().orientation.get());
		pileAction.setBeginTime(beginTime);
		System.out.println("Start tour" + idTour + ", idJ:" + e.idJoueur + " idE:" + e.idEntite);
		if (combatActu.dansMonEquipe(e)) {
			setEntiteCoursData(e);
			e.sortsA.values().forEach((sa) -> {
				ButSortActif bsa = ecran.hud.barreSA.addSA(e, sa);
				bsa.tButton.setOnAction(new OnButSAaction(this, sa));
				bsa.disableProperty().bind(
						sa.tempsAction.first.greaterThan(e.caracs.get(TypeCarac.TEMPSACTION).first.add(e.caracs.get(TypeCarac.TEMPSSUP).first)).or(
						e.caracs.get(TypeCarac.TEMPSACTION).first.lessThanOrEqualTo(0d)
				));
			});
			e.sortsP.values().forEach((sp) -> {
				ecran.hud.barreSP.addSP(sp);
			});
			e.envoutements.forEach((env) -> {
				ecran.hud.barreE.addE(env);
			});
			ecran.hud.pileA.lancer(e.caracs.get(TypeCarac.TEMPSACTION),
					e.caracs.get(TypeCarac.TEMPSSUP));
			actionState.set(DEPLACEMENT);
		} else {
			ecran.hud.timeline.setForceOpen(e.idEntite, true);
		}

		timer.start(e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP));
	}

	private void setEntiteCoursData(EntiteActive e) {
		ecran.hud.entiteCours.setData(DataVue.getEntiteIcone(e.idClasse), e.equipe.colorCode, e.nomDonne, e.niveau, e.caracs.get(TypeCarac.VITALITE), e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP), e.caracs.get(TypeCarac.VITESSE), e.caracs.get(TypeCarac.FATIGUE), e.caracs.get(TypeCarac.INITIATIVE));
		e.sortsA.values().forEach((sa) -> {
			ecran.hud.entiteCours.addSA(sa);
		});
		e.sortsP.values().forEach((sp) -> {
			ecran.hud.entiteCours.addSP(sp);
		});
		e.envoutements.forEach((env) -> {
			ecran.hud.entiteCours.addE(env);
		});
	}

	private void stopTour() {
		System.out.println("End tour" + combatActu.tourActu.get());
		actionState.set(WAIT);
		timer.stop();
		pileAction.clear();
		map.clearMap();
		ecran.hud.timeline.setForceOpen(entiteEnCours.get().idEntite, false);
		entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).first.set(entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).second.get());
		entiteEnCours.get().caracs.get(TypeCarac.TEMPSSUP).first.set(entiteEnCours.get().caracs.get(TypeCarac.TEMPSSUP).second.get());
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

	private void startTourGlobal(int idTourG) {
		System.out.println("Start tourG" + idTourG);
		combatActu.tourGlobalActu.set(idTourG);
	}

	private void endTourGlobal() {
		System.out.println("End tourG" + combatActu.tourGlobalActu.get());
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
			ecran.compteur.setVisible(false);
			tour(pack.dtg);
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
						tour(pack.dtg);
					}
				}
			}.start();
		}
	}

	private void debutTour(DebutTour pack) {
		try {
			startTour((EntiteActive) combatActu.entites.get(pack.idEntite), pack.idTour, pack.beginTime);
		} catch (ClassCastException e) {
			System.err.println("Une entité passive ne peut pas jouer de tour !");
		}
	}

	private void finTour(FinTour pack) {
		if (pack.idTour != combatActu.tourActu.get()
				|| pack.idEntite != entiteEnCours.get().idEntite) {
			return;
		}
		stopTour();
	}

	private void debutTourGlobal(DebutTourGlobal pack) {
		definirOrdreJeu(pack.ordreJeu);
		startTourGlobal(pack.idTG);
		tour(pack.dt);
	}

	private void finTourGlobal(FinTourGlobal pack) {
		if (pack.idTG != combatActu.tourGlobalActu.get()) {
			return;
		}
		endTourGlobal();
	}

	private void definirOrdreJeu(long[] ordre) {
		for (int i = 0; i < ordre.length; i++) {
			combatActu.entites.get(ordre[i]).ordreJeu.set(i);
		}
		ecran.hud.timeline.sort();
	}

	private void tour(Tour pack) {
		if (pack instanceof DebutTourGlobal) {
			debutTourGlobal((DebutTourGlobal) pack);
		} else if (pack instanceof FinTourGlobal) {
			finTourGlobal((FinTourGlobal) pack);
		} else if (pack instanceof DebutTour) {
			debutTour((DebutTour) pack);
		} else if (pack instanceof FinTour) {
			finTour((FinTour) pack);
		}
		for (Action a : pack.actions) {
			action(a);
		}
	}

	public void action(Action pack) {
		EntitePassive ep = combatActu.entites.get(pack.idCible);
		Notification n = null;
		Position p = null;
		if (pack instanceof AlterCarac) {
//			System.out.println("ALTERCARAC: " + ((AlterCarac) pack).valeur);
			Platform.runLater(() -> {
				ep.caracs.get(((AlterCarac) pack).type).first.set(
						ep.caracs.get(((AlterCarac) pack).type).first.get()
						+ ((AlterCarac) pack).valeur
				);
			});
			n = new AlterCNotif((AlterCarac) pack);
			p = AbstractMap.getRealTilePos(
					combatActu.entites.get(pack.idCible)
					.getBinding().position.get()
			);
		} else if (pack instanceof Rotation) {
			ep.getBinding().orientation.set(((Rotation) pack).direction);
		} else if (pack instanceof Teleportation) {
			ep.getBinding().position.set(((Teleportation) pack).posCible);
		} else if (pack instanceof AddEnvoutement) {
			ep.envoutements.add(new Envoutement(((AddEnvoutement) pack).idClasseSort,
					((AddEnvoutement) pack).nbrTours));
			n = new AddEnvoutNotif((AddEnvoutement) pack);
			p = AbstractMap.getRealTilePos(
					combatActu.entites.get(pack.idCible)
					.getBinding().position.get()
			);
		} else if (pack instanceof AddSortPassif) {
			ep.sortsP.putIfAbsent(((AddSortPassif) pack).idClasseSP,
					new SortPassif(((AddSortPassif) pack).idClasseSP, -1));
			n = new AddSPNotif((AddSortPassif) pack);
			p = AbstractMap.getRealTilePos(
					combatActu.entites.get(pack.idCible)
					.getBinding().position.get()
			);
		} else if (pack instanceof Mort) {
			ep.getBinding().alive.set(false);
			map.listEntitesBinding.remove(ep.getBinding());
			n = new MortNotif((Mort) pack);
			p = AbstractMap.getRealTilePos(
					combatActu.entites.get(pack.idCible)
					.getBinding().position.get()
			);
		} else if (pack instanceof Invocation) {
			//TODO
			InEntitePassive iep = ((Invocation) pack).invoc;
			Equipe equipe = combatActu.equipes.get(((Invocation) pack).numeroEquipe);
			EntitePassive e;
			if (iep instanceof InEntiteActive) {
				e = new EntiteActive((InEntiteActive) iep, equipe, ((InEntiteActive) iep).tempsDeplacement);
			} else {
				e = new EntitePassive(iep, equipe);
			}
			ajoutEntite(e);
		}
		if (n != null) {
			ecran.maps.grille.notifMap.notifierAction(n, p);
		}
	}

	private void ajouterSort(LancerSort pack) {
		if (pack instanceof Deplacement) {
			System.err.println("probleme : sort deplacement !");
			return;
		}
		EntiteActive ea;
		try {
			ea = (EntiteActive) combatActu.entites.get(pack.idEntite);
		} catch (ClassCastException e) {
			System.err.println("Une entité passive ne peut pas lancer de sort !");
			return;
		}
		if (pack.tour != combatActu.tourActu.get() || ea == null) {
			System.err.println("probleme : tour ou entite incorrecte");
			return;
		}
		SortActif sa;
		if (pack.sort != null) {
			sa = ea.sortsA.putIfAbsent(pack.idClasseSort, new SortActif(pack.sort));
		} else {
			sa = ea.sortsA.get(pack.idClasseSort);
		}
		if (sa == null) {
			System.err.println("probleme : sort incorrecte");
			return;
		}

		zoneAction.clear();
		map.getZoneAction(pack.position, sa.zoneAction.getZoneIntermediaire(), zoneAction);
		ActionSort as = new ActionSort(pack.idLancer,
				(int) (pack.beginTime - pileAction.beginTime), sa,
				pack.dureeLancer,
				pack.idEntite,
				getPositionReference(),
				(ArrayList<Position>) zoneAction.clone(), pack.actions);
		pileAction.addSort(as);
	}

	private void actionsToSort(ActionsToSort pack) {
		try {
			pileAction.getById(pack.idActionSort).actions = pack.actions;
		} catch (NullPointerException e) {
			System.err.println("ID non reconnu : " + pack.idActionSort);
		}
	}

	private void annulerSort(AnnulerSort pack) {
		pileAction.removeSort(pileAction.getById(pack.idActionSort));
	}

	private boolean ajouterDeplacement(Deplacement pack) {
		if (pack.idEntite != entiteEnCours.get().idEntite) {
			System.err.println("Une entité passive ne peut pas se déplacer !");
			return false;
		}
		ActionSort as = new ActionSort(pack.idLancer,
				(int) (pack.beginTime - pileAction.beginTime),
				entiteEnCours.get().deplacement,
				pack.dureeLancer,
				pack.idEntite,
				pack.previousPosition,
				Arrays.asList(pack.position), pack.actions);
		return pileAction.addSort(as);
	}

	private void listDeplacement(ListDeplacement pack) {
		if (pack.deplacements.isEmpty()) {
			return;
		}
		int i;
		for (i = 0; i < pack.deplacements.size(); i++) {
			if (!ajouterDeplacement(pack.deplacements.get(i))) {
				break;
			}
		}
		if (i > 0) {
			entiteEnCours.get().getBinding().positionReference.set(
					pack.deplacements.get(i - 1).position);
			for (; i > 0; i--) {
				for (Action a : pack.deplacements.get(i - 1).actions) {
					if (a instanceof Rotation) {
						entiteEnCours.get().getBinding().orientationReference.set(
								((Rotation) a).direction);
					}
				}
			}
		}
	}

	private void declencherSortPassif(DeclencherSortPassif pack) {

		EntitePassive ep = combatActu.entites.get(pack.idEntite);
		if (pack.tour != combatActu.tourActu.get() || ep == null) {
			System.err.println("probleme : tour ou entite incorrecte");
			return;
		}
		SortPassif sp;
		if (pack.sort != null) {
			sp = ep.sortsP.putIfAbsent(pack.idClasseSort, new SortPassif(pack.sort));
		} else {
			sp = ep.sortsP.get(pack.idClasseSort);
		}
		if (sp == null) {
			System.err.println("probleme : sort incorrecte");
			return;
		}
		for (Action a : pack.actions) {
			packetRecu(a);
		}
	}

	@Override
	public void packetRecu(InCombat pack) {
		if (pack instanceof ChargementCombat) {
			combatActu = new Combat((ChargementCombat) pack);
		} else if (pack instanceof DebutCombat) {
			debutCombat((DebutCombat) pack);
		} else {
			long now = System.currentTimeMillis();
			if (pack.beginTime <= now) {
				if (pack instanceof Tour) {
					tour((Tour) pack);
				} else if (pack instanceof Action) {
					action((Action) pack);
				} else if (pack instanceof ListActions) {
					((ListActions) pack).actions.forEach((a) -> action(a));
				} else if (pack instanceof ListInCombat) {
					((ListInCombat) pack).listInCombat.forEach((a) -> packetRecu(a));
				} else if (pack instanceof Deplacement) {
					ajouterDeplacement((Deplacement) pack);
				} else if (pack instanceof LancerSort) {
					ajouterSort((LancerSort) pack);
				} else if (pack instanceof ActionsToSort) {
					actionsToSort((ActionsToSort) pack);
				} else if (pack instanceof AnnulerSort) {
					annulerSort((AnnulerSort) pack);
				} else if (pack instanceof ListDeplacement) {
					listDeplacement((ListDeplacement) pack);
				} else if (pack instanceof DeclencherSortPassif) {
					declencherSortPassif((DeclencherSortPassif) pack);
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
		stopTour();
	}

}
