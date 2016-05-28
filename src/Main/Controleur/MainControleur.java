/*
 * 
 * 
 * 
 */
package Main.Controleur;

import HorsC.Controleur.HorsCControleur;
import InC.Controleur.InCControleur;
import Main.Vue.Vue;
import Log.Controleur.LogControleur;
import static Main.Modele.Data.DEBUG;
import Main.Modele.Modele;
import Serializable.HorsCombat.HorsCombat;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.DebutCombat;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.donnee.InEntite;
import Serializable.InCombat.donnee.InEquipe;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;
import Serializable.Log.Log;
import Serializable.Position;
import java.util.ArrayList;
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
	private static InCControleur icc;

	public static void start(Stage primaryStage) {
		Vue.init(primaryStage);
		Vue.PRIMARYSTAGE.setOnCloseRequest((e) -> {
			stop();
		});

		hcc = null;
		icc = null;

		lancementCombat(DEBUG_VUE());
		Vue.show();

//		lc = new LogControleur();
//		lc.start();
	}

	public static void logged() {

		hcc = new HorsCControleur();
		Vue.setEcran(hcc.getEcran());
		hcc.start();
	}

	public static void lancementCombat(DebutCombat pack) {
		icc = new InCControleur();
		Vue.setEcran(icc.getEcran());
		icc.start();
	}

	public static final void receiveFromServer(Object pack) {
		if (pack instanceof Log) {
			lc.packetRecu(pack);
		} else if (pack instanceof HorsCombat || pack instanceof DebutCombat) {
			hcc.packetRecu(pack);
		} else if (pack instanceof InCombat) {
			icc.packetRecu(pack);
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

	private static DebutCombat DEBUG_VUE() {
		int nbrEquipes = 3;
		int nbrEntitesParEquipes = 2;
		int nbrSortsActifs = 4;
		int nbrSortsPassifs = 3;

		ArrayList<InEquipe> leq = new ArrayList();
		for (int ieq = 0; ieq < nbrEquipes; ieq++) {
			ArrayList<InEntite> lent = new ArrayList();
			for (int ient = 0; ient < nbrEntitesParEquipes; ient++) {
				long id = (ient * ieq + ient);
				String nomDonne = "nomD" + (ient * ieq + ient);
				String nomClasse = "nomC" + (ient * ieq + ient);
				int niveau = (int) (40 * Math.random());
				Position pos = new Position((ient * ieq + ient), (ient * ieq + ient));
				Orientation o = Orientation.NORD;
				CaracteristiquePhysique cp = new CaracteristiquePhysique((int) (200 * Math.random()), (int) (40000 * Math.random()), (int) (10000 * Math.random()), (int) (190 * Math.random()), (int) (20 * Math.random()), (int) (400 * Math.random()));
				ArrayList<InSortActif> lsa = new ArrayList();
				for (int isa = 0; isa < nbrSortsActifs; isa++) {
					lsa.add(new InSortActif(isa, "nomSA" + (ient * ieq + ient) * isa, "descSA" + (ient * ieq + ient) * isa, (int) (20000 * Math.random()), (int) (3 * Math.random()), (int) (15 * Math.random()), new Zone(new Carre(5, true), new Carre(2, false)), new Zone(new Carre(2, true))));
				}
				ArrayList<InSortPassif> lsp = new ArrayList();
				for (int isp = 0; isp < nbrSortsPassifs; isp++) {
					lsp.add(new InSortPassif(isp, "nomSP" + (ient * ieq + ient) * isp, "descSP" + (ient * ieq + ient) * isp));
				}
				lent.add(new InEntite(id, nomDonne, nomClasse, niveau, pos, o, cp, lsp, lsa));
			}
			leq.add(new InEquipe(ieq, (int) (100 * Math.random()) + "" + (int) (100 * Math.random()) + "" + (int) (100 * Math.random()), lent));
		}
		return new DebutCombat(System.currentTimeMillis(), 5000, leq);
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
