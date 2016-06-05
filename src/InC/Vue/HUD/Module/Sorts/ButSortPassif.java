/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Sorts;

import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Module.Bulles.BulleSP;
import Main.Vue.DataVue;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ButSortPassif.java
 *
 */
public class ButSortPassif extends ImageView {

	public ButSortPassif(SortPassif sp) {
		this(DataVue.getSortIcone(sp.idClasse), sp.niveau);
		Tooltip.install(this, new BulleSP(sp));
	}

	public ButSortPassif(Image fond, int niveau) {
		super(fond);
		setPreserveRatio(true);
	}
}
