/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Chat.ChatCombat;

import InC.Vue.HUD.Module.Chat.ChatBox;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatAction;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatAddEnvoutement;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatAlterCarac;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatDeclencherSortPassif;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatInvocation;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatAction.ChatMort;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour.ChatFinTourGlobal;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour.ChatFinTour;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour.ChatDebutTour;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour.ChatDebutTourGlobal;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatTour.ChatDebutCombat;
import Serializable.InCombat.TypeCarac;
import javafx.application.Platform;

/**
 * ChatCombat.java
 *
 */
public class ChatCombat extends ChatBox {

	private ChatDebutTourGlobal tgActu;
	private ChatDebutTour tActu;
	private ChatLancerSort sActu;

	public ChatCombat() {
		setId("chatcombat");
	}

	public void addActionInvocation(int idClasseInvocation, String nomInvocation, boolean active) {
		addAction(new ChatInvocation(idClasseInvocation, nomInvocation, active));
	}

	public void addActionMort(int idClasseCible, String nomCible, boolean active) {
		addAction(new ChatMort(idClasseCible, nomCible, active));
	}

	public void addActionDeclencherSortPassif(int idClasseCible, String nomCible, boolean active,
			int idClasseSort, String nomSort) {
		addAction(new ChatDeclencherSortPassif(idClasseCible, nomCible, active, idClasseSort, nomSort));
	}

	public void addActionAddEnvoutement(int idClasseCible, String nomCible, boolean active,
			int idClasseEnvoutement, String nomEnvoutement, int nbrTours) {
		addAction(new ChatAddEnvoutement(idClasseCible, nomCible, active, idClasseEnvoutement, nomEnvoutement, nbrTours));
	}

	public void addActionAlterCarac(int idClasseCible, String nomCible, boolean active,
			TypeCarac typeCarac, int valeur) {
		addAction(new ChatAlterCarac(idClasseCible, nomCible, active, typeCarac, valeur));
	}

	private void addAction(ChatAction action) {
		Platform.runLater(() -> {
			try {
				sActu.add(action);
			} catch (NullPointerException e1) {
				try {
					tActu.add(action);
				} catch (NullPointerException e2) {
					try {
						tgActu.add(action);
					} catch (NullPointerException e3) {
						addToContent(action);
					}
				}
			}
		});
	}

	public void annulerSort() {
		Platform.runLater(() -> {
			sActu.add(new ChatAnnulation());
			sActu = null;
		});
	}

	public void startDeplacement(int idLancer, int ta) {
		Platform.runLater(() -> {
			if (sActu instanceof ChatLancerDeplacement) {
				sActu.getTop().getChatTemps().addVal((double) (ta / 100) / 10);
			} else {
				sActu = new ChatLancerDeplacement(idLancer, ta);
				tActu.add(sActu);
			}
		});
	}

	public void startSort(int idLancer, int idS, String nomSort, int ta) {
		Platform.runLater(() -> {
			sActu = new ChatLancerSort(idLancer, idS, nomSort, ta);
			tActu.add(sActu);
		});
	}

	public void startTour(int idT, int idClasse, String nomEntite, int ta) {
		Platform.runLater(() -> {
			tActu = new ChatDebutTour(idT, idClasse, nomEntite, ta);
			tgActu.add(tActu);
		});
	}

	public void endTour(int idT, int idClasse, String nomEntite) {
		Platform.runLater(() -> {
			tActu.getChildren().add(new ChatFinTour(idT, idClasse, nomEntite));
			tActu = null;
		});
	}

	public void startTourGlobal(int idTG) {
		tgActu = new ChatDebutTourGlobal(idTG);
		addToContent(tgActu);
	}

	public void endTourGlobal(int idTG) {
		Platform.runLater(() -> {
			tgActu.getChildren().add(new ChatFinTourGlobal(idTG));
			tgActu = null;
		});
	}

	public void startCombat(int compteur) {
		addToContent(new ChatDebutCombat(compteur));
	}

}
