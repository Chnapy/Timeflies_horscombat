/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Minimap;

import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Tuile;
import InC.Vue.Map.Grille.StackMap;

/**
 * Minimap.java
 *
 */
public class Minimap extends StackMap<Minitilemap, Minientitemap, Minituile, Minientite> {

	public static double MINITILE_WIDTH = 8;

	public Minimap() {
		super(new Minitilemap(), new Minientitemap());
		setId("minimap");
		tmap.prefWidthProperty().bind(widthProperty());
		emap.prefWidthProperty().bind(widthProperty());
	}

	@Override
	public Minituile ajoutTuile(Tuile tuile, int x, int y) {
		Minituile mt = new Minituile(tuile);
		tmap.ajoutNode(mt, x, y);
		return mt;
	}

	@Override
	public Minientite ajoutEntite(EntitePassive e) {
		Minientite me = new Minientite(e);
		me.setRadius(MINITILE_WIDTH / 2);
		emap.ajoutNode(me, e.getBinding().position.get().x, e.getBinding().position.get().y);
		return me;
	}

	@Override
	public void setMapSize(int width, int height) {
		tmap.setMapSize(width, height);
		emap.setMapSize(width, height);
	}

}
