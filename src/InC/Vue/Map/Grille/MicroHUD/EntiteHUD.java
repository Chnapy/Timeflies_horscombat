/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.MicroHUD;

import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Donnees.Envoutement;
import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Jauge;
import InC.Vue.HUD.Module.Sorts.ButEnvoutement;
import InC.Vue.HUD.Module.Sorts.ButSortPassif;
import Serializable.InCombat.TypeCarac;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * EntiteHUD.java
 *
 */
public class EntiteHUD extends GridPane {

	public static final double BOUTON_WIDTH = 16, JAUGE_HEIGHT = 8,
			SPACE_COTE = 2, SPACE_TOP = 1;
	private static final Insets PADDING_COTE = new Insets(4),
			PADDING_TOP = new Insets(0, 2, 4, 2);

	public final VBox left, right, top;
	private final Jauge vie, ta;

	public EntiteHUD(EntitePassive ep) {
		this();
		vie.bind(ep.caracs.get(TypeCarac.VITALITE));
		ta.bind(ep.caracs.get(TypeCarac.TEMPSACTION));
		ep.sortsP.forEach((sp) -> {
			addSortP(sp);
		});
		ep.envoutements.forEach((e) -> {
			addEnvoutement(e);
		});
	}

	public EntiteHUD() {
		top = new VBox();
		left = new VBox();
		right = new VBox();
		add(top, 1, 0);
		add(left, 0, 1);
		add(right, 2, 1);

		vie = new Jauge(Orientation.HORIZONTAL);
		vie.getStyleClass().add("vitalite");
		vie.setPrefHeight(JAUGE_HEIGHT);
		ta = new Jauge(Orientation.HORIZONTAL);
		ta.getStyleClass().add("tempsa");
		ta.setPrefHeight(JAUGE_HEIGHT);
		top.getChildren().setAll(vie, ta);
		vie.prefWidthProperty().bind(prefWidthProperty()
				.subtract(left.widthProperty())
				.subtract(right.widthProperty())
		);
		ta.prefWidthProperty().bind(vie.prefWidthProperty());

		top.setPadding(PADDING_TOP);
		left.setPadding(PADDING_COTE);
		right.setPadding(PADDING_COTE);
		
		top.setSpacing(SPACE_TOP);
		left.setSpacing(SPACE_COTE);
		right.setSpacing(SPACE_COTE);
		
		top.setMaxHeight(JAUGE_HEIGHT);
		left.setMaxWidth(BOUTON_WIDTH);
		right.setMaxWidth(BOUTON_WIDTH);

		maxWidthProperty().bind(prefWidthProperty());
		maxHeightProperty().bind(prefHeightProperty());
		setPickOnBounds(false);
	}

	public void addSortP(SortPassif sp) {
		ButSortPassif bsp = new ButSortPassif(sp);
		left.getChildren().add(bsp);
		bsp.setFitWidth(BOUTON_WIDTH);

	}

	public void addEnvoutement(Envoutement env) {
		ButEnvoutement be = new ButEnvoutement(env);
		right.getChildren().add(be);
		be.setPrefHeight(BOUTON_WIDTH);
	}

}
