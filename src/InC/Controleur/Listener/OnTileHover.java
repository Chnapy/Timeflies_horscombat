/*
 * 
 * 
 * 
 */
package InC.Controleur.Listener;

import InC.Controleur.InCControleur;
import InC.Modele.Map.Tuile;
import Serializable.Position;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * OnTileHover.java
 *
 */
public class OnTileHover implements ChangeListener<Boolean> {

	private final InCControleur controleur;
	private final Position tilePos;

	public OnTileHover(InCControleur controleur, int destX, int destY) {
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

	private void sort(boolean hover) {
		if (!hover) {
			controleur.zoneAction.clear();
			controleur.map.clearZoneAction();
			return;
		}
		if (controleur.map.getTileByPos(tilePos.x, tilePos.y).getBinding().state.get() == Tuile.TuileState.ZONE_PORTEE) {
			controleur.map.getZoneAction(tilePos,
					controleur.monSortEnCours.getSortActu().zoneAction.getZoneIntermediaire(), controleur.zoneAction);
			controleur.map.showZoneAction(controleur.zoneAction);
		}
	}

	private void deplacement(boolean hover) {
		if (!hover) {
			controleur.path.clear();
			controleur.map.clearDeplacement();
			return;
		}
		Position start = controleur.getPositionReference();
		controleur.pathFinder.findPath(controleur.entiteEnCours.get(),
				start.x,
				start.y,
				tilePos.x, tilePos.y, controleur.path);
		if (controleur.path.isEmpty()) {
			return;
		}
		controleur.map.showPath(controleur.path);
	}

}
