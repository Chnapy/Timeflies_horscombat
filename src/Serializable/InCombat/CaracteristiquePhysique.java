/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import Serializable.InCombat.CaracteristiquePhysique.Caracteristique;
import Serializable.HorsCombat.CaracteristiquePhysiqueMax;
import java.io.Serializable;
import java.util.HashMap;

/**
 * CaracteristiquePhysique.java
 *
 */
public class CaracteristiquePhysique extends HashMap<TypeCarac, Caracteristique> {

	private static final long serialVersionUID = -2753131758896621912L;

	public CaracteristiquePhysique(CaracteristiquePhysiqueMax cMax, int niveau) {
		this(cMax.vitalite, cMax.tempsaction, cMax.tempssup, cMax.vitesse, cMax.fatigue, niveau * 10);
	}

	public CaracteristiquePhysique(int vitalite, int tempsAction, int tempsSup, int vitesse, int fatigue, int initiative) {
		this.put(TypeCarac.VITALITE, new Caracteristique(vitalite, 0));
		this.put(TypeCarac.TEMPSACTION, new Caracteristique(tempsAction, 0));
		this.put(TypeCarac.TEMPSSUP, new Caracteristique(tempsSup, 0));
		this.put(TypeCarac.VITESSE, new Caracteristique(vitesse, 10, 190));
		this.put(TypeCarac.FATIGUE, new Caracteristique(fatigue, 0, 100));
		this.put(TypeCarac.INITIATIVE, new Caracteristique(initiative, 0, Integer.MAX_VALUE));
	}

	public static class Caracteristique implements Serializable {

		private static final long serialVersionUID = -5371972909114022564L;

		public final int min;
		public final int max;
		public int actu;

		public Caracteristique(int actu, int min) {
			this(actu, min, actu);
		}

		public Caracteristique(int actu, int min, int max) {
			this.min = min;
			this.max = max;
			this.actu = actu;
		}

	}

}
