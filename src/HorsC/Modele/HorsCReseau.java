/*
 * 
 * 
 * 
 */
package HorsC.Modele;

import Main.Controleur.MainControleur;
import Main.Modele.Modele;
import static Main.Modele.Modele.CLIENT;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.HorsCombat.GestionPersos.CreaPerso;
import Serializable.HorsCombat.GestionPersos.GetAllClassePerso;
import Serializable.HorsCombat.SalonCombat.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * HorsCReseau.java
 *
 */
public class HorsCReseau {
	
	public static boolean sendQuitSalon(long idJoueur) {
		try {
			RmJoueur pack = new RmJoueur(idJoueur);
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}
	
	public static boolean sendCreaPerso(int idClasse, String nomDonne) {
		try {
			CreaPerso pack = new CreaPerso(idClasse, nomDonne);
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}
	
	public static boolean sendGetAllClassePerso() {
		try {
			GetAllClassePerso pack = new GetAllClassePerso();
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}
	
	public static boolean sendPret(boolean pret) {
		try {
			EstPret pack = new EstPret(Modele.infosCompte.idjoueur, pret);
			CLIENT.send(pack);
			return true;
		} catch (IOException ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

	public static boolean sendReqSalon(AskCombat.TypeCombat type, ArrayList<HCPersonnage> persos) {
		try {
			AskCombat pack = new AskCombat(type, persos);
			CLIENT.send(pack);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

}
