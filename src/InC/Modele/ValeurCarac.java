/*
 * 
 * 
 * 
 */
package InC.Modele;

import Serializable.Duo;
import javafx.beans.property.IntegerProperty;

/**
 * ValeurCarac.java
 * 
 * @param <P>
 */
public class ValeurCarac<P extends IntegerProperty> extends Duo<P, P> {

	public ValeurCarac(P actu, P max) {
		super(actu, max);
	}
	
}
