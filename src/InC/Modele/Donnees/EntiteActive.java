/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.Pathfinding.Mover;
import Serializable.InCombat.donnee.InEntite;
import java.util.ArrayList;

/**
 * EntiteActive.java
 *
 */
public class EntiteActive extends EntitePassive implements Mover {

	public final boolean controlable;
	public final ArrayList<SortActif> sortsA;

	public EntiteActive(InEntite ent, Equipe equipe, boolean controlable) {
		super(ent, equipe);
		this.sortsA = new ArrayList();
		ent.listSA.forEach((sa) -> sortsA.add(new SortActif(sa)));
		this.controlable = controlable;
	}

}
