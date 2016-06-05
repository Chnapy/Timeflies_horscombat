/*
 * 
 * 
 * 
 */
package InC.Modele.Binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;

/**
 * TimeBinding.java
 *
 */
public class TimeBinding extends DoubleBinding {

	private final NumberBinding nb;

	public TimeBinding(IntegerProperty nb) {
		this(nb.divide(1));
	}

	public TimeBinding(NumberBinding nb) {
		this.nb = nb;
		bind(nb);
	}

	@Override
	protected double computeValue() {
		return Math.floor(nb.doubleValue() / 100) / 10;
	}

}
