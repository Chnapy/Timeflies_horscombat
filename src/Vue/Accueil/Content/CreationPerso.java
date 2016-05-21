/*
 * 
 * 
 * 
 */
package Vue.Accueil.Content;

import Serializable.Personnages.HCPersonnage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * CreationPerso.java
 *
 */
public class CreationPerso extends ChoixPersos {

	public final TextField tf;
	public final Button submit;
	private final ToggleGroup tg;

	public CreationPerso() {
		super("Choix de la classe");

		tf = new TextField();
		tf.setOnAction((e) -> actuBtn());

		gp.addColumn(0,
				new Label("Nom"),
				new Label("Classe"),
				new Label("Niveau S."),
				new Label("Vitalité"),
				new Label("T. action"),
				new Label("T. supp."),
				new Label("Vitesse"),
				new Label("Fatigue"));
		gp.addColumn(1,
				tf,
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label());

		tg = new ToggleGroup();

		submit = new Button("Créer le personnage");
		submit.setPrefSize(100, 40);
		bottom.getChildren().addAll(submit);

		setGpBinding();
		actuBtn();
	}

	public void loadData(HCPersonnage[] persos) {
		setPersos(persos);
		listPersos.forEach((p) -> p.setToggleGroup(tg));
	}

	@Override
	public final void actuBtn() {
		submit.setDisable(tg.getSelectedToggle() == null || tf.getText().trim().isEmpty());
	}

}
