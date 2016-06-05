/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Minimap;

import InC.Modele.Map.Tuile;
import javafx.scene.shape.Rectangle;
import InC.Vue.Map.VueTuile;
import Serializable.InCombat.Orientation;
import javafx.beans.binding.DoubleBinding;

/**
 * Minituile.java
 *
 */
public class Minituile extends VueTuile<Rectangle> {

	private static final double BEGIN_RADIUS = 2, END_RADIUS = BEGIN_RADIUS,
			CIBLE_SORT_POURC = 0.25;
	
	public Minituile(Tuile tuile) {
		super(tuile, new Rectangle(), BEGIN_RADIUS, END_RADIUS, CIBLE_SORT_POURC);
		getStyleClass().add("minituile");

		poly.widthProperty().bind(prefWidthProperty());
		poly.heightProperty().bind(prefHeightProperty());
	}

	@Override
	public void hover(boolean hover) {
	}

	@Override
	public void pressed(boolean pressed) {
	}

	@Override
	public void state(Tuile.TuileState state) {
	}

	@Override
	public DoubleBinding getSizeValue(Orientation o, boolean horizontal) {
		switch (o) {
			case NORD:
				return horizontal ? widthProperty().divide(2d) : heightProperty().multiply(0);
			case SUD:
				return horizontal ? widthProperty().divide(2d) : heightProperty().multiply(1d);
			case OUEST:
				return horizontal ? widthProperty().multiply(0) : heightProperty().divide(2d);
			case EST:
				return horizontal ? widthProperty().multiply(1d) : heightProperty().divide(2d);
		}
		return null;
	}

}
