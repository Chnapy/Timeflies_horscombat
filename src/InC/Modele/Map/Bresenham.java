/*
 * 
 * 
 * 
 */
package InC.Modele.Map;

import Serializable.HorsCombat.Map.TypeTuile;
import Serializable.Position;
import java.util.LinkedHashSet;

/**
 * Bresenham.java
 *
 */
public class Bresenham {

	private static final int OVER_SAMPLE = 3;	//Utiliser une valeur impaire!

	private final Map map;
	private final LinkedHashSet<Position> listPoints;

	public Bresenham(Map map) {
		this.map = map;
		listPoints = new LinkedHashSet();
	}

	public void checkerLigneDeVue(int startX, int startY, int endX, int endY) {
		line(startX * OVER_SAMPLE + OVER_SAMPLE / 2,
				startY * OVER_SAMPLE + OVER_SAMPLE / 2,
				endX * OVER_SAMPLE + OVER_SAMPLE / 2,
				endY * OVER_SAMPLE + OVER_SAMPLE / 2
		);
	}

	private void line(int startX, int startY, int endX, int endY) {

		int dx = Math.abs(endX - startX), sx = startX < endX ? 1 : -1;
		int dy = -Math.abs(endY - startY), sy = startY < endY ? 1 : -1;
		int err = dx + dy, e2; // error value e_xy
		Position pEnd = toTuilePos(endX, endY);

		while (true) {
			Position pStart = toTuilePos(startX, startY);
			if (!map.isCiblable(pStart.x, pStart.y)) {
				return;
			}

			if (startX == endX && startY == endY) {
				listPoints.add(pStart);
				return;
			} else if (map.tuiles[pStart.x][pStart.y].type == TypeTuile.ECRAN
					&& !pStart.equals(pEnd)) {
				return;
			}

			e2 = 2 * err;

			// horizontal step?
			if (e2 > dy) {
				err += dy;
				startX += sx;
			}

			// vertical step?
			if (e2 < dx) {
				err += dx;
				startY += sy;
			}
		}
	}

	private Position toTuilePos(int x, int y) {
		return new Position(x / OVER_SAMPLE, y / OVER_SAMPLE);
	}

	public void clearListPoints() {
		listPoints.clear();
	}

	public LinkedHashSet<Position> getListPoints() {
		return listPoints;
	}

}
