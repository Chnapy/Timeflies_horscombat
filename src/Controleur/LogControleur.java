/*
 * 
 * 
 * 
 */
package Controleur;

import static Controleur.MainControleur.CONTROLEUR;
import static Controleur.LogControleur.LogState.BAD_LOGS;
import static Controleur.LogControleur.LogState.NO_CONNECTION;
import static Controleur.LogControleur.LogState.NO_MDP;
import static Controleur.LogControleur.LogState.NO_PSEUDO;
import static Controleur.LogControleur.LogState.OK;
import Controleur.MainControleur.typeErreur;
import Modele.Modele;
import Modele.Reseau.InterfaceReseau;
import Serializable.Logs.AnswerLogs;
import Serializable.Personnages.HCPersonnage;
import Serializable.Personnages.Sort.HCSort.HCNiveau;
import Serializable.Personnages.Sort.HCSortActif;
import Serializable.Personnages.Sort.HCSortPassif;
import Vue.LogVue;
import java.io.IOException;
import java.util.HashMap;

/**
 * LogControleur.java
 *
 */
public class LogControleur extends Controleur<LogVue> {

	public static enum LogState {

		OK, NO_PSEUDO, NO_MDP, NO_CONNECTION, BAD_LOGS;
	}

	public LogControleur(LogVue logScene) {
		super(logScene);
	}

	@Override
	public void start() {
		ecran.submit.setOnAction((s) -> {
			String pseudo = ecran.fPseudo.getText();
			String mdp = ecran.fMdp.getText();
			LogState verifC = verifClient(pseudo, mdp);
			ecran.submit(verifC);
		});
	}

	private LogState verifClient(String pseudo, String mdp) {
		if (pseudo.isEmpty()) {
			return NO_PSEUDO;
		}
		if (mdp.isEmpty()) {
			return NO_MDP;
		}
		try {
			Modele.connectToServer();
		} catch (IOException | ClassNotFoundException ex) {
			CONTROLEUR.erreur(typeErreur.SEND, ex);
			return NO_CONNECTION;
		}
		InterfaceReseau.sendLogs(pseudo, mdp);
		return OK;
	}

	public void answerServeur(AnswerLogs pack) {
		if (!pack.accepted) {
			ecran.submit(BAD_LOGS);
		} else {
			CONTROLEUR.logged(pack);
		}
	}

	private final AnswerLogs getExamplePack() {
		boolean accepted = true;
		String pseudo = "TestPseudo";
		int argent = 4578;
		double ratio = 1.8;

		HCSortActif sa1 = new HCSortActif("TestSA1", new HCNiveau(5, 214), "Description de TestSA1", 1500, 0, 0);
		HCSortActif sa2 = new HCSortActif("TestSA2", new HCNiveau(2, 85), "Description de TestSA2", 1900, 2, 0);
		HCSortActif sa3 = new HCSortActif("TestSA3", new HCNiveau(8, 785), "Description de TestSA3", 1100, 1, 0);

		HCSortPassif sp1 = new HCSortPassif("TestSP1", new HCNiveau(4, 187), "Description de TestSP1");
		HCSortPassif sp2 = new HCSortPassif("TestSP2", new HCNiveau(0, 12), "Description de TestSP2");

		HCPersonnage perso1 = new HCPersonnage(12, 0, "Perso1", "Classe1", 8, 125, 38000, 5000, 100, 0,
				new HCSortActif[]{sa1, sa2, sa3}, new HCSortPassif[]{sp1, sp2});
		HCPersonnage perso2 = new HCPersonnage(13, 0, "Perso2", "Classe2", 2, 120, 35000, 4000, 110, 10,
				new HCSortActif[]{sa3}, new HCSortPassif[]{sp2});

		HashMap<String, int[]> equipes = new HashMap();
		equipes.put("Equipe1", new int[]{1, 2});
		equipes.put("Equipe2", new int[]{2});

		return new AnswerLogs(accepted, new AnswerLogs.InfosCompte(13, pseudo, argent, ratio), new HCPersonnage[]{
			perso1, perso2
		}, equipes);
	}

}
