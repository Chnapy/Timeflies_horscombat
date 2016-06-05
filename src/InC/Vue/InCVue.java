/*
 * 
 * 
 * 
 */
package InC.Vue;

import InC.Vue.HUD.CompteurDebutCombat;
import InC.Vue.HUD.HUD;
import InC.Vue.Map.AllMap;
import Main.Vue.Ecran;
import Main.Vue.Vue;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * InCVue.java
 *
 */
public class InCVue extends Ecran<StackPane> {

	public final AllMap maps;
	public final HUD hud;
	public final CompteurDebutCombat compteur;

	public InCVue() {
		super(new StackPane(), 1000, 600, Color.ANTIQUEWHITE);

		maps = new AllMap();
		hud = new HUD(maps.minimap);
		compteur = new CompteurDebutCombat();
		root.getChildren().addAll(maps.grille, hud, compteur);
		turnOffPickOnBoundsForAll();
	}
	
	private void turnOffPickOnBoundsForAll() {
		Vue.turnOffPickOnBoundsFor(root);
	}
}
