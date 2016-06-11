/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.donnee.InEntitePassive;
import Serializable.Position;

/**
 * Invocation.java
 *
 */
public class Invocation extends Action {

	private static final long serialVersionUID = 3437453879316093770L;

	public final InEntitePassive invoc;
	public final int numeroEquipe;
	public final Position posCible;

	public Invocation(long idLanceur, long idCible, int idClasseSort,
			InEntitePassive invoc, int numeroEquipe, Position posCible) {
		super(idLanceur, idCible, idClasseSort);
		this.invoc = invoc;
		this.numeroEquipe = numeroEquipe;
		this.posCible = posCible;
	}

}
