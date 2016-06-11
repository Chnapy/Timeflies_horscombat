/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap.Effet;

import InC.Controleur.InCControleur;
import InC.Modele.Timer.ActionSort;
import javafx.scene.Group;

/**
 * Effet.java
 *
 */
public abstract class Effet extends Group {
	
	public abstract void lancerEffet(ActionSort as, InCControleur controleur);
	
	public abstract void stop();
	
	public abstract void interrupt();

}
