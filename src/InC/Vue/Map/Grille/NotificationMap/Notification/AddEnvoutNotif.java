/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap.Notification;

import Main.Vue.DataVue;
import Serializable.InCombat.action.AddEnvoutement;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * AddEnvoutNotif.java
 *
 */
public class AddEnvoutNotif extends Notification {

	public AddEnvoutNotif(AddEnvoutement action) {
		super(DataVue.getSortIcone(action.idClasseSort));
		getStyleClass().add("envoutement");

		Label val = new Label("+");
		right.getChildren().add(val);

		ImageView iconEnv = new ImageView(DataVue.getSortIcone(action.idClasseEnvoutement));
		iconEnv.setPreserveRatio(true);
		iconEnv.setFitWidth(IMG_SIZE);

		right.getChildren().add(iconEnv);
	}

}
