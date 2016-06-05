/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

/**
 * AddEnvoutement.java
 * 
 */
public class AddEnvoutement extends Action {

	private static final long serialVersionUID = -3553415798031297305L;

	public final int idClasseSort;
	public final int nbrTours;

	public AddEnvoutement(long idCible, int idClasseSort, int nbrTours) {
		super(idCible);
		this.idClasseSort = idClasseSort;
		this.nbrTours = nbrTours;
	}

}
