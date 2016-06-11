/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.Binding.TimeBinding;
import InC.Modele.Donnees.SortActif;
import InC.Modele.ValeurCarac;
import InC.Vue.HUD.JaugeCirculaire;
import InC.Vue.HUD.Module.Sorts.ButSortPassif;
import static InC.Vue.HUD.Module.Timeline.RowEntite.BUFF_HEIGHT;
import static InC.Vue.HUD.Module.Timeline.RowEntite.SPACE;
import static InC.Vue.StyleClass.CARAC_CLASS;
import static Serializable.InCombat.TypeCarac.FATIGUE;
import static Serializable.InCombat.TypeCarac.INITIATIVE;
import static Serializable.InCombat.TypeCarac.TEMPSACTION;
import static Serializable.InCombat.TypeCarac.TEMPSSUP;
import static Serializable.InCombat.TypeCarac.VITALITE;
import static Serializable.InCombat.TypeCarac.VITESSE;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * RowEntiteActive.java
 *
 */
public class RowEntiteActive extends RowEntite {

	protected final HBox sortsActifs, caracteristiques;
	protected final JaugeCirculaire jcVita, jcTA, jcTS, jcVit, jcFat, jcIni;

	public RowEntiteActive(Image fond, String equipeCode, SimpleStringProperty nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative, SimpleIntegerProperty ordreJeu) {
		this();
		this.ordreJeu.bind(ordreJeu);
		setData(fond, equipeCode, nom, niveau, vitalite, tempsAction, tempsSup,
				vitesse, fatigue, initiative);
	}

	public RowEntiteActive() {
		getStyleClass().add("entiteactive");

		sortsActifs = new HBox();
		sortsActifs.setAlignment(Pos.CENTER_RIGHT);
		sortsActifs.setSpacing(SPACE);
		left.getChildren().add(sortsActifs);

		caracteristiques = new HBox();
		caracteristiques.setAlignment(Pos.CENTER_RIGHT);
		caracteristiques.setSpacing(SPACE);
		left.getChildren().add(0, caracteristiques);

		jcVita = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, CARAC_CLASS.get(VITALITE));
		jcTA = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, CARAC_CLASS.get(TEMPSACTION));
		jcTS = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, CARAC_CLASS.get(TEMPSSUP));
		jcVit = new JaugeCirculaire(JaugeCirculaire.TypeText.MIN_MAX, CARAC_CLASS.get(VITESSE));
		jcFat = new JaugeCirculaire(JaugeCirculaire.TypeText.POURCENTAGE, CARAC_CLASS.get(FATIGUE));
		jcIni = new JaugeCirculaire.JaugeCirculaireStack(JaugeCirculaire.TypeText.SIMPLE, CARAC_CLASS.get(INITIATIVE));

		addC(jcIni);
		addC(jcFat);
		addC(jcVit);
		addC(jcTS);
		addC(jcTA);
		addC(jcVita);

		caracteristiques.visibleProperty().bind(open);
		caracteristiques.managedProperty().bind(open);
		sortsActifs.visibleProperty().bind(open);
		sortsActifs.managedProperty().bind(open);
	}

	public final void setData(Image fond, String equipeCode, SimpleStringProperty nom,
			int niveau, ValeurCarac<IntegerProperty> vitalite,
			ValeurCarac<IntegerProperty> tempsAction,
			ValeurCarac<IntegerProperty> tempsSup,
			ValeurCarac<IntegerProperty> vitesse,
			ValeurCarac<IntegerProperty> fatigue,
			ValeurCarac<IntegerProperty> initiative) {
		super.setData(fond, equipeCode, nom, niveau, vitalite);
		jcVita.bind(vitalite);
		jcIni.bind(initiative.first);
		jcTA.bind(new TimeBinding(tempsAction.first), new TimeBinding(tempsAction.second));
		jcTS.bind(new TimeBinding(tempsSup.first), new TimeBinding(tempsSup.second));
		jcVit.bind(vitesse);
		jcFat.bind(fatigue);
	}

	public final void addSA(SortActif sa) {
		ButSortPassif b = new ButSortPassif(sa);
		b.setFitHeight(BUFF_HEIGHT);
		sortsActifs.getChildren().add(b);
	}

	protected final void addC(JaugeCirculaire jc) {
		jc.setPrefWidth(JC_WIDTH);
		caracteristiques.getChildren().add(jc);
	}
}
