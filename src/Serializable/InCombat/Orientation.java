/*
 * 
 * 
 * 
 */
package Serializable.InCombat;

import Serializable.Position;

/**
 * Orientation
 * Enum
 */
public enum Orientation {

	NORD, EST, OUEST, SUD;

	public static Orientation getDirection(Position from, Position to) {
		if (from.equals(to)) {
			System.err.println(to);
			throw new IllegalArgumentException("Les deux points sont egaux ! " + to);
		}

		int vecX = to.x - from.x;
		int vecY = to.y - from.y;

		if (vecX > 0) {	//Est
			if (vecY > 0) {	//Sud
				if (vecX < vecY) {
					return Orientation.SUD;
				} else {
					return Orientation.EST;
				}
			} else //Nord
			{
				if (vecX < vecY) {
					return Orientation.NORD;
				} else {
					return Orientation.EST;
				}
			}
		} else //Ouest
		{
			if (vecY > 0) {	//Sud
				if (vecX < vecY) {
					return Orientation.SUD;
				} else {
					return Orientation.OUEST;
				}
			} else //Nord
			{
				if (vecX > vecY) {
					return Orientation.NORD;
				} else {
					return Orientation.OUEST;
				}
			}
		}
	}

}
