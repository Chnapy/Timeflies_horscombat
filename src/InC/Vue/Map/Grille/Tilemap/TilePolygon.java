/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.Tilemap;

import InC.Modele.Map.Tuile;
import javafx.scene.shape.Polygon;
import InC.Vue.Map.VueTuile;
import Serializable.InCombat.Orientation;
import javafx.beans.binding.DoubleBinding;

/**
 * TilePolygon.java
 *
 */
public class TilePolygon extends VueTuile<Polygon> {
	
	private static final double BEGIN_RADIUS = 5, END_RADIUS = BEGIN_RADIUS,
			CIBLE_SORT_POURC = 0.20;

	public TilePolygon(Tuile tuile) {
		super(tuile, new Polygon(), BEGIN_RADIUS, END_RADIUS, CIBLE_SORT_POURC);
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
				return horizontal ? widthProperty().divide(4d) : heightProperty().divide(4d);
			case SUD:
				return horizontal ? widthProperty().divide(4d).multiply(3d) : heightProperty().divide(4d).multiply(3d);
			case OUEST:
				return horizontal ? widthProperty().divide(4d) : heightProperty().divide(4d).multiply(3d);
			case EST:
				return horizontal ? widthProperty().divide(4d).multiply(3d) : heightProperty().divide(4d);
		}
		return null;
	}

}
