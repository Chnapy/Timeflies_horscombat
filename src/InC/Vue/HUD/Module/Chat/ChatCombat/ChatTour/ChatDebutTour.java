/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCBox;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatEntite;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTemps;

/**
 * ChatDebutTour.java
 *
 */
public class ChatDebutTour extends ChatCBox {

	public ChatDebutTour(int idT, int idClasse, String nomEntite, int ta) {
		super(new ChatCMessage(
				"Tour n°" + idT + " - ",
				new ChatEntite(idClasse, nomEntite, true),
				" - ",
				new ChatTemps((double) (ta / 100) / 10, "s")
				
		));
		getStyleClass().addAll("chatt", "chat" + idT % 2);
	}

}
