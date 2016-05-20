/*
 * 
 * 
 * 
 */
package Vue.Accueil.Content;

import Serializable.Combat.AskCombat.TypeCombat;
import Serializable.Combat.InfosCombat.DonneeJoueur;
import Serializable.Combat.InfosCombat.DonneePerso;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * AttenteCombat.java
 *
 */
public class AttenteCombat extends Content {

	static class JoueurList extends VBox {

		private Image pretImage;

		private final HBox joueurLigne;
		private final ImageView pretImageView;
		private final Label niveau;
		private final Label pseudo;

		public JoueurList(DonneeJoueur dj) {

			pretImageView = new ImageView();
			niveau = new Label(dj.niveau + "");
			pseudo = new Label(dj.nom);
			pret(dj.pret);

			joueurLigne = new HBox(pretImageView, niveau, pseudo);
			getChildren().add(joueurLigne);

			for (DonneePerso dp : dj.persos) {
				PersoLigne pl = new PersoLigne(dp);
				getChildren().add(pl);
			}
		}

		public final void pret(boolean pret) {
			if (pret) {
//				pretImageView.setImage(pretImage);
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
			nom = new Label(dp.nomClasse);

			getChildren().addAll(niveau, nom);
		}

	}

	private final Text soustitre;
	private final ToggleButton readyBut;
	private final Text content;
	private final Text mapNom;
	private final Text joueurTitre;
	private final Text persoTitre;
	private final VBox joueursBox;

	private TypeCombat type;

	public AttenteCombat() {
		Text titre = new Text("Salon d'attente");
		soustitre = new Text();
		readyBut = new ToggleButton("Prêt");
		VBox top = new VBox(titre, soustitre, readyBut);
		setTop(top);

		Text mapTitre = new Text("Map");
		mapNom = new Text();
		VBox left = new VBox(mapTitre, mapNom);
		setLeft(left);

		joueurTitre = new Text();
		persoTitre = new Text();
		joueursBox = new VBox();
		VBox right = new VBox(joueurTitre, persoTitre, joueursBox);
		setRight(right);

		content = new Text();
		ScrollPane sp = new ScrollPane(content);
		setCenter(sp);
	}

	public void newAttente(TypeCombat type) {
		this.type = type;
		readyBut.setDisable(true);
		mapNom.setText("");
		joueurTitre.setText("");
		persoTitre.setText("");
		joueursBox.getChildren().clear();
		content.setText("");
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

	private void addText(String text) {
		content.setText(content.getText() + "\n" + text);
	}

}
