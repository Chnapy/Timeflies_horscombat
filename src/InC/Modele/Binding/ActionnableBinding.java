/*
 * 
 * 
 * 
 */
package InC.Modele.Binding;

import InC.Vue.Actionnable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;

/**
 * ActionnableBinding.java
 *
 * @param <A>
 */
public class ActionnableBinding<A extends Actionnable> {

	protected final ArrayList<A> actionnables;

	public BooleanExpression hover;
	public BooleanExpression pressed;

	public ActionnableBinding(A... act) {
		this(Arrays.asList(act));
	}

	public ActionnableBinding(Collection<A> act) {
		actionnables = new ArrayList();
		hover = new BooleanBinding() {
			@Override
			protected boolean computeValue() {
				return false;
			}
		};
		pressed = new BooleanBinding() {
			@Override
			protected boolean computeValue() {
				return false;
			}
		};
		hover.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changeHover(t1);
			});
		});
		pressed.addListener((ov, t, t1) -> {
			actionnables.forEach((a) -> {
				a.changePressed(t1);
			});
		});
		addAll(act);
	}

	public final void addAll(Collection<A> as) {
		as.forEach((a) -> add(a));
	}

	public void add(A a) {
		actionnables.add(a);
		hover = hover.or(a.getThis().hoverProperty());
		pressed = pressed.or(a.getThis().pressedProperty());
		hover.addListener((ov, t, t1) -> {
			actionnables.forEach((a2) -> {
				a2.changeHover(t1);
			});
		});
		pressed.addListener((ov, t, t1) -> {
			actionnables.forEach((a2) -> {
				a2.changePressed(t1);
			});
		});
	}

}
