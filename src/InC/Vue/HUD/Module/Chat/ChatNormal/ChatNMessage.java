/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatNormal;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCMessage;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

/**
 * ChatNMessage.java
 *
 */
public class ChatNMessage extends ChatCMessage {

	protected final Label labAuteur;

	public ChatNMessage(String auteur) {
		getStyleClass().add("chatnmessage");
		labAuteur = new Label(auteur);
		labAuteur.getStyleClass().add("auteur");
		addContent(labAuteur);
	}

	public static class ChatNMGeneral extends ChatNMessage {

		public ChatNMGeneral(String auteur, Object... items) {
			super(auteur);
			getStyleClass().add("chatnmgeneral");
			addContent(": ", items);
		}
	}

	public static class ChatNMEquipe extends ChatNMessage {

		public ChatNMEquipe(String auteur, Object... items) {
			super(auteur);
			getStyleClass().add("chatnmequipe");
			addContent(": ", items);
		}
	}

	public static class ChatNMPrive extends ChatNMessage {

		protected final Label labDest;

		public ChatNMPrive(String auteur, String destinataire, Object... items) {
			super(auteur);
			getStyleClass().add("chatnmprive");
			labDest = new Label(destinataire);
			labDest.getStyleClass().add("auteur");
			addContent("->", labDest, ": ", items);
		}
	}

}
