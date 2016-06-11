/*
 * 
 * 
 * 
 */
package Log.Controleur;

import Main.Controleur.Controleur;
import static Log.Controleur.LogControleur.LogState.BAD_LOGS;
import static Log.Controleur.LogControleur.LogState.NO_MDP;
import static Log.Controleur.LogControleur.LogState.NO_PSEUDO;
import static Log.Controleur.LogControleur.LogState.OK;
import Log.Modele.LogReseau;
import Log.Vue.LogVue;
import Main.Controleur.MainControleur;
import Main.Controleur.MainControleur.typeErreur;
import Main.Modele.Data;
import Main.Modele.Modele;
import Main.Vue.Vue;
import Serializable.Log.Log;
import Serializable.Log.Log.AnswerLogs;
import Serializable.Log.Log.ResultVersion;
import java.io.IOException;

/**
 * LogControleur.java
 *
 */
public class LogControleur extends Controleur<LogVue, Log> {

	public static enum LogState {

		OK, NO_PSEUDO, NO_MDP, BAD_LOGS;
	}

	public LogControleur() {
		super(new LogVue());
	}

	@Override
	public void start() {
		ecran.submit.setOnAction((s) -> {
			String pseudo = ecran.fPseudo.getText();
			String mdp = ecran.fMdp.getText();
			LogState verifC = verifClient(pseudo, mdp);
			ecran.submit(verifC);
		});
		ecran.root.setDisable(true);

		try {
			Modele.connectToServer();
			LogReseau.sendVersion(Data.VERSION_CLIENT);

			Vue.setEcran(ecran);
			Vue.show();
		} catch (IOException | ClassNotFoundException ex) {
			MainControleur.alertException(typeErreur.CONNEXION, ex);
			MainControleur.allStop();
		}
	}

	@Override
	public void packetRecu(Log pack) {
		if (pack instanceof ResultVersion) {
			resultVersion((ResultVersion) pack);
		} else if (pack instanceof AnswerLogs) {
			answerServeur((AnswerLogs) pack);
		}
	}

	private void resultVersion(ResultVersion pack) {
		if (pack.accepted) {
			ecran.root.setDisable(false);
		} else {
			Vue.erreur("Vous possédez une ancienne version du client."
					+ "\nVeuillez télécharger la dernière version en date.");
			MainControleur.allStop();
		}
	}

	private void answerServeur(AnswerLogs pack) {
		if (!pack.accepted) {
			ecran.submit(BAD_LOGS);
		} else {
			Modele.infosCompte = pack.infosCompte;
			Modele.persos = pack.persos;
			MainControleur.logged();
		}
	}

	private LogState verifClient(String pseudo, String mdp) {
		if (pseudo.isEmpty()) {
			return NO_PSEUDO;
		}
		if (mdp.isEmpty()) {
			return NO_MDP;
		}
		LogReseau.sendLogs(pseudo, mdp);
		return OK;
	}

}
