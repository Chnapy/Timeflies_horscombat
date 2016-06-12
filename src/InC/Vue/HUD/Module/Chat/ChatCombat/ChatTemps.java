/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import javafx.scene.control.Label;

/**
 * ChatTemps.java
 * 
 */
public class ChatTemps extends Label {
	
	public ChatTemps(double t) {
		this(t + "");
	}
	
	public ChatTemps(int t) {
		this(t + "");
	}
	
	public ChatTemps(String t) {
		super(t);
		getStyleClass().add("chattemps");
	}

}
