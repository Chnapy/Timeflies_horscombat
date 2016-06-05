/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.donnee.InEntite;
import Serializable.Position;

/**
 * Invocation.java
 * 
 */
public class Invocation extends Action {

	private static final long serialVersionUID = 3437453879316093770L;
	
	public final InEntite invoc;
	public final Position posCible;

	public Invocation(long idCible, InEntite invoc, Position posCible) {
		super(idCible);
		this.invoc = invoc;
		this.posCible = posCible;
	}

}
