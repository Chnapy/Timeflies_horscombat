/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.InCombat;

/**
 * Action.java
 * 
 */
public abstract class Action extends InCombat {

	private static final long serialVersionUID = -4005566963773711633L;
	
	public final long idCible;
	
	public Action(long idCible) {
		this.idCible = idCible;
	}

}
