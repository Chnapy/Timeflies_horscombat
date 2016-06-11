/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * FinTour.java
 * 
 */
public class FinTour extends TourNormal {

	private static final long serialVersionUID = -831812837294176666L;

	public FinTour(long tempsDebut, int idTour, long idEntite, Action... actions) {
		super(tempsDebut, idTour, idEntite, actions);
	}

}
