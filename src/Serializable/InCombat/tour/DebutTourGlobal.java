/*
 * 
 * 
 * 
 */
package Serializable.InCombat.tour;

import Serializable.InCombat.action.Action;

/**
 * DebutTourGlobal.java
 * 
 */
public class DebutTourGlobal extends Tour {

	private static final long serialVersionUID = -187989802752503843L;
	
	public final int idTG;
	public final long[] ordreJeu;

	public DebutTourGlobal(long tempsDebut, int idTG, long[] ordreJeu, Action... actions) {
		super(tempsDebut, actions);
		this.idTG = idTG;
		this.ordreJeu = ordreJeu;
	}
	

}
