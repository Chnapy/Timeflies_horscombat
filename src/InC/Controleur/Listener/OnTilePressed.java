/*
 * 
 * 
 * 
 */
package InC.Controleur.Listener;

import InC.Controleur.InCControleur;
import static InC.Modele.ActionState.DEPLACEMENT;
import InC.Modele.Map.Tuile;
import InC.Modele.Timer.PileAction;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.sort.Deplacement;
import Serializable.InCombat.sort.ListDeplacement;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * OnTilePressed.java
 *
 */
public class OnTilePressed implements ChangeListener<Boolean> {

	private final InCControleur controleur;
	private final Position tilePos;

	public OnTilePressed(InCControleur controleur, int destX, int destY) {
		tilePos = new Position(destX, destY);
		this.controleur = controleur;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
		switch (controleur.actionState.get()) {
			case DEPLACEMENT:
				deplacement(t1);
				break;
			case SORT:
				sort(t1);
				break;
		}

	}

	private void deplacement(boolean pressed) {
		if (!pressed) {
			return;
		}
//		InCReseau.sendDeplacement(controleur.entiteEnCours.get().idEntite, controleur.combatActu.tourActu.get(), controleur.path);
		long timeActu = System.currentTimeMillis();
		int duree = controleur.entiteEnCours.get().deplacement.tempsAction.first.get();
		ArrayList<Deplacement> deplacements = new ArrayList();
		System.out.println();
//		System.out.println("-----------");
		Orientation precO = controleur.getOrientationReference(), newO;
		for (int i = 1; i < controleur.path.size(); i++) {
			Deplacement dep;
			newO = Orientation.getDirection(controleur.path.get(i - 1),
					controleur.path.get(i));
			if (precO != newO) {
				dep = new Deplacement(timeActu + (i - 1) * duree,
						PileAction.getNewID(),
						controleur.entiteEnCours.get().idEntite,
						duree,
						controleur.combatActu.tourActu.get(),
						controleur.path.get(i - 1),
						controleur.path.get(i), newO);
			} else {
				dep = new Deplacement(timeActu + (i - 1) * duree,
						PileAction.getNewID(),
						controleur.entiteEnCours.get().idEntite,
						duree,
						controleur.combatActu.tourActu.get(),
						controleur.path.get(i - 1),
						controleur.path.get(i));
			}
			deplacements.add(dep);
//			System.out.println(controleur.path.get(i));
		}
//		System.out.println("-----------");
		ListDeplacement ld = new ListDeplacement(deplacements);
		controleur.packetRecu(ld);
		controleur.path.clear();
		controleur.map.clearDeplacement();
	}

	private void sort(boolean pressed) {
		if (!pressed) {
			return;
		}

		if (controleur.map.getTileByPos(tilePos.x, tilePos.y).getBinding().state.get()
				== Tuile.TuileState.ZONE_ACTION) {
//				InCReseau.sendLancerSort(monSortEnCours.getSortActu().idClasse, entiteEnCours.idEntite, combatActu.tourActu.get(), tilePos);
			int duree = controleur.monSortEnCours.getSortActu().tempsAction.first.get();
			LancerSort lancer = new LancerSort(System.currentTimeMillis(),
					PileAction.getNewID(),
					controleur.monSortEnCours.getSortActu().idClasse,
					controleur.entiteEnCours.get().idEntite,
					duree,
					controleur.combatActu.tourActu.get(),
					tilePos);
			controleur.packetRecu(lancer);
		}
//		controleur.zoneAction.clear();
		controleur.map.clearZoneAction();
		controleur.map.clearZonePortee();
		controleur.actionState.set(DEPLACEMENT);
		controleur.monSortEnCours.setSortActu(null);
	}

}
