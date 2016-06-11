/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module;

import InC.Vue.HUD.Module.Timeline.RowEntite;
import InC.Vue.HUD.Module.Timeline.RowEntiteActive;

/**
 * EntiteCours.java
 *
 */
public class EntiteCours extends RowEntiteActive {

	public EntiteCours() {
		setId("entiteCours");
		getStyleClass().add("module");
		
		open.set(true);
	}

}
