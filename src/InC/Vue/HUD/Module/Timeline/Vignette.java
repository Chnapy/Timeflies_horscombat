/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module.Timeline;

import InC.Modele.ValeurCarac;
import InC.Vue.HUD.Jauge;
import static InC.Vue.HUD.Module.Timeline.RowEntiteATL.EQUIPE_WIDTH;
import static InC.Vue.HUD.Module.Timeline.RowEntiteATL.JAUGE_VIE_WIDTH;
import static InC.Vue.StyleClass.CARAC_CLASS;
import Main.Vue.DataVue;
import static Serializable.InCombat.TypeCarac.VITALITE;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Vignette.java
 *
 */
public class Vignette extends VBox {

	private static final String EQUIPE_OPACITY = "FF";
	private static final double NIVEAU_FONT_WIDTH = 7;

	private final ImageView imgView;
	private final Label lNom;
	private final Label lNiveau;
	private final Jauge jaugeVie;
	private final Rectangle rectEquipe;

	public Vignette(Image fond, SimpleStringProperty nom, String equipeCode, int niveau,
			ValeurCarac<IntegerProperty> vitalite) {
		this();
		setData(nom, niveau, fond, equipeCode, vitalite);
	}

	public Vignette() {
		getStyleClass().add("vignette");
		setAlignment(Pos.CENTER);

		lNom = new Label();
		lNom.getStyleClass().add("nom");
		lNom.setAlignment(Pos.CENTER);
		lNom.setTextOverrun(OverrunStyle.CLIP);
		lNiveau = new Label();
		lNiveau.getStyleClass().add("niveau");
		lNiveau.minWidthProperty().bind(lNiveau.textProperty().length().multiply(NIVEAU_FONT_WIDTH));
		GridPane top = new GridPane();
		top.addRow(0, lNiveau, lNom);
		top.getStyleClass().add("topEntite");
		getChildren().add(top);
		GridPane.setHalignment(lNom, HPos.CENTER);
		GridPane.setHgrow(lNom, Priority.ALWAYS);

		BorderPane bp = new BorderPane();
		bp.prefHeightProperty().bind(prefHeightProperty());
		getChildren().add(bp);

		jaugeVie = new Jauge(Orientation.VERTICAL, CARAC_CLASS.get(VITALITE));
		jaugeVie.setPrefWidth(JAUGE_VIE_WIDTH);
		bp.setLeft(jaugeVie);
		BorderPane.setAlignment(jaugeVie, Pos.BOTTOM_RIGHT);

		imgView = new ImageView(DataVue.DEFAULT_ICONE);
		imgView.fitHeightProperty().bind(bp.prefHeightProperty());
		imgView.setPreserveRatio(true);
		bp.setCenter(imgView);

		rectEquipe = new Rectangle(EQUIPE_WIDTH, 0);
		rectEquipe.heightProperty().bind(bp.prefHeightProperty());
		bp.setRight(rectEquipe);
		BorderPane.setAlignment(rectEquipe, Pos.BOTTOM_LEFT);

		top.prefWidthProperty().bind(jaugeVie.prefWidthProperty().add(rectEquipe.widthProperty().add(imgView.fitWidthProperty())));
		jaugeVie.prefHeightProperty().bind(imgView.fitHeightProperty());
	}

	public final void setData(SimpleStringProperty nom, int niveau, Image fond, String eCode, 
			ValeurCarac<IntegerProperty> vitalite) {
		lNom.textProperty().bind(nom);
		lNiveau.setText(niveau + "");
		imgView.setImage(fond);
		rectEquipe.setFill(Color.valueOf(eCode + EQUIPE_OPACITY));
		imgView.getParent().setStyle("-fx-background-color: #" + eCode + "88");
		jaugeVie.bind(vitalite);
	}

//	public void setNom(String nom) {
//		lNom.setText(nom);
//	}
//
//	public void setNiveau(int niveau) {
//		lNiveau.setText(niveau + "");
//	}
//
//	public void setImage(Image fond) {
//		imgView.setImage(fond);
//	}
//
//	public void setEquipeColor(String code) {
//		rectEquipe.setFill(Color.valueOf(code + EQUIPE_OPACITY));
//	}
//
//	public void setJaugeBind(ValeurCarac<IntegerProperty> vitalite) {
//		jaugeVie.bind(vitalite);
//	}

}
