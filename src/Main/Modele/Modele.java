/*
 * 
 * 
 * 
 */
package Main.Modele;

import static Main.Controleur.MainControleur.EXEC;
import Main.Modele.Reseau.MoteurReseau;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.Log.Log.InfosCompte;
import java.io.IOException;

/**
 * Modele.java
 *
 */
public class Modele {

	public static MoteurReseau CLIENT = null;
	public static InfosCompte infosCompte = null;
	public static HCPersonnage[] persos = null;
	
	public static final void connectToServer() throws IOException, ClassNotFoundException {
		if (CLIENT != null) {
			CLIENT.close();
		}
		CLIENT = new MoteurReseau();
		EXEC.submit(CLIENT);
	}

	public static void stop() {
		try {
			CLIENT.close();
		} catch (NullPointerException ex) {

		}
	}

}
