/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * FinTourGlobal.java
 * 
 */
public class FinTourGlobal extends Tour {

	private static final long serialVersionUID = -4289465542494072495L;
	
	public final int idTG;

	public FinTourGlobal(long tempsDebut, int idTG, Action[] actions) {
		super(tempsDebut, actions);
		this.idTG = idTG;
	}

}
