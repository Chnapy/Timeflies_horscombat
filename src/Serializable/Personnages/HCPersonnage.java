/*
 * 
 * 
 * 
 */
package Serializable.Personnages;

import Serializable.Personnages.Sort.HCSortActif;
import Serializable.Personnages.Sort.HCSortPassif;
import java.io.Serializable;

/**
 * HCPersonnage.java
 *
 */
public class HCPersonnage implements Serializable {

	private static final long serialVersionUID = -7290110273359388765L;

	public final long id;
	public final String nom;
	public final String classe;
	public final int niveauS;
	public final int vitalite;
	public final int tempsA;
	public final int tempsS;
	public final int vitesse;
	public final int fatigue;
	public final HCSortActif[] sortsActifs;
	public final HCSortPassif[] sortsPassifs;

	public HCPersonnage(long id, String nom, String classe, int niveauS, int vitalite, int tempsA, int tempsS, int vitesse, int fatigue, HCSortActif[] sortsActifs, HCSortPassif[] sortsPassifs) {
		this.id = id;
		this.nom = nom;
		this.classe = classe;
		this.niveauS = niveauS;
		this.vitalite = vitalite;
		this.tempsA = tempsA;
		this.tempsS = tempsS;
		this.vitesse = vitesse;
		this.fatigue = fatigue;
		this.sortsActifs = sortsActifs;
		this.sortsPassifs = sortsPassifs;
	}

}
