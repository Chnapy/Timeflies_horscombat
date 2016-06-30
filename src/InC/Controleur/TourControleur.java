/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Controleur.Listener.OnButSAaction;
import InC.Modele.ActionState;
import static InC.Modele.ActionState.DEPLACEMENT;
import static InC.Modele.ActionState.WAIT;
import InC.Modele.Camera.Camera;
import InC.Modele.Donnees.Combat;
import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Map;
import InC.Modele.Timer.PileAction;
import InC.Modele.Timer.TourTimer;
import InC.Vue.HUD.CompteurDebutCombat;
import InC.Vue.HUD.Module.Barres.BarreEnvoutements;
import InC.Vue.HUD.Module.Barres.BarreSortsActifs;
import InC.Vue.HUD.Module.Barres.BarreSortsPassifs;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCombat;
import InC.Vue.HUD.Module.EntiteCours;
import InC.Vue.HUD.Module.PileBox;
import InC.Vue.HUD.Module.Sorts.ButSortActif;
import InC.Vue.HUD.Module.Timeline.Timeline;
import InC.Vue.Map.AllMap;
import Main.Vue.DataVue;
import Serializable.InCombat.TypeCarac;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.tour.DebutTour;
import Serializable.InCombat.tour.DebutTourGlobal;
import Serializable.InCombat.tour.FinTour;
import Serializable.InCombat.tour.FinTourGlobal;
import Serializable.InCombat.tour.Tour;
import java.util.TreeMap;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * TourControleur.java
 *
 */
public class TourControleur {
	
	//Controleur
	private final InCControleur controleur;
	private final ActionControleur actionControleur;
	
	//Modele
	private final Combat combatActu;
	private final TreeMap<Long, EntitePassive> entites;
	private final SimpleIntegerProperty tourActu;
	private final SimpleIntegerProperty tourGlobalActu;
	private final ObjectProperty<EntiteActive> entiteEnCours;
	private final Timeline timeline;
	private final ObjectProperty<ActionState> actionState;
	private final TourTimer timer;
	private final PileAction pileAction;
	private final Map map;
	
	//Vue
	private final Camera camera;
	private final ChatCombat chatCombat;
	private final AllMap maps;
	private final BarreSortsActifs barreSA;
	private final BarreSortsPassifs barreSP;
	private final BarreEnvoutements barreE;
	private final PileBox pileA;
	private final EntiteCours entiteCours;
	private final CompteurDebutCombat compteur;

	public TourControleur(InCControleur controleur) {
		this.controleur = controleur;
		actionControleur = controleur.actionControleur;
		combatActu = controleur.combatActu;
		entites = combatActu.entites;
		tourActu = combatActu.tourActu;
		tourGlobalActu = combatActu.tourGlobalActu;
		entiteEnCours = controleur.entiteEnCours;
		chatCombat = controleur.getEcran().hud.chat.chatCombat;
		timeline = controleur.getEcran().hud.timeline;
		actionState = controleur.actionState;
		timer = controleur.timer;
		pileAction = controleur.pileAction;
		map = controleur.map;
		camera = controleur.getEcran().camera;
		maps = controleur.getEcran().maps;
		barreSA = controleur.getEcran().hud.barreSA;
		barreSP = controleur.getEcran().hud.barreSP;
		barreE = controleur.getEcran().hud.barreE;
		pileA = controleur.getEcran().hud.pileA;
		entiteCours = controleur.getEcran().hud.entiteCours;
		compteur = controleur.getEcran().compteur;
	}

	public void debutCombat(DebutCombat pack) {
		if (pack.beginTime <= System.currentTimeMillis()) {
			compteur.setVisible(false);
			chatCombat.startCombat(0);
		} else {
			chatCombat.startCombat((int) (pack.beginTime - System.currentTimeMillis()));
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
			compteur.jc.pInd.progressProperty().bind(pIndBind);
			compteur.jc.label.textProperty().bind(bind.divide(1000).asString());
			long begin = System.nanoTime() - System.currentTimeMillis() * 1000000
					+ pack.beginTime * 1000000;
			new AnimationTimer() {
				@Override
				public void handle(long l) {
					if (l < begin) {
						bind.set((int) ((begin - l) / 1000000));
					} else {
						stop();
						compteur.setVisible(false);
					}
				}
			}.start();
		}
	}

	private void startTour(EntiteActive e, int idTour, long beginTime) {
		chatCombat.startTour(idTour, e.idClasse, e.nomDonne.get(), e.caracs.get(TypeCarac.TEMPSACTION).first.get());
		camera.moveToNode(maps.getEntiteSprite(e.idEntite));
		entiteEnCours.set(e);
		tourActu.set(idTour);
		entiteEnCours.get().getBinding().positionReference.set(e.getBinding().position.get());
		entiteEnCours.get().getBinding().orientationReference.set(e.getBinding().orientation.get());
		pileAction.setBeginTime(beginTime);
		System.out.println("Start tour" + idTour + ", idJ:" + e.idJoueur + " idE:" + e.idEntite);
		if (combatActu.dansMonEquipe(e)) {
			setEntiteCoursData(e);
			e.sortsA.values().forEach((sa) -> {
				ButSortActif bsa = barreSA.addSA(e, sa);
				bsa.tButton.setOnAction(new OnButSAaction(controleur, sa));
				bsa.disableProperty().bind(
						sa.tempsAction.first.greaterThan(e.caracs.get(TypeCarac.TEMPSACTION).first.add(e.caracs.get(TypeCarac.TEMPSSUP).first)).or(
						e.caracs.get(TypeCarac.TEMPSACTION).first.lessThanOrEqualTo(0d)
				));
			});
			e.sortsP.values().forEach((sp) -> {
				barreSP.addSP(sp);
			});
			e.envoutements.forEach((env) -> {
				barreE.addE(env);
			});
			pileA.lancer(e.caracs.get(TypeCarac.TEMPSACTION),
					e.caracs.get(TypeCarac.TEMPSSUP));
			actionState.set(DEPLACEMENT);
		} else {
			timeline.setForceOpen(e.idEntite, true);
		}

		timer.start(e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP));
	}

	private void setEntiteCoursData(EntiteActive e) {
		entiteCours.setData(DataVue.getEntiteIcone(e.idClasse), e.equipe.colorCode, e.nomDonne, e.niveau, e.caracs.get(TypeCarac.VITALITE), e.caracs.get(TypeCarac.TEMPSACTION), e.caracs.get(TypeCarac.TEMPSSUP), e.caracs.get(TypeCarac.VITESSE), e.caracs.get(TypeCarac.FATIGUE), e.caracs.get(TypeCarac.INITIATIVE));
		e.sortsA.values().forEach((sa) -> {
			entiteCours.addSA(sa);
		});
		e.sortsP.values().forEach((sp) -> {
			entiteCours.addSP(sp);
		});
		e.envoutements.forEach((env) -> {
			entiteCours.addE(env);
		});
	}

	public void stopTour() {
		System.out.println("End tour" + tourActu.get());
		actionState.set(WAIT);
		timer.stop();
		pileAction.clear();
		barreSA.getChildren().clear();
		map.clearMap();
		timeline.setForceOpen(entiteEnCours.get().idEntite, false);
		entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).first.set(
				entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).second.get());
		entiteEnCours.get().caracs.get(TypeCarac.TEMPSSUP).first.set(
				entiteEnCours.get().caracs.get(TypeCarac.TEMPSSUP).second.get());
	}

	private void startTourGlobal(int idTourG) {
		System.out.println("Start tourG" + idTourG);
		tourGlobalActu.set(idTourG);
		chatCombat.startTourGlobal(idTourG);
	}

	private void endTourGlobal() {
		System.out.println("End tourG" + tourGlobalActu.get());
		chatCombat.endTourGlobal(tourGlobalActu.get());
	}

	private void debutTour(DebutTour pack) {
		try {
			startTour((EntiteActive) entites.get(pack.idEntite), pack.idTour, pack.beginTime);
		} catch (ClassCastException e) {
			System.err.println("Une entité passive ne peut pas jouer de tour !");
		}
	}

	private void finTour(FinTour pack) {
		if (pack.idTour != tourActu.get()
				|| pack.idEntite != entiteEnCours.get().idEntite) {
			return;
		}
		chatCombat.endTour(tourActu.get(),
				entiteEnCours.get().idClasse,
				entiteEnCours.get().nomDonne.get());
		stopTour();
	}

	private void debutTourGlobal(DebutTourGlobal pack) {
		definirOrdreJeu(pack.ordreJeu);
		startTourGlobal(pack.idTG);
	}

	private void finTourGlobal(FinTourGlobal pack) {
		if (pack.idTG != tourGlobalActu.get()) {
			return;
		}
		endTourGlobal();
	}

	private void definirOrdreJeu(long[] ordre) {
		for (int i = 0; i < ordre.length; i++) {
			entites.get(ordre[i]).ordreJeu.set(i);
		}
		timeline.sort();
	}

	public void tour(Tour pack) {
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
			actionControleur.action(a);
		}
	}

}
