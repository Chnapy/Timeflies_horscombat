/*
 * 
 * 
 * 
 */
package Vue.Accueil.Content;

import Serializable.Personnages.HCPersonnage;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Accueil.java
 *
 */
public class Accueil extends ChoixPersos {

	private final ObservableList<String> equipes;
	public final ButAddPerso addPersoBtn;
	public final Button bSolo;
	public final Button bEquipe;

	public Accueil() {
		super("Choix des personnages");

		equipes = FXCollections.observableArrayList();
		ComboBox equipesBox = new ComboBox(equipes);
		TextField equipeField = new TextField();
		Button sauverEquipe = new Button("Sauver equipe");
		HBox eq = new HBox(equipesBox, equipeField, sauverEquipe);
		eq.setAlignment(Pos.CENTER);

		top.getChildren().add(eq);
		
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
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label(),
				new Label());

		addPersoBtn = new ButAddPerso();
		persosPane.getChildren().add(addPersoBtn);

		bSolo = new Button("Solo");
		bSolo.setPrefSize(100, 40);
		bEquipe = new Button("Par equipe");
		bEquipe.setPrefSize(100, 40);
		bottom.getChildren().addAll(bSolo, bEquipe);

		setGpBinding();
		actuBtn();
	}

	public void setData(HCPersonnage[] persos, HashMap<String, int[]> equipes) {
		this.equipes.addAll(equipes.keySet());
		setPersos(persos);
	}

	@Override
	public final void actuBtn() {
		for (ButPerso btn : listPersos) {
			if (btn.isSelected()) {
				bEquipe.setDisable(false);
				bSolo.setDisable(false);
				return;
			}
		}
		bEquipe.setDisable(true);
		bSolo.setDisable(true);
	}

	public void setIdNewPerso(int idEntite) {
		listPersos.get(listPersos.size() - 1).perso.id = idEntite;
	}
	
	public static class ButAddPerso extends Button {
		
		public ButAddPerso() {
			super("+");
			setPrefSize(100, 100);
		}
		
	}

}
