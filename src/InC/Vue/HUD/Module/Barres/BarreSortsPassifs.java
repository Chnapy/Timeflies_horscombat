/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Barres;

import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Module.Sorts.ButSortPassif;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

/**
 * BarreSortsPassifs.java
 *
 */
public class BarreSortsPassifs extends FlowPane {

	protected static final double BOUTON_WIDTH = 32, BOUTON_HEIGHT = BOUTON_WIDTH,
			SPACE = 2, PADDING = 5;

	public BarreSortsPassifs() {
		super(Orientation.HORIZONTAL);
		setId("barreSP");
		setPadding(new Insets(PADDING));
		setHgap(SPACE);
		setVgap(SPACE);
	}

	public void addSP(SortPassif sp) {
		ButSortPassif b = new ButSortPassif(sp);
		getChildren().add(b);
		b.setFitWidth(BOUTON_WIDTH);
	}

}
