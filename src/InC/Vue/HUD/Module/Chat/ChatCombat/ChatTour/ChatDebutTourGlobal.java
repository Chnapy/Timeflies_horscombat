/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCBox;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;

/**
 * ChatDebutTourGlobal.java
 *
 */
public class ChatDebutTourGlobal extends ChatCBox {

	public ChatDebutTourGlobal(int idTG) {
		super(new ChatCMessage("Tour Global n°" + idTG + " - Lancement"));
		getStyleClass().addAll("chattg", "chat" + idTG % 2);
	}

}
