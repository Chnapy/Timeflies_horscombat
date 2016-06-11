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
	public final ObjectProperty<Position> positionReference;
	public final ObjectProperty<Orientation> orientation;
	public final ObjectProperty<Orientation> orientationReference;
	public final SimpleBooleanProperty estCible;
	public final SimpleBooleanProperty alive;

	public EntiteBinding(Position initialPosition, Orientation initialOrientation) {
		
		position = new SimpleObjectProperty(initialPosition);
		position.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changePosition(t1);
			});
		});
		
		positionReference = new SimpleObjectProperty(initialPosition);
		
		orientation = new SimpleObjectProperty(initialOrientation);
		orientation.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeOrientation(t1);
			});
		});
		
		orientationReference = new SimpleObjectProperty(initialOrientation);
		
		estCible = new SimpleBooleanProperty(false);
		estCible.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeEstCible(t1);
			});
		});
		
		alive = new SimpleBooleanProperty(true);
		alive.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeAlive(t1);
			});
		});
	}

}
