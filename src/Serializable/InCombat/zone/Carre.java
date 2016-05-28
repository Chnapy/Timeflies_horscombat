/*
 * 
 * 
 * 
 */
package Serializable.InCombat.zone;

import java.util.Arrays;

/**
 * Carre.java
 * Représente une zone en forme de carré.
 *
 */
public class Carre extends TypeZone {

	private static final long serialVersionUID = 6165565806857360391L;

	/**
	 *
	 * @param cote	 longueur d'un coté
	 * @param posit	positif ?
	 */
	public Carre(int cote, boolean posit) {
		super(posit, cote);
	}

	@Override
	public boolean[][] getZoneOfInterest() {
		boolean[][] mapPositions = new boolean[diametre][diametre];
		for (boolean[] mp : mapPositions) {
			Arrays.fill(mp, true);
		}
		mapPositions[diametre / 2][diametre / 2] = false;

		return mapPositions;
	}

}
