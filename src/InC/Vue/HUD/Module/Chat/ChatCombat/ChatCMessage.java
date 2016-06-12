/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * ChatCMessage.java
 *
 */
public class ChatCMessage extends TextFlow {

	private static final double HEIGHT = 20;

	public ChatCMessage(Object... items) {
		getStyleClass().add("chatcmessage");
		setPrefHeight(HEIGHT);
		addContent(items);
	}

	public final void addContent(Object... items) {
		for (Object o : items) {
			Node n;
			if (o instanceof Node) {
				n = (Node) o;
			} else if (o instanceof String) {
				n = new Text((String) o);
			} else if (o instanceof Image) {
				n = new ImageView((Image) o);
			} else {
				continue;
			}
			getChildren().add(n);
			if(n instanceof Region)
				((Region) n).prefHeightProperty().bind(prefHeightProperty());
		}
	}

}
