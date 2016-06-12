/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import Main.Modele.Data;

/**
 * ChatLancerDeplacement.java
 * 
 */
public class ChatLancerDeplacement extends ChatLancerSort {

	public ChatLancerDeplacement(int idLancer, int ta) {
		super(idLancer, Data.DEPLACEMENT_IDCLASSE, "Déplacement", ta);
		getStyleClass().add("chatlancerdeplacement");
	}

}
