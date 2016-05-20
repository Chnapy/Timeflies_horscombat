/*
 * 
 * 
 * 
 */
package Serializable.Combat;

import Serializable.Personnages.HCPersonnage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * AskCombat.java
 * 
 */
public class AskCombat implements Serializable {
	
	private static final long serialVersionUID = -156415641401283422L;
	
	public static enum TypeCombat {
		SOLO, EQUIPE;
	}
	
	public final TypeCombat type;
	public final ArrayList<Long> idPersos;
	
	public AskCombat(TypeCombat type, ArrayList<HCPersonnage> persos) {
		this.type = type;
		this.idPersos = new ArrayList();
		for(HCPersonnage perso : persos) {
			idPersos.add(perso.id);
		}
	}

}
