/*
 * 
 * 
 * 
 */
package InC.Modele.Binding;

import InC.Modele.Map.Tuile.TuileState;
import InC.Vue.Map.VueTuile;
import Serializable.Duo;
import Serializable.InCombat.Orientation;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * TuileBinding.java
 *
 */
public class TuileBinding extends ActionnableBinding<VueTuile> {

	public final ObjectProperty<TuileState> state;
	private TuileState previousState;
	public final Duo<ObjectProperty<Orientation>, ObjectProperty<Orientation>> direction;

	public TuileBinding(VueTuile... act) {
		this(Arrays.asList(act));
	}

	public TuileBinding(List<VueTuile> act) {
		super(act);

		previousState = TuileState.NONE;
		state = new SimpleObjectProperty(TuileState.NONE);
		state.addListener((ov, t, t1) -> {
			previousState = t;
			actionnables.forEach((a) -> {
				a.changeState(t1);
			});
		});
		
		direction = new Duo(new SimpleObjectProperty<>(null), new SimpleObjectProperty<>(null));
		direction.first.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.setBegin(t1);
			});
		});
		direction.second.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.setEnd(t1);
			});
		});
	}
	
	public void returnToPreviousState() {
		state.set(previousState);
	}

}
