/*
 * 
 * 
 * 
 */
package Log.Modele;

import Main.Controleur.MainControleur;
import static Main.Modele.Modele.CLIENT;
import Serializable.Log.Log.AskLogs;
import Serializable.Log.Log.CheckVersion;

/**
 * LogReseau.java
 * 
 */
public class LogReseau {

	public static boolean sendVersion(int[] version) {
		try {
			CheckVersion cv = new CheckVersion(version);
			CLIENT.send(cv);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

	public static boolean sendLogs(String pseudo, String mdp) {
		try {
			AskLogs logs = new AskLogs(pseudo, mdp);
			CLIENT.send(logs);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

}
