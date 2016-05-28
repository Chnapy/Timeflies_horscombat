/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import Serializable.Position;

/**
 * LancerSort.java
 * 
 */
public class LancerSort extends InCombat {

	private static final long serialVersionUID = 1668810283786981565L;
	
	public final int idClasseSort;
	public final long idEntite;
	public final int tour;
	public final Position position;

	public LancerSort(int idClasseSort, long idEntite, int tour, Position position) {
		this.idClasseSort = idClasseSort;
		this.idEntite = idEntite;
		this.tour = tour;
		this.position = position;
	}

}
