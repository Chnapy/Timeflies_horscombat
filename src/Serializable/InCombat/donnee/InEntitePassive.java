/*
 * 
 * 
 * 
 */
package Serializable.InCombat.donnee;

import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * InEntitePassive.java
 * 
 */
public class InEntitePassive implements Serializable {

	private static final long serialVersionUID = 3561787358122927229L;
	
	public final long id;
	public final int idClasse;
	public final long idJoueur;
	public final int niveau;
	public final Position position;
	public final Orientation orientation;
	public final CaracteristiquePhysique caracs;
	public final ArrayList<InSortPassif> listSP;

	public InEntitePassive(long id, int idClasse, long idJoueur, int niveau, Position position, Orientation orientation, CaracteristiquePhysique caracs, ArrayList<InSortPassif> listSP) {
		this.id = id;
		this.idClasse = idClasse;
		this.idJoueur = idJoueur;
		this.niveau = niveau;
		this.position = position;
		this.orientation = orientation;
		this.caracs = caracs;
		this.listSP = listSP;
	}

}
