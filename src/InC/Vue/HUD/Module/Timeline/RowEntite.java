/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Module.Sorts.ButEnvoutement;
import InC.Modele.ValeurCarac;
import InC.Vue.HUD.Module.Sorts.ButSortPassif;
import InC.Modele.Donnees.Envoutement;
import InC.Vue.Map.VueEntite;
import Serializable.Position;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * RowEntite.java
 *
 */
public class RowEntite extends HBox implements VueEntite<RowEntite>, Comparable<Node> {

	public static final double T_ENTITE_MIN_HEIGHT = 32, T_ENTITE_MAX_HEIGHT = 96,
			EQUIPE_WIDTH = 5, JAUGE_VIE_WIDTH = 10, BUFF_HEIGHT = 32, JC_WIDTH = 40,
			SPACE = 3;

	protected final Vignette vignette;
	protected final HBox sortsPassifs, envoutements;
	protected final VBox left;

	public final SimpleIntegerProperty ordreJeu;
	protected final SimpleBooleanProperty open;

	public RowEntite(Image fond, String equipeCode, SimpleStringProperty nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite, SimpleIntegerProperty ordreJeu) {
		this();
		this.ordreJeu.bind(ordreJeu);
		setData(fond, equipeCode, nom, niveau, vitalite);
	}

	public RowEntite() {
		getStyleClass().addAll("rowentite", "open", "entitepassive");
		init();
		setSpacing(SPACE);

		ordreJeu = new SimpleIntegerProperty();

		open = new SimpleBooleanProperty(false);
		open.addListener((ov, t, t1) -> changeOpen(t1));

		sortsPassifs = new HBox();
		sortsPassifs.setAlignment(Pos.CENTER_RIGHT);
		sortsPassifs.setSpacing(SPACE);

		envoutements = new HBox();
		envoutements.setAlignment(Pos.CENTER_RIGHT);
		envoutements.setSpacing(SPACE);

		Separator sepSortEnv = new Separator(Orientation.VERTICAL);
		sepSortEnv.visibleProperty().bind(new BooleanBinding() {
			{
				super.bind(sortsPassifs.getChildren(), envoutements.getChildren());
			}

			@Override
			protected boolean computeValue() {
				return !sortsPassifs.getChildren().isEmpty() && !envoutements.getChildren().isEmpty();
			}
		});

		FlowPane flow = new FlowPane(envoutements, sepSortEnv, sortsPassifs);
		flow.setOrientation(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.CENTER_RIGHT);
		FlowPane.setMargin(sepSortEnv, new Insets(2, SPACE, 2, SPACE));

		left = new VBox(flow);
		left.setAlignment(Pos.CENTER_RIGHT);
		left.setSpacing(SPACE);
		getChildren().add(left);

		vignette = new Vignette();
		addVignette(vignette);

		open.set(false);
		changeOpen(false);
	}

	protected final void changeOpen(boolean open) {
		if (open) {
			getStyleClass().add("open");
		} else {
			getStyleClass().remove("open");
		}
		vignette.setPrefHeight(open ? T_ENTITE_MAX_HEIGHT : T_ENTITE_MIN_HEIGHT);
	}

	public final void setData(Image fond, String equipeCode, SimpleStringProperty nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite) {
		vignette.setData(nom, niveau, fond, equipeCode, vitalite);
	}

	protected final void addVignette(Vignette v) {
		this.getChildren().add(v);
		v.setPrefHeight(T_ENTITE_MAX_HEIGHT);
	}

	public final void addSP(SortPassif sp) {
		ButSortPassif b = new ButSortPassif(sp);
		b.setFitHeight(BUFF_HEIGHT);
		sortsPassifs.getChildren().add(b);
	}

	public final void addE(Envoutement e) {
		ButEnvoutement tle = new ButEnvoutement(e);
		tle.setPrefHeight(BUFF_HEIGHT);
		tle.setMaxHeight(BUFF_HEIGHT);
		envoutements.getChildren().add(tle);
	}

	public Vignette getVignette() {
		return vignette;
	}

	@Override
	public void hover(boolean hover) {
	}

	@Override
	public void pressed(boolean pressed) {
	}

	@Override
	public void position(Position position) {
	}

	@Override
	public void orientation(Serializable.InCombat.Orientation orientation) {
	}

	@Override
	public void estCible(boolean estCible) {
		hover(estCible);
	}

	@Override
	public void alive(boolean alive) {
		if (!alive) {
			hover(false);
			setDisable(true);
		}
	}

	@Override
	public int compareTo(Node n) {
		System.out.print("COMPARE " + ordreJeu.get() + " ");
		if (!(n instanceof RowEntite)) {
			System.out.println("NOT ROW");
			return -1;
		}
		if (((RowEntite) n).ordreJeu.greaterThan(ordreJeu).get()) {
			System.out.println("-1");
			return -1;
		} else if (((RowEntite) n).ordreJeu.lessThan(ordreJeu).get()) {
			System.out.println("1");
			return 1;
		}
			System.out.println("0");
		return 0;
	}

}
