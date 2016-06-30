/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Serializable.InCombat.Orientation;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.action.Rotation;
import Serializable.InCombat.action.Teleportation;
import Serializable.Position;

/**
 * Deplacement.java
 *
 */
public class Deplacement extends LancerSort {

	private static final long serialVersionUID = 2516893678849034420L;

	public final Position previousPosition;

	public Deplacement(long tempsLance, int idLancer, long idEntite, int idClasseDeplacement, int idClasseRotation, int dureeLancer, int tour, Position previousPosition, Position position, Orientation o) {
		this(tempsLance, idLancer, idEntite, idClasseDeplacement, dureeLancer, tour, previousPosition, position,
				new Teleportation(idEntite, idEntite, idClasseDeplacement, position),
				new Rotation(idEntite, idEntite, idClasseRotation, o));
	}

	public Deplacement(long tempsLance, int idLancer, long idEntite, int idClasseDeplacement, int dureeLancer, int tour, Position previousPosition, Position position) {
		this(tempsLance, idLancer, idEntite, idClasseDeplacement, dureeLancer, tour, previousPosition, position,
				new Teleportation(idEntite, idEntite, idClasseDeplacement, position));
	}

	public Deplacement(long tempsLance, int idLancer, long idEntite, int idClasseDeplacement, int dureeLancer, int tour, Position previousPosition, Position position, Action... actions) {
		super(tempsLance, idLancer, idClasseDeplacement, idEntite, dureeLancer, tour, position, actions);
		this.previousPosition = previousPosition;
	}

}
