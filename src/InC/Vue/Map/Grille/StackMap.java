/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille;

import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Tuile;
import InC.Vue.Map.VueEntite;
import InC.Vue.Map.VueMap;
import javafx.scene.layout.StackPane;
import InC.Vue.Map.VueTuile;

/**
 * StackMap.java
 *
 * @param <TM>
 * @param <EM>
 * @param <T>
 * @param <E>
 */
public abstract class StackMap<TM extends VueMap, EM extends VueMap, 
		T extends VueTuile, E extends VueEntite> extends StackPane {

	protected final TM tmap;
	protected final EM emap;

	public StackMap(TM tmap, EM emap) {
		this.tmap = tmap;
		this.emap = emap;
		getChildren().addAll(tmap.getThis(), emap.getThis());
	}
	
	public abstract T ajoutTuile(Tuile t, int x, int y);

	public abstract E ajoutEntite(EntitePassive e);
	
	public void setMapSize(int width, int height) {
		tmap.setMapSize(width, height);
		emap.setMapSize(width, height);
	}

}
