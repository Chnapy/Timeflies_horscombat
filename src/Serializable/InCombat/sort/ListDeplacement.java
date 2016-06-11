/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Serializable.InCombat.InCombat;
import java.util.ArrayList;

/**
 * ListDeplacement.java
 * 
 */
public class ListDeplacement extends InCombat {

	private static final long serialVersionUID = -1177698932728390234L;
	
	public final ArrayList<Deplacement> deplacements;
	
	public ListDeplacement(ArrayList<Deplacement> deplacements) {
		this.deplacements = deplacements;
	}

}
