/*
 * 
 * 
 * 
 */
package Serializable.InCombat.sort;

import Main.Modele.Data;
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

	public Deplacement(long tempsLance, int idLancer, long idEntite, int dureeLancer, int tour, Position previousPosition, Position position, Orientation o) {
		this(tempsLance, idLancer, idEntite, dureeLancer, tour, previousPosition, position,
				new Teleportation(idEntite, idEntite, Data.DEPLACEMENT_IDCLASSE, position),
				new Rotation(idEntite, idEntite, Data.ROTATION_IDCLASSE, o));
	}

	public Deplacement(long tempsLance, int idLancer, long idEntite, int dureeLancer, int tour, Position previousPosition, Position position) {
		this(tempsLance, idLancer, idEntite, dureeLancer, tour, previousPosition, position,
				new Teleportation(idEntite, idEntite, Data.DEPLACEMENT_IDCLASSE, position));
	}

	public Deplacement(long tempsLance, int idLancer, long idEntite, int dureeLancer, int tour, Position previousPosition, Position position, Action... actions) {
		super(tempsLance, idLancer, Data.DEPLACEMENT_IDCLASSE, idEntite, dureeLancer, tour, position, actions);
		this.previousPosition = previousPosition;
	}

}
