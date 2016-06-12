/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;

/**
 * ChatDebutCombat.java
 *
 */
public class ChatDebutCombat extends ChatCMessage {

	public ChatDebutCombat(int compteur) {
		super(new ChatCMessage("Début du combat dans " + (double) (compteur / 100) / 10 + "s"));
		getStyleClass().add("chatdc");
	}

}
