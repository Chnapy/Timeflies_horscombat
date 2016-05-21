/*
 * 
 * 
 * 
 */
package Vue.Accueil;

import Serializable.Logs.AnswerLogs;
import Serializable.Personnages.HCPersonnage;
import Vue.Accueil.Content.Accueil;
import Vue.Accueil.Content.AttenteCombat;
import Vue.Accueil.Content.Content;
import Vue.Accueil.Content.CreationPerso;
import Vue.Ecran;
import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * PagePrincipale.java
 *
 */
public class PagePrincipale extends Ecran<BorderPane> {
	
	private static final double WIDTH = 1000;
	private static final double HEIGHT = 600;

	private final Label lPseudo;
	private final Label lArgent;
	private final Label lRatio;
	public final Button bPersos;
	public final Button bJouer;
	public final Button bMenu;
	public final Button bAmis;
	public final Button bAccueil;

	public final Accueil accueil;
	public final AttenteCombat attente;
	public final CreationPerso creaPerso;

	public PagePrincipale() {
		super(new BorderPane(), WIDTH, HEIGHT, Color.BURLYWOOD);

		bPersos = new Button("Mes personnages");
		bJouer = new Button("Jouer");
		HBox buttons = new HBox(/*bPersos, bJouer*/);
		buttons.setPrefWidth(330);
		buttons.getStyleClass().add("buttons");

		Text lTitle = new Text("TimeFlies");
		lTitle.setFont(Font.font(30));
		bAccueil = new Button("Accueil");
		bAccueil.setDisable(true);
		VBox title = new VBox(lTitle, bAccueil);
		title.setAlignment(Pos.CENTER);
		title.setPrefWidth(340);
		title.getStyleClass().add("title");

		GridPane compte = new GridPane();
		lPseudo = new Label();
		compte.add(lPseudo, 1, 0);
		lArgent = new Label();
		compte.add(lArgent, 1, 1);
		lRatio = new Label();
		compte.add(lRatio, 1, 2);
		compte.setPrefWidth(330);
		compte.getStyleClass().add("compte");

		HBox top = new HBox(buttons, title, compte);

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
		root.setCenter(content);
	}

	public final Content getContent() {
		return (Content) root.getCenter();
	}

	public void setData(AnswerLogs.InfosCompte infosCompte, HCPersonnage[] persos, HashMap<String, int[]> equipes) {
		lPseudo.setText(infosCompte.pseudo);
		lArgent.setText(infosCompte.argent + " $");
		lRatio.setText("Ratio : " + infosCompte.ratio);
		accueil.setData(persos, equipes);
	}
	
	private void allEnable() {
		bPersos.setDisable(false);
		bJouer.setDisable(false);
		bAccueil.setDisable(false);
		bAmis.setDisable(false);
		bMenu.setDisable(false);
	}

}
