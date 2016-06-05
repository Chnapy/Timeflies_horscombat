/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.Entitemap;

import InC.Vue.Map.Grille.AbstractMap;

/**
 * EntiteMap.java
 *
 */
public class EntiteMap extends AbstractMap<EntiteSprite> {

	public EntiteMap() {
		setId("entitemap");
	}

	@Override
	public void ajoutNode(EntiteSprite es, int x, int y) {
		double w = es.getImage().getWidth();
		double h = es.getImage().getHeight();
		placerNode(es, x, y, (TILE_WIDTH - w) / 2, -h + TILE_HEIGHT / 2);
		getChildren().add(es);
	}

}
