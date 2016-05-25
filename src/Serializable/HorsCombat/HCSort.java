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
	public final String nom;
	public final String description;
	public final Niveau niveau;
	public final int tempsAction;
	public final int cooldown;
	public final int fatigue;
	
	public HCSort(long idClasseSort, String nom, String description, Niveau niveau) {
		this(idClasseSort, nom, description, niveau, 0, 0, 0);
	}

	public HCSort(long idClasseSort, String nom, String description, Niveau niveau, int tempsAction, int cooldown, int fatigue) {
		this.idClasseSort = idClasseSort;
		this.nom = nom;
		this.description = description;
		this.niveau = niveau;
		this.tempsAction = tempsAction;
		this.cooldown = cooldown;
		this.fatigue = fatigue;
	}

}
