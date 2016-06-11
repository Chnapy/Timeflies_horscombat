/*
 * 
 * 
 * 
 */
package InC.Vue.HUD;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * CercleLabel.java
 *
 */
public class CercleLabel extends Label {

	public CercleLabel(StringBinding sb) {
		this();
		textProperty().bind(sb);
	}

	public CercleLabel(String text) {
		this();
		setText(text);
	}

	public CercleLabel() {
		getStyleClass().add("cercle-label");
		heightProperty().addListener((ov, t, t1) -> autosize());
		prefWidthProperty().bind(heightProperty());
		minWidthProperty().bind(heightProperty());
		minHeightProperty().bind(textProperty().length().multiply(8));
		setTextAlignment(TextAlignment.CENTER);
		setAlignment(Pos.CENTER);
	}

}
