/*
 * 
 * 
 * 
 */
package Serializable.InCombat.zone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Zone.java
 Gère l'ensemble des zones permettant l'affichage de la portée d'un sort
 actif ou de sa zone d'action.
 *
 */
public class Zone implements Serializable {

	private static final long serialVersionUID = 7454854904542777682L;

	//Liste des zones cumulables
	private final ArrayList<TypeZone> listZones;

	//Zone intermédiaire sous forme de tableau 2D de booleen
	//false = vide
	private final boolean[][] zoneIntermediaire;

	private int diametre;

	/**
	 *
	 * @param zones
	 */
	public Zone(TypeZone... zones) {
		listZones = new ArrayList(Arrays.asList(zones));
		listZones.sort((TypeZone o1, TypeZone o2) -> {
			if (o1.isPositive()) {
				return -1;
			}
			return 1;
		});
		zoneIntermediaire = setZoneIntermediaire();
	}

	/**
	 * Calcul de la zone intermédiaire
	 *
	 * @return
	 */
	private boolean[][] setZoneIntermediaire() {
		diametre = 0;

		//Récupération de la taille de zone max
		listZones.forEach((zone) -> {
			if (zone.diametre > diametre) {
				diametre = zone.diametre;
			}
		});
		boolean[][] tab = new boolean[diametre][diametre];

		//Création de la zone finale
		listZones.forEach((zone) -> {
			boolean[][] zoneTab = zone.getZoneOfInterest();
			if (zone.isPositive()) {
				for (int y = (diametre - zone.diametre) / 2, j = 0; y < diametre - (diametre - zone.diametre) / 2; y++, j++) {
					for (int x = (diametre - zone.diametre) / 2, i = 0; x < diametre - (diametre - zone.diametre) / 2; x++, i++) {
						if (zoneTab[j][i]) {
							tab[y][x] = zoneTab[j][i];
						}
					}
				}
			} else {
				for (int y = (diametre - zone.diametre) / 2, j = 0; y < diametre - (diametre - zone.diametre) / 2; y++, j++) {
					for (int x = (diametre - zone.diametre) / 2, i = 0; x < diametre - (diametre - zone.diametre) / 2; x++, i++) {
						if (zoneTab[j][i]) {
							tab[y][x] = !zoneTab[j][i];
						}
					}
				}
			}
		});

		return tab;
	}

	public ArrayList<TypeZone> getListZones() {
		return listZones;
	}

	public boolean[][] getZoneIntermediaire() {
		return zoneIntermediaire;
	}

}
