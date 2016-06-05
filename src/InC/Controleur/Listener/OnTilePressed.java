/*
 * 
 * 
 * 
 */
package InC.Controleur.Listener;

import InC.Controleur.InCControleur;
import static InC.Modele.ActionState.DEPLACEMENT;
import InC.Modele.InCReseau;
import InC.Modele.Map.Tuile;
import Serializable.InCombat.TypeCarac;
import Serializable.Position;
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
		InCReseau.sendDeplacement(controleur.entiteEnCours.get().idEntite, controleur.combatActu.tourActu.get(), controleur.path);
		controleur.path.clear();
		controleur.map.clearDeplacement();
	}

	private void sort(boolean pressed) {
		if (!pressed) {
			return;
		}

		if (controleur.map.getTileByPos(tilePos.x, tilePos.y).getBinding().state.get() == Tuile.TuileState.ZONE_ACTION) {
//				InCReseau.sendLancerSort(monSortEnCours.getSortActu().idClasse, entiteEnCours.idEntite, combatActu.tourActu.get(), tilePos);
			controleur.getEcran().maps.grille.effetsMap.lancerEffet(controleur.monSortEnCours.getSortActu().idClasse, controleur.monSortEnCours.getSortActu().tempsAction.get(), controleur.entiteEnCours.get().getBinding().position.get(), controleur.zoneAction);
			controleur.pileAction.addSort(controleur.entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).second
					.subtract(controleur.entiteEnCours.get().caracs.get(TypeCarac.TEMPSACTION).first).intValue(), controleur.monSortEnCours.getSortActu(), controleur.entiteEnCours.get().getBinding().position.get(), controleur.zoneAction);
		}
		controleur.zoneAction.clear();
		controleur.map.clearZoneAction();
		controleur.map.clearZonePortee();
		controleur.actionState.set(DEPLACEMENT);
		controleur.monSortEnCours.setSortActu(null);
	}

}
