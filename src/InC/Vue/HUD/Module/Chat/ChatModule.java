/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat;

import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCombat;
import InC.Vue.HUD.Module.Chat.ChatNormal.ChatNormal;
import javafx.scene.layout.GridPane;

/**
 * ChatModule.java
 * 
 */
public class ChatModule extends GridPane {
	
	private static final double TABS_SIZE = 20;
	
	public final ChatCombat chatCombat;
	public final ChatNormal chatNormal;
	public final ChatTextField chatTextField;

	public ChatModule() {
		setId("chat");
		getStyleClass().add("module");
		
		chatCombat = new ChatCombat();
		add(chatCombat, 1, 0);
		
		chatNormal = new ChatNormal();
		addRow(1, chatNormal.getTabs(), chatNormal);
		chatNormal.getTabs().setMinWidth(TABS_SIZE);
		chatNormal.getTabs().setPrefWidth(TABS_SIZE);
		chatNormal.setMinHeight(100);
		
		chatTextField = new ChatTextField();
		add(chatTextField, 1, 2);
	}

}
