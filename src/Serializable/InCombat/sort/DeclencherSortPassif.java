/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Serializable.InCombat.InCombat;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.donnee.InSortPassif;

/**
 * DeclencherSortPassif.java
 *
 */
public class DeclencherSortPassif extends InCombat {

	private static final long serialVersionUID = 3795668699829173712L;

	public final int idClasseSort;
	public final long idEntite;
	public final int tour;
	public final InSortPassif sort;
	public final Action[] actions;

	public DeclencherSortPassif(long tempsLance, int idClasseSort, long idEntite, int tour, Action... actions) {
		this(tempsLance, idClasseSort, idEntite, tour, null, actions);
	}

	public DeclencherSortPassif(long tempsLance, int idClasseSort, long idEntite, int tour, InSortPassif sort, Action... actions) {
		super(tempsLance);
		this.idClasseSort = idClasseSort;
		this.idEntite = idEntite;
		this.tour = tour;
		this.sort = sort;
		this.actions = actions;
	}

}
