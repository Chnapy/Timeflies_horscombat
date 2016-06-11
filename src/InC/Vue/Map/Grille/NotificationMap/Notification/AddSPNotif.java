/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap.Notification;

import Main.Vue.DataVue;
import Serializable.InCombat.action.AddSortPassif;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * AddSPNotif.java
 * 
 */
public class AddSPNotif extends Notification {

	public AddSPNotif(AddSortPassif action) {
		super(DataVue.getSortIcone(action.idClasseSort));
		getStyleClass().add("sort_passif");

		Label val = new Label("+");
		right.getChildren().add(val);

		ImageView iconEnv = new ImageView(DataVue.getSortIcone(action.idClasseSP));
		iconEnv.setPreserveRatio(true);
		iconEnv.setFitWidth(IMG_SIZE);

		right.getChildren().add(iconEnv);
	}

}