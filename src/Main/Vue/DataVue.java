/*
 * 
 * 
 * 
 */
package Main.Vue;

import Main.Modele.Data;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * DataVue.java
 *
 */
public final class DataVue {

	private static final HashMap<Integer, Image> ALL_SORTS_ICONES = new HashMap();

	public static Image getSortIcone(int id) {
		Image icone = ALL_SORTS_ICONES.get(id);
		if (icone == null) {
			icone = new Image("file:" + Data.PATH_SORT_ICONES + id + ".png");
			ALL_SORTS_ICONES.put(id, icone);
		}
		return icone;
	}

}
