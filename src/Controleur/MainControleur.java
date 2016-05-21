/*
 * 
 * 
 * 
 */
package Controleur;

import Modele.Modele;
import Serializable.Logs.AnswerLogs;
import Vue.Vue;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainControleur.java
 *
 */
public class MainControleur {

	public static final boolean DEBUG = true;
	private static final Runtime RUN = Runtime.getRuntime();
	private static final String[] CMD = {"java", "-jar", "tfCombat.jar"};
	public static final ExecutorService EXEC = Executors.newFixedThreadPool(16);
	public static MainControleur CONTROLEUR;

	private final Vue vue;

	public final LogControleur logC;
	public final AccueilControleur accueilC;

	public MainControleur(Vue vue) {
		CONTROLEUR = this;
		this.vue = vue;

		logC = new LogControleur(vue.logScene);
		accueilC = new AccueilControleur(vue.pagePrincipale);
		
		vue.primaryStage.setOnCloseRequest((e) -> {
			stop();
		});
	}
	
	public void stop() {
		System.out.println("Arret client");
		vue.stop();
		Modele.stop();
		EXEC.shutdown();
	}

	public void start() {
		logC.start();
		vue.show();
	}

	public void logged(AnswerLogs pack) {
		Modele.infosCompte = pack.infosCompte;
		Modele.persos = pack.persos;
		Modele.equipes = pack.equipes;
		vue.setEcran(vue.pagePrincipale);
		accueilC.start();
	}

	public void erreur(typeErreur message, Exception ex) {
		System.err.println(message.message);
		if (DEBUG) {
			ex.printStackTrace();
		}
	}

	public void lancementCombat() {
		try {
			Process p = RUN.exec(CMD);
		} catch (IOException ex) {
			erreur(typeErreur.FILE, ex);
		}
	}

	public static enum typeErreur {

		SEND("Envoi impossible, vérifiez votre connexion internet"),
		FILE("Fichier recherché inexistant");

		public final String message;

		typeErreur(String message) {
			this.message = message;
		}
	}

}
