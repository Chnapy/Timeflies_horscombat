/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.Orientation;

/**
 * Rotation.java
 *
 */
public class Rotation extends Action {

	private static final long serialVersionUID = -5196924258016363021L;

	public final Orientation direction;

	public Rotation(long idLanceur, long idCible, int idClasseSort, Orientation direction) {
		super(idLanceur, idCible, idClasseSort);
		this.direction = direction;
	}

}
