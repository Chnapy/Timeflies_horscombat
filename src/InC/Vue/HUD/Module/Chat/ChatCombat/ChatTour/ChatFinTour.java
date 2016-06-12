/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatEntite;

/**
 * ChatFinTour.java
 *
 */
public class ChatFinTour extends ChatCMessage {

	public ChatFinTour(int idT, int idClasse, String nomEntite) {
		super(
				"Tour n°" + idT + " - Fin ",
				new ChatEntite(idClasse, nomEntite, true)
		);
	}

}
