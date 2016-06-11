/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * DebutCombat.java
 * 
 */
public class DebutCombat extends Tour {

	private static final long serialVersionUID = -6443543461012095382L;
	
	public final DebutTourGlobal dtg;

	public DebutCombat(long tempsDebut, DebutTourGlobal dtg, Action... actions) {
		super(tempsDebut, actions);
		this.dtg = dtg;
	}

}
