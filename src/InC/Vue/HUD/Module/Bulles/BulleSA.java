/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Bulles;

import InC.Modele.Donnees.SortActif;
import InC.Vue.HUD.CercleLabel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

/**
 * BulleSA.java
 *
 */
public class BulleSA extends BulleSP {

	public BulleSA(SortActif sa) {
		super(sa);
		CercleLabel ta = new CercleLabel(), cd = new CercleLabel(), fa = new CercleLabel();

		ta.textProperty().bind(sa.tempsAction.divide(1000d).asString());
		ta.getStyleClass().add("tempsa");

		cd.textProperty().bind(Bindings.concat(sa.cooldown.first.asString(), "/", sa.cooldown.second.asString()));
		cd.getStyleClass().add("cooldown");

		fa.textProperty().bind(sa.fatigue.asString());
		fa.getStyleClass().add("fatigue");

		FlowPane bottom = new FlowPane(ta, cd, fa);
		bottom.prefWrapLengthProperty().bind(vbox.widthProperty());
		bottom.setOrientation(Orientation.HORIZONTAL);
		bottom.setAlignment(Pos.TOP_LEFT);
		bottom.setHgap(SPACE);
		bottom.setVgap(SPACE);
		
		vbox.getChildren().addAll(bottom);
	}

}
