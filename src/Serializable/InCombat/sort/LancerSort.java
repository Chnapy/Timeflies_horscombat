/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Serializable.InCombat.InCombat;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.Position;

/**
 * LancerSort.java
 *
 */
public class LancerSort extends InCombat {

	private static final long serialVersionUID = 1668810283786981565L;

	public final int idLancer;
	public final int idClasseSort;
	public final long idEntite;	//Lanceur
	public final int dureeLancer;
	public final int tour;
	public final Position position;
	public final InSortActif sort;
	public final Action[] actions;

	public LancerSort(long tempsLance, int idLancer, int idClasseSort, long idEntite, int dureeLancer, int tour, Position position, Action... actions) {
		this(tempsLance, idLancer, idClasseSort, idEntite, dureeLancer, tour, position, null, actions);
	}

	public LancerSort(long tempsLance, int idLancer, int idClasseSort, long idEntite, int dureeLancer, int tour, Position position, InSortActif sort, Action... actions) {
		super(tempsLance);
		this.idLancer = idLancer;
		this.idClasseSort = idClasseSort;
		this.idEntite = idEntite;
		this.dureeLancer = dureeLancer;
		this.tour = tour;
		this.position = position;
		this.sort = sort;
		this.actions = actions;
	}

}
