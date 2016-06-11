/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Sorts;

import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.SortActif;
import InC.Vue.Actionnable;
import InC.Vue.HUD.JaugeCirculaire;
import InC.Vue.HUD.JaugeCirculaire.JaugeCirculaireStack;
import InC.Vue.HUD.Module.Bulles.BulleSA;
import static InC.Vue.StyleClass.CARAC_CLASS;
import Main.Vue.DataVue;
import Serializable.InCombat.TypeCarac;
import static Serializable.InCombat.TypeCarac.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * ButSortActif.java
 *
 */
public class ButSortActif extends BorderPane implements Actionnable<ButSortActif> {

	private static final double JC_WIDTH = 16;
	private static final double JC_MARGIN = JC_WIDTH / 2;

	private final ImageView lFond;
	public final ToggleButton tButton;
	private final JaugeCirculaire lTempsA;
	private final JaugeCirculaire lCooldown;
	private final JaugeCirculaire lFatigue;
	private final JaugeCirculaire lNiveau;

	public ButSortActif(EntiteActive ea, SortActif sa) {
		this(ea, DataVue.getSortIcone(sa.idClasse), sa.tempsAction.first,
				sa.cooldown.first, sa.cooldown.second, sa.fatigue, sa.niveau);

		tButton.setTooltip(new BulleSA(sa));
	}

	public ButSortActif(EntiteActive ea, Image fond, IntegerProperty tempsA, IntegerProperty cooldownActu, IntegerProperty cooldownTotal, IntegerProperty fatigue, int niveau) {
		init();
		getStyleClass().add("sortBut");

		this.tButton = new ToggleButton();
		tButton.setPadding(Insets.EMPTY);

		this.lFond = new ImageView(fond);
		this.lTempsA = new JaugeCirculaireStack(JaugeCirculaire.TypeText.NONE, CARAC_CLASS.get(TEMPSACTION));
		this.lCooldown = new JaugeCirculaireStack(JaugeCirculaire.TypeText.NONE, CARAC_CLASS.get(COOLDOWN));
		this.lFatigue = new JaugeCirculaireStack(JaugeCirculaire.TypeText.NONE, CARAC_CLASS.get(FATIGUE));
		this.lNiveau = new JaugeCirculaireStack(JaugeCirculaire.TypeText.SIMPLE, CARAC_CLASS.get(NIVEAU));

		lTempsA.bind(tempsA, ea.caracs.get(TypeCarac.TEMPSACTION).first);
		lCooldown.bind(cooldownActu, cooldownTotal);
		lFatigue.bind(fatigue, ea.caracs.get(TypeCarac.FATIGUE).second);
		lNiveau.bind(new SimpleIntegerProperty(niveau));

		lTempsA.setPrefWidth(JC_WIDTH);
		lCooldown.setPrefWidth(JC_WIDTH);
		lFatigue.setPrefWidth(JC_WIDTH);
		lNiveau.setPrefWidth(JC_WIDTH);
		
		lTempsA.setMouseTransparent(true);
		lCooldown.setMouseTransparent(true);
		lFatigue.setMouseTransparent(true);
		lNiveau.setMouseTransparent(true);

		tButton.prefWidthProperty().bind(prefWidthProperty().subtract(JC_WIDTH));
		lFond.fitWidthProperty().bind(tButton.prefWidthProperty());
		lFond.setPreserveRatio(true);
		tButton.setGraphic(lFond);

		setCenter(tButton);

		setTop(lTempsA);
		lTempsA.setMaxHeight(JC_WIDTH);
		lTempsA.setPrefHeight(JC_WIDTH);
		BorderPane.setAlignment(lTempsA, Pos.TOP_CENTER);
		BorderPane.setMargin(lTempsA, new Insets(0, 0, -JC_MARGIN, 0));

		setLeft(lCooldown);
		lCooldown.setMaxWidth(JC_WIDTH);
		lCooldown.setPrefWidth(JC_WIDTH);
		BorderPane.setMargin(lCooldown, new Insets(24, -JC_MARGIN, 0, 0));

		setRight(lFatigue);
		lFatigue.setMaxWidth(JC_WIDTH);
		lFatigue.setPrefWidth(JC_WIDTH);
		BorderPane.setMargin(lFatigue, new Insets(24, 0, 0, -JC_MARGIN));

		setBottom(lNiveau);
		lNiveau.setMaxWidth(JC_WIDTH);
		lNiveau.setPrefWidth(JC_WIDTH);
		BorderPane.setAlignment(lNiveau, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(lNiveau, new Insets(-JC_MARGIN, 0, 0, 0));
	}

	@Override
	public void hover(boolean hover) {
	}

	@Override
	public void pressed(boolean pressed) {
	}

}
