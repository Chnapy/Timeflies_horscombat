/*
 * 
 * 
 * 
 */
package InC.Vue;

import Main.Vue.Ecran;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * InCVue.java
 *
 */
public class InCVue extends Ecran<StackPane> {
	
	public final HUD hud;

	public InCVue() {
		super(new StackPane(), 1000, 600, Color.ANTIQUEWHITE);
		
		hud = new HUD();
		root.getChildren().add(hud);
	}

}
