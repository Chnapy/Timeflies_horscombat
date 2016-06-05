/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.ValeurCarac;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.TypeCarac;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * CaracteristiquesBindable.java
 *
 */
public class CaracteristiquesBindable extends HashMap<TypeCarac, ValeurCarac<IntegerProperty>> {

	public CaracteristiquesBindable(CaracteristiquePhysique cp) {
		cp.forEach((k, v) -> put(k, new ValeurCarac(
				new SimpleIntegerProperty(v.actu),
				new SimpleIntegerProperty(v.max)
		)));
	}

}
