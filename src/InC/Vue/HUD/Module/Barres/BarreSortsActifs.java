/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Barres;

import InC.Modele.Donnees.SortActif;
import InC.Vue.HUD.Module.Sorts.ButSortActif;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

/**
 * BarreSortsActifs.java
 *
 */
public class BarreSortsActifs extends FlowPane {

	private static final double BOUTON_WIDTH = 64, BOUTON_HEIGHT = BOUTON_WIDTH,
			SPACE = 4, PADDING = SPACE;

	public BarreSortsActifs() {
		super(Orientation.HORIZONTAL);
		setId("barreSA");
		setPadding(new Insets(PADDING));
		setHgap(SPACE);
		setVgap(SPACE);
	}

	public ButSortActif addSA(SortActif sa) {
		ButSortActif b = new ButSortActif(sa);
		getChildren().add(b);
		b.setPrefSize(BOUTON_WIDTH, BOUTON_HEIGHT);
		return b;
	}

}
