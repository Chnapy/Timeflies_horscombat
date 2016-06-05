/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import Serializable.InCombat.donnee.InSortPassif;

/**
 * SortPassif.java
 * 
 */
public class SortPassif {
	
	public final int idClasse;
	public final String nom;
	public final String description;
	public final int niveau;

	public SortPassif(InSortPassif sp) {
		this(sp.idClasseSort, sp.nom, sp.description, sp.niveau);
	}

	public SortPassif(int idClasse, String nom, String description, int niveau) {
		this.idClasse = idClasse;
		this.nom = nom;
		this.description = description;
		this.niveau = niveau;
	}

}
