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

	public Rotation(long idCible, Orientation direction) {
		super(idCible);
		this.direction = direction;
	}

}
