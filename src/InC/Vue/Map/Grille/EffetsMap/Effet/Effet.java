/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap.Effet;

import Serializable.Position;
import java.util.ArrayList;
import javafx.scene.Group;

/**
 * Effet.java
 *
 */
public abstract class Effet extends Group {
	
	public abstract void lancerEffet(int duration, Position from, ArrayList<Position> to);
	
	public abstract void stop();

}
