/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import Serializable.InCombat.donnee.InEquipe;

/**
 * Equipe.java
 * 
 */
public class Equipe {
	
	public final int numero;
	public final String colorCode;
	
	public Equipe(InEquipe ie) {
		this(ie.numero, ie.colorCode);
	}
	
	public Equipe(int numero, String colorCode) {
		this.numero = numero;
		this.colorCode = colorCode;
	}

}
