/*
 * 
 * 
 * 
 */
package Serializable.Combat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * InfosCombat.java
 *
 */
public abstract class InfosCombat implements Serializable {

	private static final long serialVersionUID = 625782106078776094L;

	public static class PartieTrouvee extends InfosCombat {

		private static final long serialVersionUID = 3135149435124340248L;

		public final ArrayList<DonneeJoueur> donneesJoueur;
		public final String nomMap;

		public PartieTrouvee(ArrayList<DonneeJoueur> donneesJoueur, String nomMap) {
			this.donneesJoueur = donneesJoueur;
			this.nomMap = nomMap;
		}

		public int getNbrPersos() {
			int nbr = 0;
			for (DonneeJoueur dj : donneesJoueur) {
				nbr += dj.persos.size();
			}
			return nbr;
		}
	}

	public static class NewJoueur extends InfosCombat {

		private static final long serialVersionUID = 3131655523732648959L;

		public final DonneeJoueur dj;

		public NewJoueur(DonneeJoueur dj) {
			this.dj = dj;
		}
	}

	public static class LancementCombat extends InfosCombat {

		private static final long serialVersionUID = 1147765223531782044L;

		public LancementCombat() {
		}
	}

	public static class DonneeJoueur extends InfosCombat {

		private static final long serialVersionUID = 8981162716022575215L;

		public final int niveau;
		public final String nom;
		public final boolean pret;
		public final ArrayList<DonneePerso> persos;

		public DonneeJoueur(int niveau, String nom, boolean pret, ArrayList<DonneePerso> persos) {
			this.niveau = niveau;
			this.nom = nom;
			this.pret = pret;
			this.persos = persos;
		}

	}

	public static class DonneePerso extends InfosCombat {

		private static final long serialVersionUID = 8527592662289348136L;

		public final int niveau;
		public final String nomClasse;

		public DonneePerso(int niveau, String nomClasse) {
			this.niveau = niveau;
			this.nomClasse = nomClasse;
		}

	}

}
