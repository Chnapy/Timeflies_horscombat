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
import Main.Modele.XMLLoader;
import Serializable.HorsCombat.HorsCombat;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.InCombat;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.ListInCombat;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.TypeCarac;
import Serializable.InCombat.action.AddEnvoutement;
import Serializable.InCombat.action.AlterCarac;
import Serializable.InCombat.donnee.InEntiteActive;
import Serializable.InCombat.donnee.InEntitePassive;
import Serializable.InCombat.donnee.InEquipe;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.InCombat.tour.DebutCombat;
import Serializable.InCombat.tour.DebutTour;
import Serializable.InCombat.tour.DebutTourGlobal;
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

	public static final ExecutorService EXEC = Executors.newFixedThreadPool(64);

	private static LogControleur lc;
	private static HorsCControleur hcc;
	private static InCControleur icc;

	public static void start(Stage primaryStage) {
		XMLLoader.loadDefaultLang();

		Vue.init(primaryStage);
		Vue.PRIMARYSTAGE.setOnCloseRequest((e) -> stop());

		hcc = null;
		icc = null;

//		try {
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("TestMap6.tfmap")));
//			lancementCombat(DEBUG_VUE(), (MapSerializable) ois.readObject());
//		} catch (ClassNotFoundException | IOException ex) {
//			Logger.getLogger(MainControleur.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		Vue.show();

		lc = new LogControleur();
		lc.start();
	}

	public static void logged() {
		Modele.state = MENU;
		hcc = new HorsCControleur();
		Vue.setEcran(hcc.getEcran());
		hcc.start();
	}

	public static void lancementCombat(ChargementCombat pack, MapSerializable mapS) {
		Modele.state = COMBAT;
		icc = new InCControleur(pack, mapS);
		Vue.setEcran(icc.getEcran());
		icc.start();
//		new Thread(() -> {
//			try {
//				Thread.sleep(2000);
//				Platform.runLater(() -> {
//					receiveFromServer(new Tour(0, 0));
//				});
//				Thread.sleep(5000);
//				Platform.runLater(() -> {
//					receiveFromServer(new DebutCombat(System.currentTimeMillis(),
//							new DebutTourGlobal(-1, 0, new long[]{
//						1, 2, 0, 3
//					}, new DebutTour(System.currentTimeMillis(), 0, 0)
//							)
//					));
//				});
//				Thread.sleep(5000);
//				Platform.runLater(() -> {
//					receiveFromServer(new AnnulerSort(0));
//				});
//				Thread.sleep(8000);
//				Platform.runLater(() -> {
//					ArrayList<InCombat> ar = new ArrayList();
//					ar.add(new LancerSort(System.currentTimeMillis(), 10, 1, 0, 1000, 0, new Position(3, 3),
//							new AlterCarac(1, 0, 0, TypeCarac.VITALITE, -50),
//							new AlterCarac(1, 0, 0, TypeCarac.VITESSE, -10),
//							new AlterCarac(1, 0, 0, TypeCarac.FATIGUE, 3)));
//					ar.add(new LancerSort(System.currentTimeMillis() + 00, 11, 1, 0, 1000, 0, new Position(3, 3),
//							new AlterCarac(1, 0, 0, TypeCarac.VITESSE, -10)));
//					ar.add(new LancerSort(System.currentTimeMillis() + 00, 12, 1, 0, 1000, 0, new Position(3, 3),
//							new AlterCarac(1, 0, 0, TypeCarac.FATIGUE, 3)));
//					receiveFromServer(new ListInCombat(ar));
//				});
//				Thread.sleep(5000);
//				Platform.runLater(() -> {
//					ArrayList<InSortPassif> listSP = new ArrayList();
//					listSP.add(new InSortPassif(0, 23));
//					ArrayList<InCombat> ar = new ArrayList();
//					ar.add(new Invocation(0, 0, 0, new InEntiteActive(1000, 3, 0, "TOTO", 3, new Position(0, 0), Orientation.OUEST, new CaracteristiquePhysique(120, 30000, 4000, 100, 12, 120), listSP, new ArrayList(), 500), 0, new Position(0, 0)));
//					ar.add(new AddEnvoutement(0, 1, 0, 1, 3));
//					ar.add(new AddSortPassif(1, 0, 1, 2));
//					ar.add(new Mort(1, 2, 2));
//					receiveFromServer(new ListInCombat(ar));
//				});
//				Thread.sleep(10000);
//				Platform.runLater(() -> {
//					ArrayList<InCombat> ar = new ArrayList();
////					ar.add(new FinTour(System.currentTimeMillis(), 0, 0));
////					ar.add(new DebutTour(System.currentTimeMillis(), 1, 2));
//					receiveFromServer(new ListInCombat(ar));
//				});
//			} catch (InterruptedException ex) {
//				Logger.getLogger(MainControleur.class.getName()).log(Level.SEVERE, null, ex);
//			}
//		}).start();
	}

	public static final void receiveFromServer(Object pack) {
		if (pack instanceof ChargementCombat) {
			lancementCombat((ChargementCombat) pack, hcc.getMapS());
		} else if (pack instanceof Log) {
			lc.packetRecu((Log) pack);
		} else if (pack instanceof HorsCombat) {
			hcc.packetRecu((HorsCombat) pack);
		} else if (pack instanceof InCombat) {
			icc.packetRecu((InCombat) pack);
		} else {
			System.err.println("PAQUET NON RECONNU : " + pack);
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
		if (icc != null) {
			icc.stop();
		}
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
			ArrayList<InEntitePassive> lent = new ArrayList();
			for (int ient = 0; ient < nbrEntitesParEquipes; ient++) {
				long id = (ient * ieq + ient);
				int idClasse = (int) id % 2;
				String nomDonne = "nomD" + (ient * ieq + ient);
				String nomClasse = "nomC" + (ient * ieq + ient);
				int niveau = (int) (40 * Math.random());
				Position pos = new Position((ient * ieq + ient) +11, (ient * ieq + ient) +10 + 1);
				Orientation o = Orientation.NORD;
				CaracteristiquePhysique cp = new CaracteristiquePhysique((int) (200 * Math.random()), (int) (40000), (int) (10000 * Math.random()), (int) (190 * Math.random()), (int) (20 * Math.random()), (int) (400 * Math.random()));
				ArrayList<InSortPassif> lsp = new ArrayList();
				if (ieq == 0) {
					for (int isp = 0; isp < nbrSortsPassifs; isp++) {
						lsp.add(new InSortPassif(isp, (int) (20 * Math.random())));
					}
				}
				if (ient % 2 == 0) {

					ArrayList<InSortActif> lsa = new ArrayList();
//					if (ieq == 0) {
					for (int isa = 0; isa < nbrSortsActifs; isa++) {
						lsa.add(new InSortActif(isa, (int) (20 * Math.random()), (int) (20000 * Math.random()), (int) (8 * Math.random()), (int) (15 * Math.random()), new Zone(new Carre(4, true), new Carre(2, false)), new Zone(new Carre(1, true))));
					}
//					}
					lent.add(new InEntiteActive(id, idClasse, Modele.infosCompte.idjoueur, nomDonne, niveau, pos, o, cp, lsp, lsa, 1000));
				} else {
					lent.add(new InEntitePassive(id, idClasse, Modele.infosCompte.idjoueur, niveau, pos, o, cp, lsp));
				}
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
