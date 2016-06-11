/*
 * 
 * 
 * 
 */
package Serializable.InCombat.donnee;

import Serializable.InCombat.zone.Zone;

/**
 * InSortActif.java
 * 
 */
public class InSortActif extends InSort {

	private static final long serialVersionUID = -7139047353434471239L;
	
	public final int tempsAction;
	public final int cooldown;
	public final int fatigue;
	public final Zone zonePortee;
	public final Zone zoneAction;

	public InSortActif(int idClasseSort, int niveau, int tempsAction, int cooldown, int fatigue, Zone zonePortee, Zone zoneAction) {
		super(idClasseSort, niveau);
		this.tempsAction = tempsAction;
		this.cooldown = cooldown;
		this.fatigue = fatigue;
		this.zonePortee = zonePortee;
		this.zoneAction = zoneAction;
	}

}
