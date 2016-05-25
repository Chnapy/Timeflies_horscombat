/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

import Serializable.HorsCombat.Map.MapSerializable;
import java.util.ArrayList;

/**
 * SalonCombat.java
 *
 */
public class SalonCombat implements HorsCombat {

	private static final long serialVersionUID = 3555283008499791797L;

	public static class AskCombat extends SalonCombat {

		private static final long serialVersionUID = -156415641401283422L;

		public final HorsCombat.TypeCombat type;
		public final ArrayList<Long> idPersos;

		public AskCombat(HorsCombat.TypeCombat type, ArrayList<HCPersonnage> persos) {
			this.type = type;
			this.idPersos = new ArrayList();
			for (HCPersonnage perso : persos) {
				idPersos.add(perso.id);
			}
		}

	}

	public static class EstPret extends SalonCombat {

		private static final long serialVersionUID = -9017058172066843586L;

		public final long idJoueur;
		public final boolean pret;

		public EstPret(long idJoueur, boolean pret) {
			this.idJoueur = idJoueur;
			this.pret = pret;
		}

	}

	public static class PartieTrouvee extends SalonCombat {

		private static final long serialVersionUID = 7575622563505371673L;

		public final ArrayList<HorsCombat.DonneeJoueur> donneesJoueur;
		public final MapSerializable mapS;

		public PartieTrouvee(ArrayList<HorsCombat.DonneeJoueur> donneesJoueur, MapSerializable mapS) {
			this.donneesJoueur = donneesJoueur;
			this.mapS = mapS;
		}

		public int getNbrPersos() {
			int nbr = 0;
			for (HorsCombat.DonneeJoueur dj : donneesJoueur) {
				nbr += dj.persos.size();
			}
			return nbr;
		}
	}

	public static class NewJoueur extends SalonCombat {

		private static final long serialVersionUID = 2690672833470182144L;

		public final HorsCombat.DonneeJoueur dj;

		public NewJoueur(HorsCombat.DonneeJoueur dj) {
			this.dj = dj;
		}
	}

	public static class RmJoueur extends SalonCombat {

		private static final long serialVersionUID = -212210909012130331L;

		public final long idJoueur;

		public RmJoueur(long idJoueur) {
			this.idJoueur = idJoueur;
		}
	}

	public static class LancementCombat extends SalonCombat {

		private static final long serialVersionUID = -3229558049323216851L;

		public LancementCombat() {
		}
	}

}
