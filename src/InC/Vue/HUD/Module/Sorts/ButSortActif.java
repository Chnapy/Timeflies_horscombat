/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Sorts;

import InC.Modele.Donnees.SortActif;
import InC.Vue.Actionnable;
import InC.Vue.HUD.Module.Bulles.BulleSA;
import Main.Vue.DataVue;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * ButSortActif.java
 *
 */
public class ButSortActif extends ToggleButton implements Actionnable<ButSortActif> {

	private final ImageView lFond;
	private final Label lTempsA;
	private final Label lCooldown;
	private final Label lFatigue;
	private final Label lNiveau;

	public ButSortActif(SortActif sa) {
		this(DataVue.getSortIcone(sa.idClasse), sa.tempsAction,
				sa.cooldown.first, sa.cooldown.second, sa.fatigue, sa.niveau);

		setTooltip(new BulleSA(sa));
	}

	public ButSortActif(Image fond, IntegerProperty tempsA, IntegerProperty cooldownActu, IntegerProperty cooldownTotal, IntegerProperty fatigue, int niveau) {
		init();
		getStyleClass().add("sortBut");
		setPadding(Insets.EMPTY);

		BorderPane apane = new BorderPane();
		getChildren().add(apane);

		this.lFond = new ImageView(fond);
		this.lTempsA = new Label();
		this.lCooldown = new Label();
		this.lFatigue = new Label();
		this.lNiveau = new Label(niveau + "");

		lTempsA.getStyleClass().add("tempsa");
		lCooldown.getStyleClass().add("cooldown");
		lFatigue.getStyleClass().add("fatigue");
		lNiveau.getStyleClass().add("niveau");

		lTempsA.textProperty().bind(tempsA.divide(1000d).asString());
		lCooldown.textProperty().bind(cooldownActu.asString());
		lFatigue.textProperty().bind(fatigue.asString());

		lFond.fitWidthProperty().bind(prefWidthProperty());
		lFond.setPreserveRatio(true);
		setGraphic(apane);

		apane.getChildren().add(lFond);

		apane.setTop(lTempsA);
		BorderPane.setAlignment(lTempsA, Pos.TOP_CENTER);

		apane.setLeft(lCooldown);
		BorderPane.setAlignment(lCooldown, Pos.CENTER_LEFT);

		apane.setRight(lFatigue);
		BorderPane.setAlignment(lFatigue, Pos.CENTER_RIGHT);

		apane.setBottom(lNiveau);
		BorderPane.setAlignment(lNiveau, Pos.BOTTOM_CENTER);

		lFond.toBack();
	}

	@Override
	public void hover(boolean hover) {
	}

	@Override
	public void pressed(boolean pressed) {
	}

}
