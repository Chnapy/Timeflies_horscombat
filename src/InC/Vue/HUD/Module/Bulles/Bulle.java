/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Bulles;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Bulle.java
 *
 */
public class Bulle extends Tooltip {

	protected static final double SPACE = 4;

	protected final VBox vbox;

	public Bulle(Node... nodes) {
		vbox = new VBox(nodes);
		setGraphic(vbox);
		getStyleClass().add("bulle");
		try {
			Field f = Tooltip.class.getDeclaredField("BEHAVIOR");
			f.setAccessible(true);

			Class[] classes = Tooltip.class.getDeclaredClasses();
			for (Class clazz : classes) {
				if (clazz.getName().equals("javafx.scene.control.Tooltip$TooltipBehavior")) {
					Constructor ctor = clazz.getDeclaredConstructor(Duration.class, Duration.class, Duration.class, boolean.class);
					ctor.setAccessible(true);
					Object tooltipBehavior = ctor.newInstance(Duration.ZERO, Duration.INDEFINITE, Duration.ZERO, false);
					f.set(null, tooltipBehavior);
					break;
				}
			}
		} catch (Exception e) {
		}
	}

}
