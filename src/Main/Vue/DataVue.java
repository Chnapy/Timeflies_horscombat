/*
 * 
 * 
 * 
 */
package Main.Vue;

import Main.Modele.Data;
import static Main.Modele.Data.ENTITE_ICONES_EXT;
import static Main.Modele.Data.ENTITE_SPRITES_EXT;
import static Main.Modele.Data.SORT_ICONES_EXT;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * DataVue.java
 *
 */
public final class DataVue {

	public static final Image DEFAULT_ICONE = new Image("file:" + Data.PATH_DEFAULT_ICONE);

	private static final HashMap<Integer, Image> ALL_SORTS_ICONES = new HashMap();
	private static final HashMap<Integer, Image> ALL_ENTITES_ICONES = new HashMap();
	private static final HashMap<Integer, Image> ALL_ENTITES_SPRITES = new HashMap();

	public static Image getSortIcone(int id) {
		Image icone = ALL_SORTS_ICONES.get(id);
		if (icone == null) {
			try {
				icone = new Image("file:" + Data.PATH_SORT_ICONES + id + "." + SORT_ICONES_EXT);
				ALL_SORTS_ICONES.put(id, icone);
			} catch (NullPointerException | IllegalArgumentException e) {
				icone = DEFAULT_ICONE;
			}
		}
		return icone;
	}

	public static Image getEntiteIcone(int id) {
		Image icone = ALL_ENTITES_ICONES.get(id);
		if (icone == null) {
			try {
				icone = new Image("file:" + Data.PATH_ENTITE_ICONES + id + "." + ENTITE_ICONES_EXT);
				if(icone.getException() != null) {
					throw new IllegalArgumentException(icone.getException());
				}
				ALL_ENTITES_ICONES.put(id, icone);
			} catch (NullPointerException | IllegalArgumentException e) {
				icone = DEFAULT_ICONE;
			}
		}
		return icone;
	}

	public static Image getEntiteSprite(int id) {
		Image sprite = ALL_ENTITES_SPRITES.get(id);
		if (sprite == null) {
			try {
				sprite = new Image("file:" + Data.PATH_ENTITE_SPRITES + id + "." + ENTITE_SPRITES_EXT);
				if(sprite.getException() != null) {
					throw new IllegalArgumentException(sprite.getException());
				}
				ALL_ENTITES_SPRITES.put(id, sprite);
			} catch (NullPointerException | IllegalArgumentException e) {
				sprite = DEFAULT_ICONE;
			}
		}
		return sprite;
	}

}
