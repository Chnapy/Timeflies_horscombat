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

	private int width;
	
	@Override
	public void ajoutNode(Minientite tuile, int x, int y) {
		tuile.radiusProperty().bind(prefWidthProperty().divide(width * 2));
		getChildren().add(tuile);
		Platform.runLater(() -> {
			tuile.setLayoutX(x * tuile.getRadius() * 2 + tuile.getRadius());
			tuile.setLayoutY(y * tuile.getRadius() * 2 + tuile.getRadius());
		});
	}

	@Override
	public void setMapSize(int width, int height) {
		this.width = width;
	}

}
