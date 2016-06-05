/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.Tilemap;

import InC.Vue.Map.Grille.AbstractMap;

/**
 * TileMap.java
 *
 */
public class TileMap extends AbstractMap<TilePolygon> {

	private static final Double[] TILE_PTS = {
		0d, TILE_HEIGHT / 2,
		TILE_WIDTH / 2, TILE_HEIGHT,
		TILE_WIDTH, TILE_HEIGHT / 2,
		TILE_WIDTH / 2, 0d
	};

	public TilePolygon[][] tuiles;

	public TileMap() {
		setId("tilemap");
	}
	
	@Override
	public void setMapSize(int wLength, int hLength) {
		AbstractMap.hLength = hLength;
		tuiles = new TilePolygon[wLength][hLength];
	}

	@Override
	public void ajoutNode(TilePolygon tp, int x, int y) {
		tuiles[x][y] = tp;
		tuiles[x][y].poly.getPoints().addAll(TILE_PTS);
		tuiles[x][y].setPrefSize(TILE_WIDTH, TILE_HEIGHT);
		placerNode(tuiles[x][y], x, y, 0, 0);
		getChildren().add(tuiles[x][y]);
	}

}
