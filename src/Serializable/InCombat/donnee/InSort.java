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
	public final int niveau;

	public InSort(int idClasseSort, int niveau) {
		this.idClasseSort = idClasseSort;
		this.niveau = niveau;
	}

}
