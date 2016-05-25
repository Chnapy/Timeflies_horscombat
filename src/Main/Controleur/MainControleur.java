/*
 * 
 * 
 * 
 */
package Main.Controleur;

import HorsC.Controleur.HorsCControleur;
import Main.Vue.Vue;
import Log.Controleur.LogControleur;
import static Main.Modele.Data.DEBUG;
import Main.Modele.Modele;
import Serializable.HorsCombat.HorsCombat;
import Serializable.Log.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.stage.Stage;

/**
 * MainControleur.java
 *
 */
public class MainControleur {

	public static final ExecutorService EXEC = Executors.newFixedThreadPool(16);

	private static LogControleur lc;
	private static HorsCControleur hcc;

	public static void start(Stage primaryStage) {
		Vue.init(primaryStage);
		Vue.PRIMARYSTAGE.setOnCloseRequest((e) -> {
			stop();
		});

		lc = new LogControleur();
		lc.start();
	}

	public static void logged() {

		hcc = new HorsCControleur();
		Vue.setEcran(hcc.getEcran());
		hcc.start();
	}

	public static void lancementCombat() {
	}

	public static final void receiveFromServer(Object pack) {
		if (pack instanceof Log) {
			lc.packetRecu(pack);
		} else if(pack instanceof HorsCombat) {
			hcc.packetRecu(pack);
		}
	}
	
	public static void allStop() {
		Vue.PRIMARYSTAGE.close();
		stop();
	}

	private static void stop() {
		System.out.println("Arret client");
		Modele.stop();
		EXEC.shutdown();
	}

	public static void alertInfo(String message) {
		Vue.info(message);
	}

	public static void alertException(typeErreur message, Exception ex) {
		alertException(message.message, ex);
	}

	public static void alertException(String message, Exception ex) {
		Vue.exception(message, ex);
		System.err.println(message);
		if (DEBUG) {
			ex.printStackTrace();
		}
	}

	public static enum typeErreur {

		CONNEXION("Connexion au serveur impossible.\n"
				+ "Le serveur est peut-être éteint.\n\n"
				+ "Vérifiez votre connexion internet et votre pare-feu, puis relancez le jeu."),
		SEND("Envoi impossible, vérifiez votre connexion internet"),
		FILE("Fichier recherché inexistant");

		public final String message;

		typeErreur(String message) {
			this.message = message;
		}
	}

}
