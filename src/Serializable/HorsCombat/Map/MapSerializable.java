/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat.Map;

import Serializable.HorsCombat.HorsCombat;
import Serializable.HorsCombat.HorsCombat.TypeCombat;
import java.io.File;

/**
 * SERVEUR
 */
public class MapSerializable implements HorsCombat {

	private static final long serialVersionUID = 6925824279989941073L;

	public final String nom;
	public final String description;
	public final long idCreateur;	//idJoueur
	public final TypeCombat typeCombat;
	public final int nbrEquipes;
	public final int joueursParEquipe;	//3 = 3v3, 4 = 4v4 etc
	public final int difficulte;
	public final TypeTuile[][] tuiles;
	public final File background;
	public final int versionMajeure;
	public final int versionMineure;

	public MapSerializable(String nom, String description, long idCreateur,
			TypeCombat typeCombat, int nbrEquipes, int joueursParEquipe,
			int difficulte, TypeTuile[][] tuiles, File background,
			int versionMajeure, int versionMineure) {
		this.nom = nom;
		this.description = description;
		this.idCreateur = idCreateur;
		this.typeCombat = typeCombat;
		this.nbrEquipes = nbrEquipes;
		this.joueursParEquipe = joueursParEquipe;
		this.difficulte = difficulte;
		this.tuiles = tuiles;
		this.background = background;
		this.versionMajeure = versionMajeure;
		this.versionMineure = versionMineure;
	}

}
