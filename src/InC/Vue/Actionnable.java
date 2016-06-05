/*
 * 
 * 
 * 
 */
package InC.Vue;

import javafx.scene.Node;

/**
 * Actionnable.java
 *
 * @param <N>
 */
public interface Actionnable<N extends Node> {

	public default void init() {
		getThis().getStyleClass().add("actionnable");
	}

	public default void changeHover(boolean hover) {
		if (hover) {
			getThis().getStyleClass().add("hover");
		} else {
			getThis().getStyleClass().remove("hover");
		}
		hover(hover);
	}

	public void hover(boolean hover);

	public default void changePressed(boolean pressed) {
		if (pressed) {
			getThis().getStyleClass().add("pressed");
		} else {
			getThis().getStyleClass().remove("pressed");
		}
		pressed(pressed);
	}

	public void pressed(boolean pressed);

	public default N getThis() {
		return (N) this;
	}

}
