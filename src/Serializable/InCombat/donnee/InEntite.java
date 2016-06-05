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
 * InEntite.java
 * 
 */
public class InEntite implements Serializable {

	private static final long serialVersionUID = 3561787358122927229L;
	
	public final long id;
	public final int idClasse;
	public final long idJoueur;
	public final String nomDonne;
	public final String nomClasse;
	public final int niveau;
	public final Position position;
	public final Orientation orientation;
	public final CaracteristiquePhysique caracs;
	public final ArrayList<InSortPassif> listSP;
	public final ArrayList<InSortActif> listSA;

	public InEntite(long id, int idClasse, long idJoueur, String nomDonne, String nomClasse, int niveau, Position position, Orientation orientation, CaracteristiquePhysique caracs, ArrayList<InSortPassif> listSP, ArrayList<InSortActif> listSA) {
		this.id = id;
		this.idClasse = idClasse;
		this.idJoueur = idJoueur;
		this.nomDonne = nomDonne;
		this.nomClasse = nomClasse;
		this.niveau = niveau;
		this.position = position;
		this.orientation = orientation;
		this.caracs = caracs;
		this.listSP = listSP;
		this.listSA = listSA;
	}

}
