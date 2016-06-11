/*
 * 
 * 
 * 
 */

package InC.Vue.Map;

import Serializable.InCombat.Orientation;
import Serializable.Position;
import javafx.scene.Node;

/**
 * VueEntite
 * Interface
 * @param <N>
 */
public interface VueEntite<N extends Node> extends VueItem<N> {

	public default void changePosition(Position position) {
		position(position);
	}

	public void position(Position position);

	public default void changeOrientation(Orientation orientation) {
		orientation(orientation);
	}

	public void orientation(Orientation orientation);

	public default void changeEstCible(boolean estCible) {
		estCible(estCible);
	}

	public void estCible(boolean estCible);

	public default void changeAlive(boolean alive) {
		alive(alive);
	}

	public void alive(boolean alive);

}
