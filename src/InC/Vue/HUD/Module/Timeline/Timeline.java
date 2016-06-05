/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.Donnees.SortActif;
import InC.Modele.Donnees.SortPassif;
import InC.Modele.ValeurCarac;
import Main.Vue.DataVue;
import Main.Vue.Vue;
import java.util.HashMap;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Timeline.java
 *
 */
public class Timeline extends VBox {

	private static final double LIST_ENTITE_SPACE = 20;

	private final HashMap<Long, RowEntiteTL> listRow;

	public Timeline() {
		setId("timeline");
		setAlignment(Pos.BOTTOM_RIGHT);
		setSpacing(LIST_ENTITE_SPACE);

		listRow = new HashMap();

//		Vue.turnOffPickOnBoundsFor(this);
	}

	public RowEntiteTL addRow(long idEntite, int idClasse, String couleurEquipe, String nomDonne, int niveau,
			ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative) {
		RowEntiteTL retl = new RowEntiteTL(DataVue.getEntiteIcone(idClasse), couleurEquipe, nomDonne, niveau, vitalite, tempsAction, tempsSup, vitesse, fatigue, initiative);
		listRow.put(idEntite, retl);
		add(retl);
		return retl;
	}
	
	public void setForceOpen(long idEntite, boolean forceOpen) {
		listRow.get(idEntite).setForceOpen(forceOpen);
	}

	public void addSortActif(long idEntite, SortActif sa) {
		listRow.get(idEntite).addSA(sa);
	}

	public void addSortPassif(long idEntite, SortPassif sp) {
		listRow.get(idEntite).addSP(sp);
	}

	private void add(RowEntiteTL tle) {
		getChildren().add(tle);
		tle.setAlignment(Pos.BOTTOM_RIGHT);
		Vue.turnOffPickOnBoundsFor(tle);
	}

}
