/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille;

import InC.Vue.Map.VueMap;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import InC.Vue.Map.VueItem;

/**
 * AbstractMap.java
 *
 * @param <T>
 */
public abstract class AbstractMap<T extends VueItem> extends AnchorPane implements VueMap<T, AbstractMap> {

	public static final double TILE_WIDTH = 128, TILE_HEIGHT = TILE_WIDTH / 2;
	protected static int hLength;

	public void placerNode(Node n, int x, int y, double decalageX, double decalageY) {
		AnchorPane.setLeftAnchor(n, getRealTileX(x, y) + decalageX);
		AnchorPane.setTopAnchor(n, getRealTileY(x, y) + decalageY);
	}

	public static double getRealTileX(int x, int y) {
		return x * TILE_WIDTH / 2 + y * TILE_WIDTH / 2;
	}

	public static double getRealTileY(int x, int y) {
		return y * TILE_HEIGHT / 2 - x * TILE_HEIGHT / 2 + TILE_HEIGHT / 2 * (hLength + 1);
	}

}
