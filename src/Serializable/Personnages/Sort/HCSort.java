/*
 * 
 * 
 * 
 */
package Serializable.Personnages.Sort;

import java.io.Serializable;

/**
 * HCSort.java
 *
 */
public abstract class HCSort implements Serializable {

	private static final long serialVersionUID = 3238941071137920942L;

	public final String nom;
	public final String description;
	public final HCNiveau niveau;

	public HCSort(String nom, HCNiveau niveau, String description) {
		this.nom = nom;
		this.description = description;
		this.niveau = niveau;
	}

	public static class HCNiveau implements Serializable {

		private static final long serialVersionUID = -5702122673495080557L;

		public final int niveau;
		public final int xp;

		public HCNiveau(int niveau, int xp) {
			this.niveau = niveau;
			this.xp = xp;
		}
	}

}
