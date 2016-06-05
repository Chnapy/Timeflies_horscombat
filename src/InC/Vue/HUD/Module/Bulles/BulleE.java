/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Bulles;

import InC.Modele.Donnees.Envoutement;
import InC.Vue.HUD.CercleLabel;

/**
 * BulleE.java
 * 
 */
public class BulleE extends BulleSP {
	
	public BulleE(Envoutement e) {
		super(e);
		CercleLabel tours = new CercleLabel(e.nbrTours.asString());
		vbox.getChildren().add(tours);
	}

}
