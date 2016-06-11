/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * DebutTour.java
 * 
 */
public class DebutTour extends TourNormal {

	private static final long serialVersionUID = 5086470951119041686L;

	public DebutTour(long tempsDebut, int idTour, long idEntite, Action... actions) {
		super(tempsDebut, idTour, idEntite, actions);
	}

}
