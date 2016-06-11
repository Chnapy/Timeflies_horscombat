/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

/**
 * HCSort.java
 *
 */
public class HCSort implements HorsCombat {

	private static final long serialVersionUID = 3238941071137920942L;

	public final long idClasseSort;
	public final Niveau niveau;
	public final int tempsAction;
	public final int cooldown;
	public final int fatigue;
	
	public HCSort(long idClasseSort, Niveau niveau) {
		this(idClasseSort, niveau, 0, 0, 0);
	}

	public HCSort(long idClasseSort, Niveau niveau, int tempsAction, int cooldown, int fatigue) {
		this.idClasseSort = idClasseSort;
		this.niveau = niveau;
		this.tempsAction = tempsAction;
		this.cooldown = cooldown;
		this.fatigue = fatigue;
	}

}
