/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.text.TextFlow;

/**
 * ChatCMessage.java
 *
 */
public class ChatCMessage extends TextFlow {

	private static final double HEIGHT = 20;

	private ChatTemps chatTemps;

	public ChatCMessage(Object... items) {
		getStyleClass().add("chatcmessage");
		chatTemps = null;
		setPrefHeight(HEIGHT);
		addContent(items);
	}

	public final void addContent(Object... items) {
		for (Object o : items) {
			add(o);
		}
	}

	private void add(Object o) {
		Node n;

		if (o instanceof Node) {
			n = (Node) o;
			if (o instanceof ChatTemps) {
				chatTemps = (ChatTemps) n;
			}
		} else if (o instanceof String) {
			n = new Text((String) o);
			((Text) n).setTranslateY(2);
		} else if (o instanceof Image) {
			n = new ImageView((Image) o);
		} else if (o instanceof Object[]) {
			for (Object obj : (Object[]) o) {
				add(obj);
			}
			return;
		} else {
			return;
		}
		n.getStyleClass().add("chatitem");
		getChildren().add(n);
		if (n instanceof Region) {
			((Region) n).prefHeightProperty().bind(prefHeightProperty());
		}
	}

	public ChatTemps getChatTemps() {
		return chatTemps;
	}

}
