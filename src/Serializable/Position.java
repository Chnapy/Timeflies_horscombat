/*
 * 
 * 
 * 
 */
package Serializable;

import java.io.Serializable;

/**
 * Position.java
 *
 */
public class Position implements Serializable {

	private static final long serialVersionUID = -8848246289523111641L;

	public final int x;
	public final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return x * y;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Position) {
			Position o = (Position) other;

			return (o.x == x) && (o.y == y);
		}

		return false;
	}

	@Override
	public String toString() {
		return "Pos: x" + x + " y" + y;
	}

}
