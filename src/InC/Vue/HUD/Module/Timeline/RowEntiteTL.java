/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.ValeurCarac;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

/**
 * RowEntiteTL.java
 *
 */
public class RowEntiteTL extends RowEntite {

	private boolean forceOpen;

	public RowEntiteTL(Image fond, String equipeCode, String nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative) {
		super(fond, equipeCode, nom, niveau, vitalite, tempsAction, tempsSup, vitesse, fatigue, initiative);

		vignette.prefHeightProperty().bind(new DoubleBinding() {
			{
				super.bind(caracteristiques.visibleProperty());
			}

			@Override
			protected double computeValue() {
				return caracteristiques.visibleProperty().get() ? T_ENTITE_MAX_HEIGHT : T_ENTITE_MIN_HEIGHT;
			}
		});
		caracteristiques.managedProperty().bind(caracteristiques.visibleProperty());
		sortsActifs.visibleProperty().bind(caracteristiques.visibleProperty());
		sortsActifs.managedProperty().bind(sortsActifs.visibleProperty());

		hover(false);
	}

	public void setForceOpen(boolean open) {
		forceOpen = open;
		caracteristiques.setVisible(open);
		setPickOnBounds(false);
	}

	@Override
	public final void hover(boolean hover) {
		if (!forceOpen) {
			caracteristiques.setVisible(hover);
			setPickOnBounds(hover);
		}
	}

}
