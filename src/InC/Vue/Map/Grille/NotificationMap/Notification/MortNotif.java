/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap.Notification;

import Main.Vue.DataVue;
import Serializable.InCombat.action.Mort;

/**
 * MortNotif.java
 *
 */
public class MortNotif extends Notification {

	public MortNotif(Mort mort) {
		super(DataVue.getSortIcone(mort.idClasseSort));
		getStyleClass().add("mort");
	}

}
