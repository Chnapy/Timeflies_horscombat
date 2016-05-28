/*
 * 
 * 
 * 
 */
package InC.Vue.Module.Timeline;

import InC.Modele.ValeurCarac;
import InC.Vue.Jauge;
import InC.Vue.JaugeCirculaire;
import InC.Vue.StyleClass;
import static InC.Vue.StyleClass.FATIGUE;
import static InC.Vue.StyleClass.TEMPSA;
import static InC.Vue.StyleClass.TEMPSS;
import static InC.Vue.StyleClass.VITESSE;
import Main.Vue.DataVue;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * RowEntite.java
 *
 */
public class RowEntite extends HBox {

	private static final double T_ENTITE_MIN_HEIGHT = 64, T_ENTITE_MAX_HEIGHT = 100,
			EQUIPE_WIDTH = 5, JAUGE_VIE_WIDTH = 10, BUFF_HEIGHT = 32, JC_WIDTH = 40,
			SPACE = 3;

	private final HBox sortsPassifs, envoutements, caracteristiques;

	public RowEntite(Image fond, String equipeCode, String classe, String nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue, int initiative) {
		setSpacing(SPACE);

		caracteristiques = new HBox();
		caracteristiques.setAlignment(Pos.CENTER);
		caracteristiques.setSpacing(SPACE);
		sortsPassifs = new HBox();
		sortsPassifs.setAlignment(Pos.CENTER);
		sortsPassifs.setSpacing(SPACE);
		envoutements = new HBox();
		envoutements.setAlignment(Pos.CENTER);
		envoutements.setSpacing(SPACE);
		Separator sepCaracSort = new Separator(Orientation.VERTICAL);
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
		FlowPane flow = new FlowPane(envoutements, sepSortEnv, sortsPassifs, sepCaracSort, caracteristiques);
		flow.setOrientation(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.CENTER_RIGHT);
//		flow.setPrefWrapLength(2000);
		getChildren().add(flow);

		addVignette(new Vignette(fond, equipeCode, niveau, vitalite));
		addSP(DataVue.getSortIcone(0));
		addSP(DataVue.getSortIcone(1));
		addE(new TlEnvoutement(DataVue.getSortIcone(2), new SimpleIntegerProperty(3)));
		addE(new TlEnvoutement(DataVue.getSortIcone(3), new SimpleIntegerProperty(5)));
		addC(new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, TEMPSA), tempsAction);
		addC(new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, TEMPSS), tempsSup);
		addC(new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, VITESSE), vitesse);
		addC(new JaugeCirculaire(JaugeCirculaire.TypeText.POURCENTAGE, FATIGUE), fatigue);
	}

	private void addVignette(Vignette v) {
		this.getChildren().add(v);
		v.setPrefHeight(T_ENTITE_MIN_HEIGHT);
		v.setMinHeight(T_ENTITE_MIN_HEIGHT);
		v.setMaxHeight(T_ENTITE_MAX_HEIGHT);
	}

	private void addSP(Image image) {
		ImageView imgView = new ImageView(image);
		imgView.setFitHeight(BUFF_HEIGHT);
		imgView.setPreserveRatio(true);
		sortsPassifs.getChildren().add(imgView);
	}

	private void addE(TlEnvoutement tle) {
		tle.setPrefHeight(BUFF_HEIGHT);
		tle.setMaxHeight(BUFF_HEIGHT);
		envoutements.getChildren().add(tle);
	}

	private void addC(JaugeCirculaire jc, ValeurCarac<? extends NumberExpression> vc) {
		jc.setPrefWidth(JC_WIDTH);
		jc.bind(vc);
		caracteristiques.getChildren().add(jc);
	}

	public class Vignette extends VBox {

		public Vignette(Image fond, String equipeCode, int niveau,
				ValeurCarac<IntegerProperty> vitalite) {
			getStyleClass().add("vignette");

			BorderPane bp = new BorderPane();
			bp.prefHeightProperty().bind(prefHeightProperty());
			getChildren().add(bp);

			Jauge jaugeVie = new Jauge(Orientation.VERTICAL, StyleClass.VITALITE);
			jaugeVie.setPrefWidth(JAUGE_VIE_WIDTH);
			jaugeVie.bind(vitalite);
			bp.setLeft(jaugeVie);
			BorderPane.setAlignment(jaugeVie, Pos.BOTTOM_RIGHT);

			ImageView imgView = new ImageView(fond);
			imgView.fitHeightProperty().bind(bp.prefHeightProperty());
			imgView.setPreserveRatio(true);
			bp.setCenter(imgView);

			Rectangle rectEquipe = new Rectangle(EQUIPE_WIDTH, 0);
			rectEquipe.setFill(Color.valueOf(equipeCode + "FF"));
			rectEquipe.heightProperty().bind(bp.prefHeightProperty());
			bp.setRight(rectEquipe);
			BorderPane.setAlignment(rectEquipe, Pos.BOTTOM_LEFT);
		}

	}

}
