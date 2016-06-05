/*
 * 
 * 
 * 
 */
package InC.Modele.Binding;

import InC.Vue.Map.VueEntite;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * EntiteBinding.java
 * 
 */
public class EntiteBinding extends ActionnableBinding<VueEntite> {
	
	public final ObjectProperty<Position> position;
	public final ObjectProperty<Orientation> orientation;
	public final SimpleBooleanProperty estCible;

	public EntiteBinding(Position initialPosition, Orientation initialOrientation) {
		
		position = new SimpleObjectProperty(initialPosition);
		position.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changePosition(t1);
			});
		});
		
		orientation = new SimpleObjectProperty(initialOrientation);
		orientation.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeOrientation(t1);
			});
		});
		
		estCible = new SimpleBooleanProperty();
		estCible.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeEstCible(t1);
			});
		});
	}

}
