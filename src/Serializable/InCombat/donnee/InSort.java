/*
 * 
 * 
 * 
 */
package Serializable.InCombat.donnee;

import java.io.Serializable;

/**
 * InSort.java
 * 
 */
public abstract class InSort implements Serializable {

	private static final long serialVersionUID = -2444559369259199240L;
	
	public final int idClasseSort;
	public final String nom;
	public final String description;
	public final int niveau;

	public InSort(int idClasseSort, String nom, String description, int niveau) {
		this.idClasseSort = idClasseSort;
		this.nom = nom;
		this.description = description;
		this.niveau = niveau;
	}

}
