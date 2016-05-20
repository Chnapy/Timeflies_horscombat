/*
 * 
 * 
 * 
 */
package Modele;

import static Controleur.MainControleur.CONTROLEUR;
import Modele.Reseau.MoteurReseau;
import static Controleur.MainControleur.EXEC;
import Serializable.Combat.InfosCombat;
import Serializable.Logs.AnswerLogs;
import Serializable.Logs.AnswerLogs.InfosCompte;
import Serializable.Personnages.HCPersonnage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Modele.java
 *
 */
public class Modele {

	public static MoteurReseau CLIENT = null;
	public static InfosCompte infosCompte = null;
	public static HCPersonnage[] persos = null;
	public static HashMap<String, int[]> equipes = null;

	public static final void connectToServer() throws IOException, ClassNotFoundException {
		if (CLIENT != null) {
			CLIENT.close();
		}
		CLIENT = new MoteurReseau();
		EXEC.submit(CLIENT);
	}

	public static final void receiveFromServer(Object pack) {
		if (pack instanceof AnswerLogs) {
			CONTROLEUR.logC.answerServeur((AnswerLogs) pack);
		} else if (pack instanceof InfosCombat) {
			CONTROLEUR.accueilC.majAttenteCombat((InfosCombat) pack);
		}
	}

}
