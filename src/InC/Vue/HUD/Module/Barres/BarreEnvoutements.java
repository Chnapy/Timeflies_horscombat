/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Barres;

import InC.Modele.Donnees.Envoutement;
import InC.Vue.HUD.Module.Sorts.ButEnvoutement;

/**
 * BarreEnvoutements.java
 *
 */
public class BarreEnvoutements extends BarreSortsPassifs {

	public BarreEnvoutements() {

	}

	public void addE(Envoutement env) {
		ButEnvoutement b = new ButEnvoutement(env);
		getChildren().add(b);
		b.setPrefHeight(BOUTON_HEIGHT);
	}

}
