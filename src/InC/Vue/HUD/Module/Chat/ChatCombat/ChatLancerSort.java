/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

/**
 * ChatLancerSort.java
 * 
 */
public class ChatLancerSort extends ChatCBox {

	public ChatLancerSort(int idLancer, int idS, String nomSort, int ta) {
		super(new ChatCMessage(
				new ChatSort(idS, nomSort),
				" - ",
				new ChatTemps((double) (ta / 100) / 10, "s")
		));
		getStyleClass().addAll("chatlancersort", "chat" + idLancer % 2);
	}

}
