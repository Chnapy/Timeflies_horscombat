/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap.Notification;

import static InC.Vue.StyleClass.CARAC_CLASS;
import Main.Vue.DataVue;
import Serializable.InCombat.action.AlterCarac;
import javafx.scene.control.Label;

/**
 * AlterCNotif.java
 * 
 */
public class AlterCNotif extends Notification {
	
	public AlterCNotif(AlterCarac action) {
		super(DataVue.getSortIcone(action.idClasseSort));
		getStyleClass().add(CARAC_CLASS.get(action.type));
		
		Label val = new Label((action.valeur > 0 ? "+" : "") + action.valeur);
		
		right.getChildren().add(val);
	}

}
