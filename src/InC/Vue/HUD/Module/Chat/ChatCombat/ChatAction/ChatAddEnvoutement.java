/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatSort;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTemps;

/**
 * ChatAddEnvoutement.java
 *
 */
public class ChatAddEnvoutement extends ChatAction {

	public ChatAddEnvoutement(int idClasseCible, String nomCible, boolean active, int idClasseEnvoutement, String nomEnvoutement, int nbrTours) {
		super(idClasseCible, nomCible, active);
		getStyleClass().add("chatenvoutement");
		addContent(
				" gagne l'envoutement ",
				new ChatSort(idClasseEnvoutement, nomEnvoutement),
				" - ",
				new ChatTemps(nbrTours + "trs")
		);
	}

}
