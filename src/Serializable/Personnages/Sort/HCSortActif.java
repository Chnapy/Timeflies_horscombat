/*
 * 
 * 
 * 
 */
package Serializable.Personnages.Sort;

/**
 * HCSortActif.java
 *
 */
public class HCSortActif extends HCSort {

	private static final long serialVersionUID = -8063582855744864546L;

	public final int tempsAction;
	public final int cooldown;
	public final int fatigue;

	public HCSortActif(String nom, HCNiveau niveau, String description, int tempsAction, int cooldown, int fatigue) {
		super(nom, niveau, description);
		this.tempsAction = tempsAction;
		this.cooldown = cooldown;
		this.fatigue = fatigue;
	}

}
