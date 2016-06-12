/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction;

import Serializable.InCombat.TypeCarac;

/**
 * ChatAlterCarac.java
 *
 */
public class ChatAlterCarac extends ChatAction {

	public ChatAlterCarac(int idClasseCible, String nomCible, boolean active, TypeCarac typeCarac, int valeur) {
		super(idClasseCible, nomCible, active);
		getStyleClass().addAll("chataltercarac", typeCarac.toString().toLowerCase());
		addContent((valeur > 0 ? " gagne " : " perd ") + Math.abs(valeur) + " " + typeCarac.toString().toLowerCase());
	}

}
