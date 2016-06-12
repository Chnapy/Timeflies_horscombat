/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

/**
 * ChatInvocation.java
 * 
 */
public class ChatInvocation extends ChatAction {

	public ChatInvocation(int idClasseInvocation, String nomInvocation, boolean active) {
		super(idClasseInvocation, nomInvocation, active);
		getStyleClass().add("chatinvocation");
		addContent(" vient d'être invoqué");
	}

}
