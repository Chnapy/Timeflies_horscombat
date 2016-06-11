/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.ValeurCarac;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * RowEntiteATL.java
 *
 */
public class RowEntiteATL extends RowEntiteActive {

	private boolean forceOpen;

	public RowEntiteATL(Image fond, String equipeCode, SimpleStringProperty nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative, SimpleIntegerProperty ordreJeu) {
		super(fond, equipeCode, nom, niveau, vitalite, tempsAction, tempsSup, vitesse, fatigue, initiative, ordreJeu);

		hover(false);
	}

	public void setForceOpen(boolean force) {
		forceOpen = force;
		open.set(force);
		setPickOnBounds(false);
	}

	@Override
	public final void hover(boolean hover) {
		if (!forceOpen) {
			open.set(hover);
			setPickOnBounds(hover);
		}
	}

}
