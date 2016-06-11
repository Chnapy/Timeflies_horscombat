/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.InCombat;
import Serializable.InCombat.action.Action;

/**
 * Tour.java
 * 
 */
public abstract class Tour extends InCombat {

	private static final long serialVersionUID = -6825864747385794766L;
	
	public final Action[] actions;
	
	public Tour(long tempsDebut, Action[] actions) {
		super(tempsDebut);
		this.actions = actions;
	}

}
