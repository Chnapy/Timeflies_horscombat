/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.Donnees.SortActif;
import InC.Modele.Donnees.SortPassif;
import InC.Vue.HUD.Module.Sorts.ButEnvoutement;
import InC.Modele.ValeurCarac;
import InC.Vue.HUD.CercleLabel;
import InC.Vue.HUD.JaugeCirculaire;
import InC.Vue.HUD.Module.Sorts.ButSortPassif;
import static InC.Vue.StyleClass.FATIGUE;
import static InC.Vue.StyleClass.INITIATIVE;
import static InC.Vue.StyleClass.TEMPSA;
import static InC.Vue.StyleClass.TEMPSS;
import static InC.Vue.StyleClass.VITALITE;
import static InC.Vue.StyleClass.VITESSE;
import InC.Modele.Binding.TimeBinding;
import InC.Vue.Map.VueEntite;
import Serializable.Position;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * RowEntite.java
 *
 */
public class RowEntite extends HBox implements VueEntite<RowEntite> {

	public static final double T_ENTITE_MIN_HEIGHT = 32, T_ENTITE_MAX_HEIGHT = 96,
			EQUIPE_WIDTH = 5, JAUGE_VIE_WIDTH = 10, BUFF_HEIGHT = 32, JC_WIDTH = 40,
			SPACE = 3;

	protected final Vignette vignette;
	protected final HBox sortsPassifs, envoutements, caracteristiques, sortsActifs;
	protected final JaugeCirculaire jcVita, jcTA, jcTS, jcVit, jcFat;
	protected final CercleLabel jcIni;

	public RowEntite(Image fond, String equipeCode, String nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative) {
		this();
		setData(fond, equipeCode, nom, niveau, vitalite, tempsAction, tempsSup, vitesse, fatigue, initiative);
	}

	public RowEntite() {
		init();
		setSpacing(SPACE);

		sortsActifs = new HBox();
		sortsActifs.setAlignment(Pos.CENTER_RIGHT);
		sortsActifs.setSpacing(SPACE);

		caracteristiques = new HBox();
		caracteristiques.setAlignment(Pos.CENTER_RIGHT);
		caracteristiques.setSpacing(SPACE);

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

		VBox left = new VBox(caracteristiques, flow, sortsActifs);
		left.setAlignment(Pos.CENTER_RIGHT);
		left.setSpacing(SPACE);
		getChildren().add(left);

		vignette = new Vignette();
		addVignette(vignette);

		jcVita = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, VITALITE);
		jcTA = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, TEMPSA);
		jcTS = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, TEMPSS);
		jcVit = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, VITESSE);
		jcFat = new JaugeCirculaire(JaugeCirculaire.TypeText.POURCENTAGE, FATIGUE);
		jcIni = new CercleLabel();
		jcIni.getStyleClass().add(INITIATIVE);

		addC(jcIni);
		addC(jcFat);
		addC(jcVit);
		addC(jcTS);
		addC(jcTA);
		addC(jcVita);
	}

	public final void setData(Image fond, String equipeCode, String nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative) {
		vignette.setImage(fond);
		vignette.setEquipeColor(equipeCode);
		vignette.setNom(nom);
		vignette.setNiveau(niveau);
		vignette.setJaugeBind(vitalite);
		jcIni.textProperty().bind(initiative.first.asString());
		jcVita.bind(vitalite);
		jcTA.bind(new TimeBinding(tempsAction.first), new TimeBinding(tempsAction.second));
		jcTS.bind(new TimeBinding(tempsSup.first), new TimeBinding(tempsSup.second));
		jcVit.bind(vitesse);
		jcFat.bind(fatigue);
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

	public final void addSA(SortActif sa) {
		ButSortPassif b = new ButSortPassif(sa);
		b.setFitHeight(BUFF_HEIGHT);
		sortsActifs.getChildren().add(b);
	}

	public final void addE(Image image, IntegerProperty toursDuree) {
		ButEnvoutement tle = new ButEnvoutement(image, toursDuree);
		tle.setPrefHeight(BUFF_HEIGHT);
		tle.setMaxHeight(BUFF_HEIGHT);
		envoutements.getChildren().add(tle);
	}

	protected final void addC(CercleLabel jc) {
		jc.setPrefHeight(JC_WIDTH);
		caracteristiques.getChildren().add(jc);
	}

	protected final void addC(JaugeCirculaire jc) {
		jc.setPrefWidth(JC_WIDTH);
		caracteristiques.getChildren().add(jc);
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

}
