/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.TypeCarac;

/**
 * AlterCarac.java
 * 
 */
public class AlterCarac extends Action {

	private static final long serialVersionUID = 8567688624423757559L;
	
	public final TypeCarac type;
	public final int valeur;

	public AlterCarac(long idCible, TypeCarac type, int valeur) {
		super(idCible);
		this.type = type;
		this.valeur = valeur;
	}

}
