/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import java.util.ArrayList;

/**
 * ListInCombat.java
 * 
 */
public class ListInCombat extends InCombat {

	private static final long serialVersionUID = -4601101763380362641L;
	
	public final ArrayList<InCombat> listInCombat;

	public ListInCombat(ArrayList<InCombat> listInCombat) {
		this.listInCombat = listInCombat;
	}

}
