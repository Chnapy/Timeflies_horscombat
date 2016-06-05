/*
 * 
 * 
 * 
 */
package InC.Vue.Map;

import javafx.scene.Node;

/**
 * VueMap
 * Interface
 *
 * @param <T>	type de tuile
 * @param <N>
 */
public interface VueMap<T extends VueItem, N extends Node> {

	public void ajoutNode(T tuile, int x, int y);
	
	public default void setMapSize(int width, int height) {
	}

	public default N getThis() {
		return (N) this;
	}

}
