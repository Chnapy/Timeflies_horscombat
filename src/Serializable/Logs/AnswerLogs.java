/*
 * 
 * 
 * 
 */
package Serializable.Logs;

import Serializable.Personnages.HCPersonnage;
import java.io.Serializable;
import java.util.HashMap;

/**
 * AnswerLogs.java
 *
 */
public class AnswerLogs implements Serializable {

	private static final long serialVersionUID = 8290171349877351705L;

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

	public static class InfosCompte implements Serializable {

		private static final long serialVersionUID = 1253542471421658761L;

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
