/*
 * 
 * 
 * 
 */
package Serializable.Log;

import Serializable.HorsCombat.HCPersonnage;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Log.java
 *
 */
public interface Log extends Serializable {

	public static class ResultVersion implements Log {

		private static final long serialVersionUID = 7156520860072377423L;

		public final boolean accepted;

		public ResultVersion(boolean accepted) {
			this.accepted = accepted;
		}

	}

	public static class CheckVersion implements Log {

		private static final long serialVersionUID = 1654574232188053283L;

		public final int[] version;

		public CheckVersion(int[] version) {
			this.version = version;
		}

	}

	public static class AskLogs implements Log {

		private static final long serialVersionUID = -648369991189613198L;

		public final String pseudo;
		public final String mdp;

		public AskLogs(String pseudo, String mdp) {
			this.pseudo = pseudo;
			this.mdp = mdp;
		}

	}

	public static class AnswerLogs implements Log {

		private static final long serialVersionUID = 1626979552067576617L;

		public final boolean accepted;
		public final InfosCompte infosCompte;
		public final HCPersonnage[] persos;
		public final HashMap<String, int[]> equipes;

		public AnswerLogs(boolean accepted, InfosCompte infosCompte, HCPersonnage[] persos, HashMap<String, int[]> equipes) {
			this.accepted = accepted;
			this.infosCompte = infosCompte;
			this.persos = persos;
			this.equipes = equipes;
		}

	}

	public static class InfosCompte implements Log {

		private static final long serialVersionUID = 328528596355496863L;

		public final long idjoueur;
		public final String pseudo;
		public int argent;
		public double ratio;
		public int niveauS;

		public InfosCompte(long idjoueur, String pseudo, int argent, double ratio) {
			this.idjoueur = idjoueur;
			this.pseudo = pseudo;
			this.argent = argent;
			this.ratio = ratio;
			this.niveauS = 0;
		}

	}

}
