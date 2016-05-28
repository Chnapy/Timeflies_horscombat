/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import Serializable.InCombat.donnee.InEquipe;
import java.util.ArrayList;

/**
 * DebutCombat.java
 * 
 */
public class DebutCombat extends InCombat {

	private static final long serialVersionUID = 5877877654116487062L;
	
	public final int dureeAttente;
	public final ArrayList<InEquipe> equipes;

	public DebutCombat(long startCombat, int dureeAttente, ArrayList<InEquipe> equipes) {
		super(startCombat);
		this.dureeAttente = dureeAttente;
		this.equipes = equipes;
	}

}
