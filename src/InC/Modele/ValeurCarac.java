/*
 * 
 * 
 * 
 */
package InC.Modele;

import Serializable.Duo;
import javafx.beans.value.ObservableValue;

/**
 * ValeurCarac.java
 * 
 * @param <P>
 */
public class ValeurCarac<P extends ObservableValue> extends Duo<P, P> {

	public ValeurCarac(P first, P second) {
		super(first, second);
	}
	
}
