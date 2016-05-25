/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

/**
 * Niveau.java
 * Représente le niveau d'un sort, et gère son augmentation.
 *
 */
public class Niveau implements HorsCombat {

	private static final long serialVersionUID = -3798987986433973740L;
	
	/**
	 * Echelle d'augmentation des niveaux.
	 * Plus l'échelle est grande, plus il faudra d'xp pour monter de niveaux.
	 */
	private static final double ECHELLE = 0.1;

	private int niveauActu;

	private int experienceActu;

	public Niveau() {
		this(0);
	}

	/**
	 * Niveau en fonction de l'experience.
	 *
	 * @param xp	experience
	 */
	public Niveau(int xp) {
		experienceActu = xp;
		niveauActu = calculNiveau();
	}
	
	public Niveau(int niveau, int xp) {
		this.experienceActu = xp;
		this.niveauActu = niveau;
	}

	/**
	 * Gère le gain d'expérience et la montée de niveaux.
	 *
	 * @param xp expérience gagnée
	 * @return true si le sort UP
	 */
	public boolean gainExperience(int xp) {
		experienceActu += xp;
		int niveau = calculNiveau();
		if (niveau > niveauActu) {
			niveauActu = niveau;
			return true;
		}
		return false;
	}

	/**
	 * Calcul le niveau depuis l'expérience possédée.
	 * Utilise un algo logarithmique.
	 *
	 * niveau = racine(experience) * echelle
	 *
	 * @return	niveau
	 */
	private int calculNiveau() {
		return (int) (Math.sqrt(experienceActu) * ECHELLE);
	}

	public int getNiveauActu() {
		return niveauActu;
	}

	public int getExperienceActu() {
		return experienceActu;
	}

}
