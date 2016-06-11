/*
 * 
 * 
 * 
 */
package InC.Vue.Map;

import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Map.Tuile;
import InC.Vue.Map.Grille.Entitemap.EntiteSprite;
import InC.Vue.Map.Grille.Grille;
import InC.Vue.Map.Grille.StackMap;
import InC.Vue.Map.Minimap.Minientite;
import InC.Vue.Map.Minimap.Minimap;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * AllMap.java
 *
 */
public class AllMap {

	public final Grille grille;
	public final Minimap minimap;

	private final ArrayList<StackMap> maps;
	private final HashMap<Long, VueEntite[]> sEntites;

	public AllMap() {
		grille = new Grille();
		minimap = new Minimap();
		maps = new ArrayList();
		sEntites = new HashMap();

		maps.add(grille);
		maps.add(minimap);
	}

	public void setMapSize(int width, int height) {
		maps.forEach((m) -> m.setMapSize(width, height));
	}

	public VueTuile[] ajoutTuile(Tuile tuile, int x, int y) {
		VueTuile[] ts = new VueTuile[maps.size()];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = maps.get(i).ajoutTuile(tuile, x, y);
		}
		return ts;
	}

	public VueEntite[] ajoutEntite(EntitePassive e) {
		VueEntite[] ts = new VueEntite[maps.size()];
		for (int i = 0; i < ts.length; i++) {
			ts[i] = maps.get(i).ajoutEntite(e);
		}
		sEntites.put(e.idEntite, ts);
		return ts;
	}

	public EntiteSprite getEntiteSprite(long id) {
		return (EntiteSprite) sEntites.get(id)[0];
	}

	public Minientite getEntiteMini(long id) {
		return (Minientite) sEntites.get(id)[1];
	}

}
