/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Donnees.Envoutement;
import InC.Modele.Donnees.SortActif;
import InC.Modele.Donnees.SortPassif;
import Main.Vue.DataVue;
import Main.Vue.Vue;
import static Serializable.InCombat.TypeCarac.FATIGUE;
import static Serializable.InCombat.TypeCarac.INITIATIVE;
import static Serializable.InCombat.TypeCarac.TEMPSACTION;
import static Serializable.InCombat.TypeCarac.TEMPSSUP;
import static Serializable.InCombat.TypeCarac.VITALITE;
import static Serializable.InCombat.TypeCarac.VITESSE;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Timeline.java
 *
 */
public class Timeline extends VBox {

	private static final double LIST_ENTITE_SPACE = 20;

	private final HashMap<Long, RowEntite> listRow;

	public Timeline() {
		setId("timeline");
		getStyleClass().add("module");
		setAlignment(Pos.BOTTOM_RIGHT);
		setSpacing(LIST_ENTITE_SPACE);

		listRow = new HashMap();

//		Vue.turnOffPickOnBoundsFor(this);
	}

	public RowEntiteATL addEntiteActive(EntiteActive e) {
		RowEntiteATL retl = new RowEntiteATL(DataVue.getEntiteIcone(e.idClasse),
				e.equipe.colorCode, e.nomDonne, e.niveau, e.caracs.get(VITALITE),
				e.caracs.get(TEMPSACTION), e.caracs.get(TEMPSSUP),
				e.caracs.get(VITESSE), e.caracs.get(FATIGUE), e.caracs.get(INITIATIVE),
				e.ordreJeu);
		listRow.put(e.idEntite, retl);
		add(retl);
		return retl;
	}

	public RowEntite addEntitePassive(EntitePassive e) {
		RowEntite retl = new RowEntite(DataVue.getEntiteIcone(e.idClasse),
				e.equipe.colorCode, e.nomClasse, e.niveau,
				e.caracs.get(VITALITE), e.ordreJeu);
		listRow.put(e.idEntite, retl);
		add(retl);
		return retl;
	}

	public void setForceOpen(long idEntite, boolean forceOpen) {
		try {
			((RowEntiteATL) listRow.get(idEntite)).setForceOpen(forceOpen);
		} catch (ClassCastException e) {
			System.err.println("La row " + idEntite + " n'est pas ATL");
		}
	}

	public void addSortActif(long idEntite, SortActif sa) {
		try {
			((RowEntiteATL) listRow.get(idEntite)).addSA(sa);
		} catch (ClassCastException e) {
			System.err.println("La row " + idEntite + " n'est pas ATL");
		}
	}

	public void addSortPassif(long idEntite, SortPassif sp) {
		listRow.get(idEntite).addSP(sp);
	}

	public void addEnvoutement(long idEntite, Envoutement sp) {
		listRow.get(idEntite).addE(sp);
	}

	private void add(RowEntite tle) {
		getChildren().add(tle);
		tle.setAlignment(Pos.BOTTOM_RIGHT);
		Vue.turnOffPickOnBoundsFor(tle);
	}

	public void sort() {
		ObservableList<Node> workingCollection = FXCollections.observableArrayList(
				getChildren()
		);

		Collections.sort(workingCollection, null);
//		Collections.sort(workingCollection, null);
		Platform.runLater(()
				-> getChildren().setAll(workingCollection));
	}

}
