/*
 * 
 * 
 * 
 */
package InC.Vue.Module;

import Main.Vue.DataVue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	public void addButton(ButSortPassif b) {
		getChildren().add(b);
		b.setFitWidth(BOUTON_WIDTH);
		b.setPreserveRatio(true);
	}

	public static class ButSortPassif extends ImageView {

		public ButSortPassif(Image fond) {
			super(fond);
		}
	}

}
