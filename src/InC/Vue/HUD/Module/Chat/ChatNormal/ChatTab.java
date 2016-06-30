/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatNormal;

import java.util.ArrayList;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Rectangle;

/**
 * ChatTab.java
 * 
 * @param <M>
 */
public class ChatTab<M extends ChatNMessage> extends ToggleButton {
	
	private final ArrayList<M> listMessages;
	
	public ChatTab() {
		super(null, new Rectangle());
		getStyleClass().add("chattab");
		listMessages = new ArrayList();
	}
	
	public void addMessage(M m) {
		m.visibleProperty().bind(selectedProperty());
		m.managedProperty().bind(selectedProperty());
		listMessages.add(m);
	}

}
