/*
 * 
 * 
 * 
 */
package Serializable.InCombat.donnee;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * InEquipe.java
 * 
 */
public class InEquipe implements Serializable {

	private static final long serialVersionUID = 8693398529715688180L;
	
	public final int numero;
	public final String colorCode;
	public final ArrayList<InEntitePassive> listInEntites;

	public InEquipe(int numero, String colorCode, ArrayList<InEntitePassive> listInEntites) {
		this.numero = numero;
		this.colorCode = colorCode;
		this.listInEntites = listInEntites;
	}

}
