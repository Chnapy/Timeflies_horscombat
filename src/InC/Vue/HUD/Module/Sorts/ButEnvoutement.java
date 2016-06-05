/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Sorts;

import InC.Modele.Donnees.Envoutement;
import InC.Vue.HUD.Module.Bulles.BulleE;
import Main.Vue.DataVue;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * ButEnvoutement.java
 *
 */
public class ButEnvoutement extends AnchorPane {
	
	public ButEnvoutement(Envoutement e) {
		this(DataVue.getSortIcone(e.idClasse), e.nbrTours);
		Tooltip.install(this, new BulleE(e));
	}

	public ButEnvoutement(Image fond, IntegerProperty toursRestant) {
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
