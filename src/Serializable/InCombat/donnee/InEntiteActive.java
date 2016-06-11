/*
 * 
 * 
 * 
 */
package Serializable.InCombat.donnee;

import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import java.util.ArrayList;

/**
 * InEntiteActive.java
 * 
 */
public class InEntiteActive extends InEntitePassive {

	private static final long serialVersionUID = -1170606320826067223L;
	
	public final String nomDonne;
	public final ArrayList<InSortActif> listSA;
	public final int tempsDeplacement;
	
	public InEntiteActive(long id, int idClasse, long idJoueur, String nomDonne, int niveau, Position position, Orientation orientation, CaracteristiquePhysique caracs, ArrayList<InSortPassif> listSP, ArrayList<InSortActif> listSA, int tempsDeplacement) {
		super(id, idClasse, idJoueur, niveau, position, orientation, caracs, listSP);
		this.nomDonne = nomDonne;
		this.listSA = listSA;
		this.tempsDeplacement = tempsDeplacement;
	}

}
