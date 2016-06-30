/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatNormal;

import InC.Vue.HUD.Module.Chat.ChatBox;
import InC.Vue.HUD.Module.Chat.ChatNormal.Buttons.ButtonPane;
import InC.Vue.HUD.Module.Chat.ChatNormal.ChatNMessage.*;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;

/**
 * ChatNormal.java
 * 
 */
public class ChatNormal extends ChatBox {
	
	private static final double TAB_SIZE = 20;
	
	private final ChatTab tabGeneral, tabEquipe, tabPrive;
	
	private final ButtonPane tabs;
	
	public ChatNormal() {
		setId("chatnormal");
		
		tabGeneral = new ChatTab<ChatNMGeneral>();
		tabGeneral.getStyleClass().add("tabgeneral");
		tabGeneral.setPrefSize(TAB_SIZE, TAB_SIZE);
		tabGeneral.setSelected(true);
		
		tabEquipe = new ChatTab<ChatNMEquipe>();
		tabEquipe.getStyleClass().add("tabequipe");
		tabEquipe.setPrefSize(TAB_SIZE, TAB_SIZE);
		tabEquipe.setSelected(true);
		
		tabPrive = new ChatTab<ChatNMPrive>();
		tabPrive.getStyleClass().add("tabprive");
		tabPrive.setPrefSize(TAB_SIZE, TAB_SIZE);
		tabPrive.setSelected(true);
		
		tabs = new ButtonPane(tabGeneral, tabEquipe, tabPrive);
		
		addGeneral("TestPseudo", "TOTO");
		addEquipe("TestPseudo", "TOTO");
		addPrive("TestPseudo", "TestPseudo2", "TOTO");
	}
	
	public void addGeneral(String auteur, String message) {
		ChatNMGeneral m = new ChatNMGeneral(auteur, message);
		tabGeneral.addMessage(m);
		addToContent(m);
	}
	
	public void addEquipe(String auteur, String message) {
		ChatNMEquipe m = new ChatNMEquipe(auteur, message);
		tabEquipe.addMessage(m);
		addToContent(m);
	}
	
	public void addPrive(String auteur, String dest, String message) {
		ChatNMPrive m = new ChatNMPrive(auteur, dest, message);
		tabPrive.addMessage(m);
		addToContent(m);
	}

	public ButtonPane getTabs() {
		return tabs;
	}

}
