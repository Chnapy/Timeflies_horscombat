/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatSort;

/**
 * ChatDeclencherSortPassif.java
 * 
 */
public class ChatDeclencherSortPassif extends ChatAction {

	public ChatDeclencherSortPassif(int idClasseCible, String nomCible, boolean active, int idClasseSort, String nomSort) {
		super(idClasseCible, nomCible, active);
		getStyleClass().add("chatsp");
		addContent(
				" voit son sort passif ",
				new ChatSort(idClasseSort, nomSort),
				" déclenché !");
	}

}
