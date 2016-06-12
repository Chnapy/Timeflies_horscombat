/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

/**
 * ChatMort.java
 * 
 */
public class ChatMort extends ChatAction {

	public ChatMort(int idClasseCible, String nomCible, boolean active) {
		super(idClasseCible, nomCible, active);
		getStyleClass().add("chatmort");
		addContent(" est mort");
	}

}