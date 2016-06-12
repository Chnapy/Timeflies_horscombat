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
 * ChatSort.java
 * 
 */
public class ChatSort extends Label {
	
	public ChatSort(int idClasse, String nom) {
		super(nom);
		getStyleClass().add("chatsort");
		ImageView icone = new ImageView(DataVue.getSortIcone(idClasse));
		icone.getStyleClass().add("chatimg");
		icone.setPreserveRatio(true);
		icone.fitHeightProperty().bind(prefHeightProperty());
		setGraphic(icone);
	}

}
