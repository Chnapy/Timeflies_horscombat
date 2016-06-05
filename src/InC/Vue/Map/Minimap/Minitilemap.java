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
 * Minitilemap.java
 *
 */
public class Minitilemap extends Pane implements VueMap<Minituile, Minitilemap> {

	private int width;

	public Minitilemap() {
	}

	@Override
	public void ajoutNode(Minituile tuile, int x, int y) {
		tuile.prefWidthProperty().bind(prefWidthProperty().divide(width));
		tuile.prefHeightProperty().bind(tuile.prefWidthProperty());
		getChildren().add(tuile);
		Platform.runLater(() -> {
			tuile.setLayoutX(x * tuile.getPrefWidth());
			tuile.setLayoutY(y * tuile.getPrefHeight());
		});
	}

	@Override
	public void setMapSize(int width, int height) {
		this.width = width;
	}

}
