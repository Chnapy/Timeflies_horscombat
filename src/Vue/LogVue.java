/*
 * 
 * 
 * 
 */
package Vue;

import Controleur.LogControleur.LogState;
import static Controleur.LogControleur.LogState.NO_MDP;
import static Controleur.LogControleur.LogState.NO_PSEUDO;
import static Controleur.LogControleur.LogState.OK;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * LogVue.java
 *
 */
public class LogVue extends Ecran<VBox> {

	private final Label titre;
	private final Label lPseudo;
	private final Label lMdp;
	public final TextField fPseudo;
	public final PasswordField fMdp;
	public final Button submit;
	private final Label lDetails;

	public LogVue() {
		super(new VBox(), Color.ANTIQUEWHITE);
		root.setPadding(new Insets(20));
		root.setSpacing(5);

		titre = new Label("Test_Client");
		titre.setFont(Font.font(36));

		lPseudo = new Label("Votre pseudo");

		lMdp = new Label("Votre mot de passe");

		fPseudo = new TextField();

		fMdp = new PasswordField();

		submit = new Button("Se connecter");
		submit.setDefaultButton(true);

		lDetails = new Label();
		lDetails.setTextFill(Color.RED);

		root.getChildren().addAll(titre, lPseudo, fPseudo, lMdp, fMdp, submit, lDetails);
	}

	public void submit(LogState verifClient) {
		switch (verifClient) {
			case NO_PSEUDO:
				lDetails.setText("Pseudo non rentré");
				break;
			case NO_MDP:
				lDetails.setText("Mdp non rentré");
				break;
			case OK:
				lDetails.setText("Connexion en cours...");
				setAllDisable(true);
				lDetails.setDisable(false);
				break;
			case BAD_LOGS:
				lDetails.setText("Mauvais pseudo/mdp");
				setAllDisable(false);
				break;
			case NO_CONNECTION:
				lDetails.setText("Connexion au serveur impossible");
				setAllDisable(false);
				break;
		}
	}

}
