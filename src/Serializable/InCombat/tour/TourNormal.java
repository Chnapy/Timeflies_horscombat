/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * TourNormal.java
 * 
 */
public abstract class TourNormal extends Tour {

	private static final long serialVersionUID = 4245658260193603572L;

	public final int idTour;
	public final long idEntite;
	
	public TourNormal(long tempsDebut, int idTour, long idEntite, Action[] actions) {
		super(tempsDebut, actions);
		this.idTour = idTour;
		this.idEntite = idEntite;
	}

}
