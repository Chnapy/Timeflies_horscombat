/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * ChatCBox.java
 * 
 */
public class ChatCBox extends VBox {
	
	private final VBox box;
	
	public ChatCBox(ChatCMessage top) {
		getStyleClass().add("chatcbox");
		getChildren().add(top);
		
		box = new VBox();
		box.getStyleClass().add("chatchild");
		getChildren().add(box);
	}
	
	public void add(Node n) {
		box.getChildren().add(n);
	}

}
