/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.Position;

/**
 * Teleportation.java
 * 
 */
public class Teleportation extends Action {

	private static final long serialVersionUID = 6422033694607203031L;

	public final Position posCible;

	public Teleportation(long idLanceur, long idCible, int idClasseSort, Position posCible) {
		super(idLanceur, idCible, idClasseSort);
		this.posCible = posCible;
	}
}
