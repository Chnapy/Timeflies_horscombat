/*
 * 
 * 
 * 
 */
package Main.Vue;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

/**
 * Ecran.java
 * 
 * @param <R>
 */
public abstract class Ecran<R extends Parent> {

	public final R root;
	public final double width;
	public final double height;
	public final Paint bg;

	public Ecran(R root, double w, double h, Paint bg) {
		this.root = root;
		this.width = w;
		this.height = h;
		this.bg = bg;
		if (root instanceof Region) {
			((Region) root).setBackground(Background.EMPTY);
		}
	}

	public Ecran(R root, Paint bg) {
		this(root, -1, -1, bg);
	}

	protected void setAllDisable(boolean disable) {
		root.getChildrenUnmodifiable().forEach((Node c) -> {
			if (c instanceof Node) {
				c.setDisable(disable);
			}
		});
	}

}
