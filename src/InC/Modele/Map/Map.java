/*
 * 
 * 
 * 
 */
package InC.Modele.Map;

import InC.Modele.Binding.EntiteBinding;
import InC.Modele.Map.Tuile.TuileState;
import InC.Modele.Pathfinding.Mover;
import InC.Modele.Pathfinding.TileBasedMap;
import Serializable.HorsCombat.Map.MapSerializable;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import java.util.ArrayList;

/**
 * Map.java
 *
 */
public class Map implements TileBasedMap<Position> {

	public final MapSerializable mapS;

	public final Tuile[][] tuiles;
	public final ArrayList<EntiteBinding> listEntitesBinding;
	private final Bresenham bresenham;

	public Map(MapSerializable mapS) {
		this.mapS = mapS;
		tuiles = new Tuile[mapS.tuiles.length][mapS.tuiles[0].length];
		for (int x = 0; x < mapS.tuiles.length; x++) {
			for (int y = 0; y < mapS.tuiles[0].length; y++) {
				tuiles[x][y] = new Tuile(x * mapS.tuiles[0].length + y, mapS.tuiles[x][y], x, y);
			}
		}
		listEntitesBinding = new ArrayList();
		bresenham = new Bresenham(this);
	}

	public void showPath(ArrayList<Position> listPos) {
		Orientation before, after;
		Tuile t;
		for (int i = 0; i < listPos.size(); i++) {
			t = getTileByPos(listPos.get(i).x, listPos.get(i).y);
			if (i == 0) {
				before = null;
			} else {
				before = Orientation.getDirection(t.position, listPos.get(i - 1));
			}

			if (i == listPos.size() - 1) {
				after = null;
			} else {
				after = Orientation.getDirection(t.position, listPos.get(i + 1));
			}
			t.setDeplacement(before, after);
		}
	}

	@Override
	public int getWidthInTiles() {
		return tuiles.length;
	}

	@Override
	public int getHeightInTiles() {
		return tuiles[0].length;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		tuiles[x][y].visited = true;
	}

	public void clearMap() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				tuiles[x][y].getBinding().state.set(TuileState.NONE);
			}
		}
	}

	public void clearDeplacement() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				tuiles[x][y].visited = false;
				if (tuiles[x][y].getBinding().state.get() == TuileState.DEPLACEMENT) {
					tuiles[x][y].getBinding().state.set(TuileState.NONE);
				}
			}
		}
	}

	public void clearZonePortee() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				if (tuiles[x][y].getBinding().state.get() == TuileState.ZONE_PORTEE) {
					tuiles[x][y].getBinding().state.set(TuileState.NONE);
				}
			}
		}
	}

	public void clearZoneAction() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				if (tuiles[x][y].getBinding().state.get() == TuileState.ZONE_ACTION) {
					tuiles[x][y].getBinding().returnToPreviousState();
				}
			}
		}
		listEntitesBinding.forEach((eb) -> eb.estCible.set(false));
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {
		return !tuiles[x][y].estTraversable() || positionHaveEntite(x, y);
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	private boolean positionHaveEntite(int x, int y) {
		return getEntiteBindingFromPosReference(x, y) != null;
	}

	private EntiteBinding getEntiteBindingFromPosReference(int x, int y) {
		for (EntiteBinding eb : listEntitesBinding) {
			if (eb.positionReference.get().x == x && eb.positionReference.get().y == y) {
				return eb;
			}
		}
		return null;
	}

	@Override
	public Position getPathPoint(int x, int y) {
		return new Position(x, y);
	}

	public Tuile getTileByPos(int x, int y) {
		return tuiles[x][y];
	}

	public Tuile getTileById(int id) {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				if (tuiles[x][y].id == id) {
					return tuiles[x][y];
				}
			}
		}
		return null;
	}

	public void showZonePortee(Position centre, boolean[][] zoneIntermediaire) {
		int dx = centre.x - zoneIntermediaire.length / 2;
		int dy = centre.y - zoneIntermediaire[0].length / 2;
		int fx, fy;

		for (int i = 0; i < zoneIntermediaire.length; i++) {
			for (int j = 0; j < zoneIntermediaire[0].length; j++) {
				fx = dx + i;
				fy = dy + j;
				if (isCiblable(fx, fy)) {
					bresenham.checkerLigneDeVue(centre.x, centre.y, fx, fy);
				}
			}
		}

		bresenham.getListPoints().forEach((p) -> {
			tuiles[p.x][p.y].getBinding().state.set(TuileState.ZONE_PORTEE);
		});
		bresenham.clearListPoints();
	}

	public ArrayList<Position> getZoneAction(Position centre, boolean[][] zoneIntermediaire, ArrayList<Position> output) {
		int dx = centre.x - zoneIntermediaire.length / 2;
		int dy = centre.y - zoneIntermediaire[0].length / 2;
		int fx, fy;

		for (int i = 0; i < zoneIntermediaire.length; i++) {
			for (int j = 0; j < zoneIntermediaire[0].length; j++) {
				fx = dx + i;
				fy = dy + j;
				if (isCiblable(fx, fy)) {
					output.add(new Position(fx, fy));
				}
			}
		}
//		System.out.println("OU" + output.size());
		return output;
	}

	public void showZoneAction(ArrayList<Position> points) {
		points.forEach((p) -> {
			tuiles[p.x][p.y].getBinding().state.set(TuileState.ZONE_ACTION);
			EntiteBinding eb = getEntiteBindingFromPosReference(p.x, p.y);
			if (eb != null) {
				eb.estCible.set(true);
			}
		});
	}

	public boolean isCiblable(int x, int y) {
		try {
			return tuiles[x][y].estTraversable();
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
