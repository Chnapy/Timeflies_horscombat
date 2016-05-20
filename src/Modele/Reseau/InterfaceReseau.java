/*
 * 
 * 
 * 
 */
package Modele.Reseau;

import Controleur.MainControleur;
import static Modele.Modele.CLIENT;
import Serializable.Combat.AskCombat;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import java.util.ArrayList;

/**
 * InterfaceReseau.java
 *
 */
public class InterfaceReseau {

	public static boolean sendLogs(String pseudo, String mdp) {
		try {
			AskLogs logs = new AskLogs(pseudo, mdp);
			CLIENT.send(logs);
			return true;
		} catch (Exception ex) {
			MainControleur.CONTROLEUR.erreur(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

	public static boolean sendReqCombat(AskCombat.TypeCombat type, ArrayList<HCPersonnage> persos) {
		try {
			AskCombat pack = new AskCombat(type, persos);
			CLIENT.send(pack);
			return true;
		} catch (Exception ex) {
			MainControleur.CONTROLEUR.erreur(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

}
