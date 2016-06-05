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
import static Main.Modele.Modele.ClientState.COMBAT;
import static Main.Modele.Modele.ClientState.MENU;
import Serializable.HorsCombat.HorsCombat;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.TypeCarac;
import Serializable.InCombat.action.AlterCarac;
import Serializable.InCombat.donnee.InEntite;
import Serializable.InCombat.donnee.InEquipe;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.zone.Carre;
import Serializable.InCombat.zone.Zone;
import Serializable.Log.Log;
import Serializable.Position;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("TestMap5.tfmap")));
			lancementCombat(DEBUG_VUE(), (MapSerializable) ois.readObject());
		} catch (ClassNotFoundException | IOException ex) {
			Logger.getLogger(MainControleur.class.getName()).log(Level.SEVERE, null, ex);
		}
		Vue.show();

//		lc = new LogControleur();
//		lc.start();
	}

	public static void logged() {
		Modele.state = MENU;
		hcc = new HorsCControleur();
		Vue.setEcran(hcc.getEcran());
		hcc.start();
	}

	public static void lancementCombat(ChargementCombat pack, MapSerializable mapS) {
		Modele.state = COMBAT;
		icc = new InCControleur();
		Vue.setEcran(icc.getEcran());
		icc.packetRecu(pack);
		icc.setMap(mapS);
		icc.start();
		new Thread(() -> {
			try {
//				Thread.sleep(2000);
//				Platform.runLater(() -> {
//					receiveFromServer(new Tour(0, 0));
//				});
//				Thread.sleep(5000);
				Platform.runLater(() -> {
					receiveFromServer(new DebutCombat(System.currentTimeMillis() + 5000,
							0, 0, 0));
				});
				Thread.sleep(8000);
				Platform.runLater(() -> {
					receiveFromServer(new AlterCarac(0, TypeCarac.VITALITE, -20));
				});
//				Platform.runLater(() -> {
//					receiveFromServer(new FinTour(1, 1));
//					receiveFromServer(new Tour(2, 2));
//				});
//				Thread.sleep(5000);
//				Platform.runLater(() -> {
//					receiveFromServer(new FinTour(2, 2));
//					receiveFromServer(new Tour(3, 3));
//				});
			} catch (InterruptedException ex) {
				Logger.getLogger(MainControleur.class.getName()).log(Level.SEVERE, null, ex);
			}
		}).start();
	}

	public static final void receiveFromServer(Object pack) {
		if (pack instanceof Log) {
			lc.packetRecu(pack);
		} else if (pack instanceof HorsCombat || pack instanceof ChargementCombat) {
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

	private static ChargementCombat DEBUG_VUE() {
		int nbrEquipes = 3;
		int nbrEntitesParEquipes = 2;
		int nbrSortsActifs = 4;
		int nbrSortsPassifs = 3;

		Modele.infosCompte = new Log.InfosCompte(0, "monPseudo", 0, 1);
		ArrayList<InEquipe> leq = new ArrayList();
		for (int ieq = 0; ieq < nbrEquipes; ieq++) {
			ArrayList<InEntite> lent = new ArrayList();
			for (int ient = 0; ient < nbrEntitesParEquipes; ient++) {
				long id = (ient * ieq + ient);
				int idClasse = (int) id % 2;
				String nomDonne = "nomD" + (ient * ieq + ient);
				String nomClasse = "nomC" + (ient * ieq + ient);
				int niveau = (int) (40 * Math.random());
				Position pos = new Position((ient * ieq + ient), (ient * ieq + ient) + 1);
				Orientation o = Orientation.NORD;
				CaracteristiquePhysique cp = new CaracteristiquePhysique((int) (200 * Math.random()), (int) (40000 * Math.random()), (int) (10000 * Math.random()), (int) (190 * Math.random()), (int) (20 * Math.random()), (int) (400 * Math.random()));
				ArrayList<InSortActif> lsa = new ArrayList();
				for (int isa = 0; isa < nbrSortsActifs; isa++) {
					lsa.add(new InSortActif(isa, "nomSA" + (ient * ieq + ient) * isa, "descSA" + (ient * ieq + ient) * isa, (int) (20 * Math.random()), (int) (20000 * Math.random()), (int) (3 * Math.random()), (int) (15 * Math.random()), new Zone(new Carre(4, true), new Carre(2, false)), new Zone(new Carre(1, true))));
				}
				ArrayList<InSortPassif> lsp = new ArrayList();
				for (int isp = 0; isp < nbrSortsPassifs; isp++) {
					lsp.add(new InSortPassif(isp, "nomSP" + (ient * ieq + ient) * isp, "descSP" + (ient * ieq + ient) * isp, (int) (20 * Math.random())));
				}
				lent.add(new InEntite(id, idClasse, Modele.infosCompte.idjoueur, nomDonne, nomClasse, niveau, pos, o, cp, lsp, lsa));
			}
			leq.add(new InEquipe(ieq, (int) (10 * Math.random()) + "0" + (int) (10 * Math.random()) + "0" + (int) (10 * Math.random()) + "0", lent));
		}
		return new ChargementCombat(leq);
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
