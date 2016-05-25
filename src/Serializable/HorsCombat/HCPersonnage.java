/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

/**
 * HCPersonnage.java
 *
 */
public class HCPersonnage implements HorsCombat {

	private static final long serialVersionUID = -7290110273359388765L;

	public long id;
	public final int idClasse;
	public String nom;
	public final String nomClasse;
	public final CaracteristiquePhysiqueMax caracMax;
	public final int niveauS;
	public final HCSort[] sortsActifs;
	public final HCSort[] sortsPassifs;

	public HCPersonnage(long id, int idClasse, String nom, String nomClasse, CaracteristiquePhysiqueMax caracMax, int niveauS, HCSort[] sortsActifs, HCSort[] sortsPassifs) {
		this.idClasse = idClasse;
		this.id = id;
		this.nom = nom;
		this.nomClasse = nomClasse;
		this.caracMax = caracMax;
		this.niveauS = niveauS;
		this.sortsActifs = sortsActifs;
		this.sortsPassifs = sortsPassifs;
	}

}
