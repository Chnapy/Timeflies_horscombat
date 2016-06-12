/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCombat;
import javafx.scene.layout.VBox;

/**
 * ChatBox.java
 * 
 */
public class ChatBox extends VBox {
	
	public final ChatCombat chatCombat;

	public ChatBox() {
		setId("chat");
		getStyleClass().add("module");
		
		chatCombat = new ChatCombat();
		getChildren().add(chatCombat);
	}

}
