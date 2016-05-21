/*
 * 
 * 
 * 
 */
package Vue.Accueil.Content;

import Serializable.Combat.AskCombat.TypeCombat;
import Serializable.Combat.InfosCombat.DonneeJoueur;
import Serializable.Combat.InfosCombat.DonneePerso;
import Vue.Vue;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * AttenteCombat.java
 *
 */
public class AttenteCombat extends Content {

	private static final Insets MARGINLEFT_PERSOS = new Insets(0, 0, 0, 25);

	private final Text soustitre;
	public final ToggleButton readyBut;
	private final VBox content;
	private final Text mapNom;
	private final Text joueurTitre;
	private final Text persoTitre;
	private final VBox joueursBox;

	private TypeCombat type;

	public AttenteCombat() {
		Insets defaultPadding = new Insets(5);
		Text titre = new Text("Salon d'attente");
		titre.setFont(Font.font(26));
		soustitre = new Text();
		readyBut = new ToggleButton("Prêt");
		readyBut.setPrefWidth(100);
		VBox top = new VBox(titre, soustitre, readyBut);
		top.setAlignment(Pos.CENTER);
		top.setPadding(defaultPadding);
		setTop(top);
		top.setId("top");

		Text mapTitre = new Text("Map");
		mapTitre.setFont(Font.font(20));
		mapNom = new Text();
		VBox left = new VBox(mapTitre, mapNom);
		left.setPrefWidth(250);
		left.setAlignment(Pos.TOP_CENTER);
		left.setPadding(defaultPadding);
		setLeft(left);
		left.setId("left");

		joueurTitre = new Text();
		joueurTitre.setFont(Font.font(20));
		persoTitre = new Text();
		persoTitre.setFont(Font.font(16));
		joueursBox = new VBox();
		VBox vbright = new VBox(joueurTitre, persoTitre, joueursBox);
		VBox.setMargin(persoTitre, MARGINLEFT_PERSOS);
		vbright.setAlignment(Pos.TOP_LEFT);
		vbright.setPrefWidth(250);
		ScrollPane right = new ScrollPane(vbright);
		right.setPrefViewportWidth(250);
		vbright.setPadding(defaultPadding);
		setRight(right);
		vbright.setId("right");

		content = new VBox();
		content.setMaxWidth(500);
		content.setPadding(defaultPadding);
		content.setSpacing(5);
		ScrollPane center = new ScrollPane(content);
		center.setPrefViewportWidth(500);
		setCenter(center);
		center.setId("center");
	}

	public void estPret(long idJoueur, boolean pret) {
		try {
			getJoueurList(idJoueur).pret(pret);
		} catch (NullPointerException ex) {
		}
	}

	public void newAttente(TypeCombat type) {
		this.type = type;
		readyBut.setDisable(true);
		mapNom.setText("");
		joueurTitre.setText("");
		persoTitre.setText("");
		joueursBox.getChildren().clear();
		content.getChildren().clear();
		soustitre.setText("Combat souhaité : " + type);
		addText("Recherche d'une partie en " + type + " ...");
	}

	public void partieTrouvee(ArrayList<DonneeJoueur> donnees, int nbrPersos, String nomMap) {
		addText("Partie trouvée : " + donnees.size() + " joueurs et " + nbrPersos + " persos présents."
				+ "\nMap : " + nomMap);
		mapNom.setText(nomMap);
		joueurTitre.setText(donnees.size() + " joueurs");
		persoTitre.setText(nbrPersos + " persos");
		donnees.stream().forEach((dj) -> {
			joueursBox.getChildren().add(new JoueurList(dj));
		});
		if (donnees.size() > 1) {
			readyBut.setDisable(false);
		}
	}

	public void newJoueur(DonneeJoueur dj) {
		addText("Un joueur rejoint la partie : " + dj.nom + " - " + dj.persos.size() + " persos.");
		joueursBox.getChildren().add(new JoueurList(dj));
		readyBut.setDisable(false);
	}

	public void lancementCombat() {
		addText("Lancement du combat !");
	}

	public void rmJoueur(long idJoueur) {
		try {
			joueursBox.getChildren().remove(getJoueurList(idJoueur));
		} catch (NullPointerException ex) {
		}
	}

	private JoueurList getJoueurList(long idJoueur) {
		JoueurList jl;
		for (int i = 0; i < joueursBox.getChildren().size(); i++) {
			jl = (JoueurList) joueursBox.getChildren().get(i);
			if (jl.idJoueur == idJoueur) {
				return jl;
			}
		}
		return null;
	}

	private void addText(String text) {
		content.getChildren().add(new Text(text));
	}

	static class JoueurList extends VBox {

		private static final Image pretImage = Vue.PRELOADIMAGE.get("pret");

		public final long idJoueur;
		private final HBox joueurLigne;
		private final ImageView pretImageView;
		private final Label niveau;
		private final Label pseudo;

		public JoueurList(DonneeJoueur dj) {

			idJoueur = dj.id;
			pretImageView = new ImageView();
			pretImageView.setFitWidth(10);
			niveau = new Label(dj.niveau + "");
			niveau.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			niveau.setTextFill(Color.BEIGE);
			pseudo = new Label(dj.nom);
			pseudo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			pseudo.setTextFill(Color.BEIGE);
			pret(dj.pret);

			joueurLigne = new HBox(pretImageView, niveau, pseudo);
			joueurLigne.setSpacing(4);
			joueurLigne.setAlignment(Pos.BOTTOM_LEFT);
			getChildren().add(joueurLigne);

			for (DonneePerso dp : dj.persos) {
				PersoLigne pl = new PersoLigne(dp);
				getChildren().add(pl);
				VBox.setMargin(pl, MARGINLEFT_PERSOS);
			}
		}

		public final void pret(boolean pret) {
			if (pret) {
				pretImageView.setImage(pretImage);
			} else {
				pretImageView.setImage(null);
			}
		}

	}

	static class PersoLigne extends HBox {

		private final Label niveau;
		private final Label nom;

		public PersoLigne(DonneePerso dp) {
			niveau = new Label(dp.niveau + "");
			niveau.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			niveau.setTextFill(Color.LIGHTGREY);
			nom = new Label(dp.nomClasse);
			nom.setTextFill(Color.GAINSBORO);

			getChildren().addAll(niveau, nom);
			setSpacing(2);
		}

	}

}
