/*
 * 
 * 
 * 
 */
package InC.Vue.Module.Timeline;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * TlEnvoutement.java
 *
 */
public class TlEnvoutement extends AnchorPane {

	public TlEnvoutement(Image fond, IntegerProperty toursRestant) {
		ImageView imgView = new ImageView(fond);
		imgView.fitHeightProperty().bind(prefHeightProperty());
		imgView.setPreserveRatio(true);
		getChildren().add(imgView);

		Label trs = new Label();
		trs.getStyleClass().add("envTours");
		trs.textProperty().bind(toursRestant.asString());
		getChildren().add(trs);
		AnchorPane.setLeftAnchor(trs, 0d);
		AnchorPane.setBottomAnchor(trs, 0d);
	}

}
