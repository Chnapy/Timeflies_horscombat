/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatNormal.Buttons;

import InC.Vue.HUD.Module.Chat.ChatNormal.ChatTab;
import com.sun.media.jfxmediaimpl.platform.Platform;
import java.awt.Toolkit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * ButtonPane.java
 *
 */
public class ButtonPane extends VBox {

	private final ChatTab[] tabs;

	public ButtonPane(ChatTab... tabs) {
		this.tabs = tabs;
		getChildren().addAll(tabs);
		for (ChatTab t : tabs) {
			t.setOnMouseClicked((e) -> action(t, e.isShiftDown()));
		}
		setAlignment(Pos.BOTTOM_RIGHT);
	}

	private void action(ChatTab t, boolean shiftDown) {
		if (!shiftDown) {
			for (ChatTab ct : tabs) {
				if (ct != t) {
					ct.setSelected(false);
				}
			}
		} else {
			t.setSelected(!t.isSelected());
		}
	}

}
