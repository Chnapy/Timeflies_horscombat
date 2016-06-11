/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

import Serializable.InCombat.InCombat;
import java.util.ArrayList;

/**
 * ListActions.java
 * 
 */
public class ListActions extends InCombat {

	private static final long serialVersionUID = 3561041120840062857L;
	
	public final ArrayList<Action> actions;

	public ListActions(ArrayList<Action> actions) {
		this.actions = actions;
	}

}
