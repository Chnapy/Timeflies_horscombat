/*
 * 
 * 
 * 
 */
package Log.Vue;

import Main.Vue.Ecran;
import Log.Controleur.LogControleur.LogState;
import static Log.Controleur.LogControleur.LogState.NO_MDP;
import static Log.Controleur.LogControleur.LogState.NO_PSEUDO;
import static Log.Controleur.LogControleur.LogState.OK;
import Main.Controleur.MainControleur;
import Main.Vue.Vue;
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

		root.getChildren().addAll(titre, lPseudo, fPseudo, lMdp, fMdp, submit);
	}

	public void submit(LogState verifClient) {
		switch (verifClient) {
			case NO_PSEUDO:
				Vue.erreur("Pseudo non rentré");
				break;
			case NO_MDP:
				Vue.erreur("Mdp non rentré");
				break;
			case BAD_LOGS:
				Vue.erreur("Mauvais pseudo/mdp");
				setAllDisable(false);
				break;
			case OK:
				submit.setText("Connexion en cours...");
				setAllDisable(true);
				break;
		}
	}

}
