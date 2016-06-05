/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import Serializable.InCombat.donnee.InEquipe;
import java.util.ArrayList;

/**
 * ChargementCombat.java
 *
 */
public class ChargementCombat extends InCombat {

	private static final long serialVersionUID = 5877877654116487062L;

	public final ArrayList<InEquipe> equipes;

	public ChargementCombat(ArrayList<InEquipe> equipes) {
		this.equipes = equipes;
	}

}
