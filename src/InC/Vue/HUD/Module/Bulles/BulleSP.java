/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Bulles;

import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.CercleLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * BulleSP.java
 *
 */
public class BulleSP extends Bulle {

	public BulleSP(SortPassif sp) {
		Label titre = new Label();
		titre.textProperty().bind(sp.nom);
		titre.getStyleClass().add("bulle-titre");
		
		CercleLabel niveau = new CercleLabel(sp.niveau + "");
		niveau.getStyleClass().add("niveau");
		
		HBox top = new HBox(titre, niveau);
		top.setAlignment(Pos.CENTER_LEFT);
		top.setSpacing(SPACE);
		HBox.setHgrow(niveau, Priority.ALWAYS);
		
		Label desc = new Label();
		desc.textProperty().bind(sp.description);
		desc.getStyleClass().add("desc");
		vbox.getChildren().addAll(top, desc);
	}

}
