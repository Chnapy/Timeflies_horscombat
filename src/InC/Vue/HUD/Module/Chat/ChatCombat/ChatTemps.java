/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

/**
 * ChatTemps.java
 *
 */
public class ChatTemps extends Label {

	private final String unit;
	private final boolean withDecimal;
	private double val;

	public ChatTemps(double t, String unit) {
		this(t, unit, true);
	}

	public ChatTemps(double t, String unit, boolean withDecimal) {
		this.withDecimal = withDecimal;
		val = t;
		this.unit = unit;
		getStyleClass().add("chattemps");
		defText();
	}

	private void defText() {
		if (withDecimal) {
			setText(val + unit);
		} else {
			setText((int) val + unit);
		}
	}

//	public ChatTemps(int t, String unit) {
//		this(t, unit);
//	}
//	public ChatTemps(String t) {
//		super(t);
//	}
	public void addVal(double t) {
		val += t;
		defText();
	}

}
