/*
 * 
 * 
 * 
 */
package Serializable.HorsCombat;

/**
 * CaracteristiquePhysiqueMax.java
 *
 */
public class CaracteristiquePhysiqueMax implements HorsCombat {

	private static final long serialVersionUID = -3961408057303372307L;

	public final int vitalite;
	public final int tempsaction;
	public final int tempssup;
	public final int vitesse;
	public final int fatigue;

	public CaracteristiquePhysiqueMax(int vitalite, int tempsaction, int tempssup, int vitesse, int fatigue) {
		this.vitalite = vitalite;
		this.tempsaction = tempsaction;
		this.tempssup = tempssup;
		this.vitesse = vitesse;
		this.fatigue = fatigue;
	}

}
