/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Barres;

import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.SortActif;
import InC.Vue.HUD.Module.Sorts.ButSortActif;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

/**
 * BarreSortsActifs.java
 *
 */
public class BarreSortsActifs extends FlowPane {

	private static final double BOUTON_WIDTH = 80, BOUTON_HEIGHT = BOUTON_WIDTH;

	public BarreSortsActifs() {
		super(Orientation.HORIZONTAL);
		setId("barreSA");
	}

	public ButSortActif addSA(EntiteActive ea, SortActif sa) {
		ButSortActif b = new ButSortActif(ea, sa);
		getChildren().add(b);
		b.setPrefSize(BOUTON_WIDTH, BOUTON_HEIGHT);
		return b;
	}

}
