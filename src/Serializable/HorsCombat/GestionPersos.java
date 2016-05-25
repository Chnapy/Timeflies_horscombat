/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

/**
 * GestionPersos.java
 * 
 */
public class GestionPersos implements HorsCombat {

	private static final long serialVersionUID = -6128553949337773545L;

	public static class CreaPerso extends GestionPersos {

		private static final long serialVersionUID = 2227575037241203398L;

		public final int idClasse;
		public final String nomDonne;

		public CreaPerso(int idClasse, String nomDonne) {
			this.idClasse = idClasse;
			this.nomDonne = nomDonne;
		}
	}

	public static class IdCreaPerso extends GestionPersos {

		private static final long serialVersionUID = 3865361158805853473L;

		public final int idEntite;

		public IdCreaPerso(int idEntite) {
			this.idEntite = idEntite;
		}
	}

	public static class AllClassePerso extends GestionPersos {

		private static final long serialVersionUID = -5828363628645138527L;

		public final HCPersonnage[] allPersos;

		public AllClassePerso(HCPersonnage[] allPersos) {
			this.allPersos = allPersos;
		}
	}

	public static class GetAllClassePerso extends GestionPersos {

		private static final long serialVersionUID = -728821705681770374L;

		public GetAllClassePerso() {
		}
	}

}
