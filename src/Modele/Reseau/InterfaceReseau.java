/*
 * 
 * 
 * 
 */
package Modele.Reseau;

import Controleur.MainControleur;
import Modele.Modele;
import static Modele.Modele.CLIENT;
import Serializable.Combat.AskCombat;
import Serializable.Combat.InfosCombat.CreaPerso;
import Serializable.Combat.InfosCombat.EstPret;
import Serializable.Combat.InfosCombat.GetAllClassePerso;
import Serializable.Logs.AskLogs;
import Serializable.Personnages.HCPersonnage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * InterfaceReseau.java
 *
 */
public class InterfaceReseau {
	
	public static boolean sendCreaPerso(int idClasse, String nomDonne) {
		try {
			CreaPerso pack = new CreaPerso(idClasse, nomDonne);
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.CONTROLEUR.erreur(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}
	
	public static boolean sendGetAllClassePerso() {
		try {
			GetAllClassePerso pack = new GetAllClassePerso();
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.CONTROLEUR.erreur(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}
	
	public static boolean sendPret(boolean pret) {
		try {
			EstPret pack = new EstPret(Modele.infosCompte.idjoueur, pret);
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.CONTROLEUR.erreur(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

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
