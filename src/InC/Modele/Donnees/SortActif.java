/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.ValeurCarac;
import Serializable.InCombat.donnee.InSortActif;
import Serializable.InCombat.zone.Zone;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * SortActif.java
 *
 */
public class SortActif extends SortPassif {
	
	public final ValeurCarac<IntegerProperty> tempsAction;
	public final ValeurCarac<IntegerProperty> cooldown;
	public final SimpleIntegerProperty fatigue;
	public final Zone zonePortee;
	public final Zone zoneAction;

	public SortActif(InSortActif sa) {
		this(sa.idClasseSort, sa.niveau, sa.tempsAction,
				sa.cooldown, sa.fatigue, sa.zonePortee, sa.zoneAction);
	}

	public SortActif(int idClasse, int niveau,
			int tempsAction, int cooldown, int fatigue, Zone zonePortee,
			Zone zoneAction) {
		super(idClasse, niveau);
		this.tempsAction = new ValeurCarac(
				new SimpleIntegerProperty(tempsAction),
				new SimpleIntegerProperty(tempsAction)
		);
		this.cooldown = new ValeurCarac(
				new SimpleIntegerProperty(0),
				new SimpleIntegerProperty(cooldown)
		);
		this.fatigue = new SimpleIntegerProperty(fatigue);
		this.zonePortee = zonePortee;
		this.zoneAction = zoneAction;
	}

}
