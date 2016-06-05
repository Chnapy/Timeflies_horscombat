/*
 * 
 * 
 * 
 */
package InC.Controleur.Listener;

import InC.Controleur.InCControleur;
import InC.Modele.ActionState;
import InC.Modele.Binding.SortBinding;
import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.SortActif;
import InC.Modele.Map.Map;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * OnButSAaction.java
 *
 */
public class OnButSAaction implements EventHandler<ActionEvent> {

	private final SortActif sa;
	private final InCControleur controleur;

	public OnButSAaction(InCControleur controleur, SortActif sa) {
		this.sa = sa;
		this.controleur = controleur;
	}

	@Override
	public void handle(ActionEvent t) {
		controleur.actionState.set(ActionState.SORT);
		if (controleur.monSortEnCours.getSortActu() == sa) {
			return;
		}
		controleur.monSortEnCours.setSortActu(sa);
		controleur.map.showZonePortee(controleur.entiteEnCours.get().getBinding().position.get(),
				sa.zonePortee.getZoneIntermediaire());
	}

}
