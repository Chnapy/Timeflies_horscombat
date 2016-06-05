/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.InCombat;

/**
 * Tour.java
 * 
 */
public abstract class Tour extends InCombat {

	private static final long serialVersionUID = -6825864747385794766L;
	
	public final int idTourGlobal;
	public final int idTour;
	public final long idEntite;
	
	public Tour(long tempsDebut, int idTourGlobal, int idTour, long idEntite) {
		super(tempsDebut);
		this.idTourGlobal = idTourGlobal;
		this.idTour = idTour;
		this.idEntite = idEntite;
	}

}
