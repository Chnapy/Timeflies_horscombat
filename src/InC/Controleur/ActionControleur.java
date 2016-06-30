/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Modele.Binding.EntiteBinding;
import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Donnees.Envoutement;
import InC.Modele.Donnees.Equipe;
import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Module.Chat.ChatCombat.ChatCombat;
import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.Grille.NotificationMap.Notification.AddEnvoutNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.AddSPNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.AlterCNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.MortNotif;
import InC.Vue.Map.Grille.NotificationMap.Notification.Notification;
import InC.Vue.Map.Grille.NotificationMap.NotificationMap;
import Main.Modele.TextManager;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.action.AddEnvoutement;
import Serializable.InCombat.action.AddSortPassif;
import Serializable.InCombat.action.AlterCarac;
import Serializable.InCombat.action.Invocation;
import Serializable.InCombat.action.Mort;
import Serializable.InCombat.action.Rotation;
import Serializable.InCombat.action.Teleportation;
import Serializable.InCombat.donnee.InEntiteActive;
import Serializable.InCombat.donnee.InEntitePassive;
import Serializable.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.application.Platform;

/**
 * ActionControleur.java
 *
 */
public class ActionControleur {

	private final InCControleur controleur;
	private final TreeMap<Long, EntitePassive> entites;
	private final ChatCombat chatCombat;
	private final ArrayList<EntiteBinding> listEntitesBinding;
	private final HashMap<Integer, Equipe> equipes;
	private final NotificationMap notifMap;

	public ActionControleur(InCControleur controleur) {
		this.controleur = controleur;
		entites = controleur.combatActu.entites;
		chatCombat = controleur.getEcran().hud.chat.chatCombat;
		listEntitesBinding = controleur.map.listEntitesBinding;
		equipes = controleur.combatActu.equipes;
		notifMap = controleur.getEcran().maps.grille.notifMap;
	}

	public void action(Action pack) {
		EntitePassive ep = entites.get(pack.idCible);
		boolean active = ep instanceof EntiteActive;
		Notification n = null;
		Position p = null;
		if (pack instanceof AlterCarac) {
//			System.out.println("ALTERCARAC: " + ((AlterCarac) pack).valeur);
			Platform.runLater(() -> {
				ep.caracs.get(((AlterCarac) pack).type).first.set(
						ep.caracs.get(((AlterCarac) pack).type).first.get()
						+ ((AlterCarac) pack).valeur
				);
			});
			n = new AlterCNotif((AlterCarac) pack);
			p = AbstractMap.getRealTilePos(
					entites.get(pack.idCible)
					.getBinding().position.get()
			);
			chatCombat.addActionAlterCarac(ep.idClasse,
					active ? ((EntiteActive) ep).nomDonne.get() : ep.nomClasse.get(),
					active, ((AlterCarac) pack).type, ((AlterCarac) pack).valeur);
		} else if (pack instanceof Rotation) {
			ep.getBinding().orientation.set(((Rotation) pack).direction);
		} else if (pack instanceof Teleportation) {
			ep.getBinding().position.set(((Teleportation) pack).posCible);
		} else if (pack instanceof AddEnvoutement) {
			ep.envoutements.add(new Envoutement(((AddEnvoutement) pack).idClasseSort,
					((AddEnvoutement) pack).nbrTours));
			n = new AddEnvoutNotif((AddEnvoutement) pack);
			p = AbstractMap.getRealTilePos(
					entites.get(pack.idCible)
					.getBinding().position.get()
			);
			chatCombat.addActionAddEnvoutement(ep.idClasse,
					active ? ((EntiteActive) ep).nomDonne.get() : ep.nomClasse.get(),
					active, ((AddEnvoutement) pack).idClasseEnvoutement,
					TextManager.getSortName(((AddEnvoutement) pack).idClasseEnvoutement).get(),
					((AddEnvoutement) pack).nbrTours);
		} else if (pack instanceof AddSortPassif) {
			ep.sortsP.putIfAbsent(((AddSortPassif) pack).idClasseSP,
					new SortPassif(((AddSortPassif) pack).idClasseSP, -1));
			n = new AddSPNotif((AddSortPassif) pack);
			p = AbstractMap.getRealTilePos(
					entites.get(pack.idCible)
					.getBinding().position.get()
			);
			chatCombat.addActionDeclencherSortPassif(ep.idClasse,
					active ? ((EntiteActive) ep).nomDonne.get() : ep.nomClasse.get(),
					active, ((AddSortPassif) pack).idClasseSP,
					TextManager.getSortName(((AddSortPassif) pack).idClasseSP).get());
		} else if (pack instanceof Mort) {
			ep.getBinding().alive.set(false);
			listEntitesBinding.remove(ep.getBinding());
			n = new MortNotif((Mort) pack);
			p = AbstractMap.getRealTilePos(
					entites.get(pack.idCible)
					.getBinding().position.get()
			);
			chatCombat.addActionMort(ep.idClasse,
					active ? ((EntiteActive) ep).nomDonne.get() : ep.nomClasse.get(),
					active);
		} else if (pack instanceof Invocation) {
			InEntitePassive iep = ((Invocation) pack).invoc;
			Equipe equipe = equipes.get(((Invocation) pack).numeroEquipe);
			EntitePassive e;
			if (iep instanceof InEntiteActive) {
				e = new EntiteActive((InEntiteActive) iep, equipe, ((InEntiteActive) iep).tempsDeplacement);
			} else {
				e = new EntitePassive(iep, equipe);
			}
			controleur.ajoutEntite(e);
			chatCombat.addActionInvocation(iep.idClasse, e.nomClasse.get(), e instanceof EntiteActive);
		}
		if (n != null) {
			notifMap.notifierAction(n, p);
		}
	}
}
