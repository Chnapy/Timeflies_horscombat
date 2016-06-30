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

	public DebutCombat(long tempsDebut, Action... actions) {
		super(tempsDebut, actions);
	}

}
