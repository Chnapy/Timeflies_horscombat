/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Serializable.InCombat.InCombat;
import Serializable.InCombat.action.Action;

/**
 * ActionsToSort.java
 * 
 */
public class ActionsToSort extends InCombat {

	private static final long serialVersionUID = -7535580618795151924L;
	
	public final int idActionSort;
	public final Action[] actions;
	
	public ActionsToSort(int idActionSort, Action[] actions) {
		this.idActionSort = idActionSort;
		this.actions = actions;
	}

}
