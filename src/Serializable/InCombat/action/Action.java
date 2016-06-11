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
	
	public final long idLanceur;
	public final long idCible;
	public final int idClasseSort;
	
	public Action(long idLanceur, long idCible, int idClasseSort) {
		this.idLanceur = idLanceur;
		this.idCible = idCible;
		this.idClasseSort = idClasseSort;
	}

}
