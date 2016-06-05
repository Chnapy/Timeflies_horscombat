/*
 * 
 * 
 * 
 */
package InC.Controleur.Listener;

import InC.Controleur.InCControleur;
import InC.Modele.ActionState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * OnActionState.java
 *
 */
public class OnActionState implements ChangeListener<ActionState> {

	private final InCControleur controleur;

	public OnActionState(InCControleur controleur) {
		this.controleur = controleur;
	}

	@Override
	public void changed(ObservableValue<? extends ActionState> ov, ActionState t, ActionState t1) {
		switch (t1) {
			case WAIT:
				controleur.waitState();
				break;
			case DEPLACEMENT:
				controleur.deplacementState();
				break;
			case SORT:
				break;
		}
	}
}
