/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Minimap;

import InC.Vue.Map.VueMap;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

/**
 * Minientitemap.java
 *
 */
public class Minientitemap extends Pane implements VueMap<Minientite, Minientitemap> {

	private double width;

	@Override
	public void ajoutNode(Minientite tuile, int x, int y) {
		tuile.prefWidthProperty().bind(prefWidthProperty().divide(width));
		tuile.prefHeightProperty().bind(tuile.prefWidthProperty());
		getChildren().add(tuile);
		Platform.runLater(() -> {
			tuile.setLayoutX(x * tuile.getWidth());
			tuile.setLayoutY(y * tuile.getWidth());
		});
	}

	@Override
	public void setMapSize(int width, int height) {
		this.width = width;
	}

}
