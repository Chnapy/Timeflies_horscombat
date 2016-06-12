/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import Main.Vue.DataVue;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * ChatEntite.java
 * 
 */
public class ChatEntite extends Label {
	
	public ChatEntite(int idClasse, String nom, boolean active) {
		super(nom);
		getStyleClass().addAll("chatentite", active ? "chatactive" : "chatpassive");
		ImageView icone = new ImageView(DataVue.getEntiteIcone(idClasse));
		icone.getStyleClass().add("chatimg");
		icone.setPreserveRatio(true);
		icone.fitHeightProperty().bind(prefHeightProperty().subtract(0));
		setGraphic(icone);
	}

}
