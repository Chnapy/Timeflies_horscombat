/*
 * 
 * 
 * 
 */
package Serializable.InCombat.action;

/**
 * AddSortPassif.java
 *
 */
public class AddSortPassif extends Action {

	private static final long serialVersionUID = -4197301914876968413L;
	
	public final int idClasseSP;

	public AddSortPassif(long idLanceur, long idCible, int idClasseSort, int idClasseSP) {
		super(idLanceur, idCible, idClasseSort);
		this.idClasseSP = idClasseSP;
	}

}
