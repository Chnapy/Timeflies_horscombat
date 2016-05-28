/*
 * 
 * 
 * 
 */
package Serializable.InCombat.zone;

import java.io.Serializable;

/**
 * TypeZone.java
 Représente une zone pouvant être positive ou négative.
 * Une zone positive représente une zone classique.
 * Une zone négative fait un "trou" dans une zone positive.
 *
 */
public abstract class TypeZone implements Serializable {

	private static final long serialVersionUID = -8698183332248029149L;

	//Ajoute de la surface si positif, en retire sinon
	private final boolean positive;

	protected final int diametre;

	/**
	 *
	 * @param positive
	 * @param rayon
	 */
	public TypeZone(boolean positive, int rayon) {
		this.positive = positive;
		this.diametre = rayon * 2 + 1;
	}

	/**
	 * Récupère la zone sous forme de tableau 2D de booleen
	 *
	 * @return
	 */
	public abstract boolean[][] getZoneOfInterest();

	public boolean isPositive() {
		return this.positive;
	}

}
