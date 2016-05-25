/*
 * 
 * 
 * 
 */
package HorsC.Vue;

import Main.Vue.Ecran;
import HorsC.Vue.Content.Accueil;
import HorsC.Vue.Content.AttenteCombat;
import HorsC.Vue.Content.Content;
import HorsC.Vue.Content.CreationPerso;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.Log.Log.InfosCompte;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * HorsCVue.java
 *
 */
public class HorsCVue extends Ecran<BorderPane> {

	private static final double WIDTH = 1000;
	private static final double HEIGHT = 600;

	private final Label lPseudo;
	private final Label lArgent;
	private final Label lRatio;
	public final Button bMenu;
	public final Button bAmis;
	public final Button bAccueil;

	public final Accueil accueil;
	public final AttenteCombat attente;
	public final CreationPerso creaPerso;

	public HorsCVue() {
		super(new BorderPane(), WIDTH, HEIGHT, Color.BURLYWOOD);

		HBox buttons = new HBox();
		buttons.getStyleClass().add("buttons");

		Text lTitle = new Text("TimeFlies");
		lTitle.setFont(Font.font(30));
		bAccueil = new Button("Accueil");
		bAccueil.setDisable(true);
		VBox title = new VBox(lTitle, bAccueil);
		title.setAlignment(Pos.CENTER);
		title.getStyleClass().add("title");

		GridPane compte = new GridPane();
		lPseudo = new Label();
		compte.add(lPseudo, 1, 0);
		lArgent = new Label();
		compte.add(lArgent, 1, 1);
		lRatio = new Label();
		compte.add(lRatio, 1, 2);
		compte.getStyleClass().add("compte");

		GridPane top = new GridPane();
		top.addRow(0, buttons, title, compte);
		ColumnConstraints columnCons = new ColumnConstraints();
		columnCons.setPercentWidth(33.334);
		top.getColumnConstraints().addAll(columnCons, columnCons, columnCons);

		bMenu = new Button("Menu");

		bAmis = new Button("Amis");
		bAmis.setAlignment(Pos.CENTER_RIGHT);

		Pane spacer = new Pane();

		HBox bottom = new HBox(bMenu, spacer, bAmis);
		HBox.setHgrow(spacer, Priority.SOMETIMES);

		root.setTop(top);
		root.setBottom(bottom);

		accueil = new Accueil();
		attente = new AttenteCombat();
		creaPerso = new CreationPerso();

		setContent(accueil);
	}

	public final void setContent(Content content) {
		bAccueil.setDisable(content == accueil);
		root.setCenter(content);
	}

	public final Content getContent() {
		return (Content) root.getCenter();
	}

	public void setData(InfosCompte infosCompte, HCPersonnage[] persos) {
		lPseudo.setText("Pseudo : " + infosCompte.pseudo + " [" + infosCompte.idjoueur + "]");
		lArgent.setText("Argent : " + infosCompte.argent + " $");
		lRatio.setText("Niveau symbolique : " + infosCompte.niveauS + " [" + infosCompte.ratio + "]");
		accueil.setData(persos);
	}

}
