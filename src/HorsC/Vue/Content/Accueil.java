/*
 * 
 * 
 * 
 */
package HorsC.Vue.Content;

import Serializable.HorsCombat.HCPersonnage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Accueil.java
 *
 */
public class Accueil extends ChoixPersos {

	public final ButAddPerso addPersoBtn;
	public final Button bSolo;
	public final Button bEquipeCps;
	public final Button bEquipe;

	public Accueil() {
		super("Choix des personnages");

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

		bSolo = new Button("Chacun pour soi");
		bSolo.setPrefHeight(40);
		bEquipeCps = new Button("Par equipe - Chacun pour soi");
		bEquipeCps.setPrefHeight(40);
		bEquipe = new Button("Par equipe");
		bEquipe.setPrefHeight(40);
		bottom.getChildren().addAll(bSolo, bEquipeCps, bEquipe);

		setGpBinding();
		actuBtn();
	}

	public void setData(HCPersonnage[] persos) {
		setPersos(persos);
	}

	@Override
	public final void actuBtn() {
		int s = 0;
		for (ButPerso btn : listPersos) {
			if (btn.isSelected()) {
				s++;
				bSolo.setDisable(s != 1);
				bEquipe.setDisable(false);
				bEquipeCps.setDisable(false);
			}
		}
		if (s == 0) {
			bSolo.setDisable(true);
			bEquipe.setDisable(true);
			bEquipeCps.setDisable(true);
		}
	}

	public void setIdNewPerso(int idEntite) {
		listPersos.get(listPersos.size() - 1).perso.id = idEntite;
	}

	@Override
	public boolean toAccueil() {
		return false;
	}

	public static class ButAddPerso extends Button {

		public ButAddPerso() {
			super("+");
			setPrefSize(100, 100);
		}

	}

}
