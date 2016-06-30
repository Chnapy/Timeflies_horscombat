/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * ChatBox.java
 *
 */
public abstract class ChatBox extends ScrollPane {

	private final VBox content;

	public ChatBox() {
		getStyleClass().add("chatbox");
		content = new VBox();
		setContent(content);

		setFitToWidth(true);
		setFitToHeight(true);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		content.setAlignment(Pos.BOTTOM_LEFT);
		content.heightProperty().addListener((o, ov, nv) -> toBottom());
	}

	protected void addToContent(Node n) {
		Platform.runLater(() -> {
			content.getChildren().add(n);
		});
	}

	protected void toBottom() {
		setVvalue(1.0);
	}
}
