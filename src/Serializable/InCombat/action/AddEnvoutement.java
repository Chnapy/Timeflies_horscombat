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

	public final int idClasseEnvoutement;
	public final int nbrTours;

	public AddEnvoutement(long idLanceur, long idCible, int idClasseSort, int idClasseEnvoutement, int nbrTours) {
		super(idLanceur, idCible, idClasseSort);
		this.idClasseEnvoutement = idClasseEnvoutement;
		this.nbrTours = nbrTours;
	}

}
