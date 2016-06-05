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

	public final SimpleIntegerProperty tempsAction;
	public final ValeurCarac<IntegerProperty> cooldown;
	public final SimpleIntegerProperty fatigue;
	public final Zone zonePortee;
	public final Zone zoneAction;

	public SortActif(int idClasse, String nom, String description, int niveau,
			int tempsAction, int cooldown, int fatigue, Zone zonePortee,
			Zone zoneAction) {
		super(idClasse, nom, description, niveau);
		this.tempsAction = new SimpleIntegerProperty(tempsAction);
		this.cooldown = new ValeurCarac(
				new SimpleIntegerProperty(cooldown),
				new SimpleIntegerProperty(cooldown)
		);
		this.fatigue = new SimpleIntegerProperty(fatigue);
		this.zonePortee = zonePortee;
		this.zoneAction = zoneAction;
	}

	public SortActif(InSortActif sa) {
		this(sa.idClasseSort, sa.nom, sa.description, sa.niveau, sa.tempsAction,
				sa.cooldown, sa.fatigue, sa.zonePortee, sa.zoneAction);
	}

}
