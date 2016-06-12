/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatEntite;

/**
 * ChatAction.java
 * 
 */
public abstract class ChatAction extends ChatCMessage {
	
	public ChatAction(int idClasseCible, String nomCible, boolean active) {
		super(new ChatEntite(idClasseCible, nomCible, active));
		getStyleClass().add("chataction");
	}

}
