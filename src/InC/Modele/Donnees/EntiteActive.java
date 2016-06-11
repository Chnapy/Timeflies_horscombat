/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.Pathfinding.Mover;
import Serializable.InCombat.TypeCarac;
import Serializable.InCombat.donnee.InEntiteActive;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 * EntiteActive.java
 *
 */
public class EntiteActive extends EntitePassive implements Mover {

	public final SimpleStringProperty nomDonne;
	public final SimpleMapProperty<Integer, SortActif> sortsA;
	public final SortActif deplacement;
	public final int maxTempsAction;

	public EntiteActive(InEntiteActive ent, Equipe equipe, int tempsDeplacement) {
		super(ent, equipe);
		this.nomDonne = new SimpleStringProperty(ent.nomDonne);
		this.sortsA = new SimpleMapProperty(FXCollections.observableHashMap());
		ent.listSA.forEach((sa) -> sortsA.putIfAbsent(sa.idClasseSort, new SortActif(sa)));
		this.deplacement = new SortActif(-1, 0, tempsDeplacement, 0, 0, null, null);
		this.maxTempsAction = ent.caracs.get(TypeCarac.TEMPSACTION).max;
	}

}
