/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap;

import InC.Controleur.InCControleur;
import InC.Modele.Timer.ActionSort;
import InC.Vue.Map.Grille.EffetsMap.Effet.DefaultEffet;
import InC.Vue.Map.Grille.EffetsMap.Effet.DeplacementEffet;
import InC.Vue.Map.Grille.EffetsMap.Effet.Effet;
import Main.Modele.Data;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;

/**
 * EffetsMap.java
 *
 */
public class EffetsMap extends Pane {

	private InCControleur controleur;
	private final DefaultEffet deffet;
	private final DeplacementEffet deplacEffet;

	private final SimpleObjectProperty<Effet> effetActu;

	public EffetsMap() {
		effetActu = new SimpleObjectProperty();
		effetActu.addListener((ov, t, t1) -> {
			try {
				t.stop();
				getChildren().remove(t);
			} catch (NullPointerException e) {
			}
			getChildren().add(t1);
		});

		deffet = new DefaultEffet();
		deplacEffet = new DeplacementEffet();
	}
	
	public void setControleur(InCControleur controleur) {
		this.controleur = controleur;
	}

	public void lancerEffet(ActionSort as) {
		effetActu.set(getEffet(as.sort.idClasse));
		effetActu.get().lancerEffet(as, controleur);
//		System.err.println("PLAY");
	}
	
	public void interruptEffet() {
		effetActu.get().interrupt();
	}

	public void stopEffet() {
		effetActu.get().stop();
	}

	private Effet getEffet(int idClasse) {
		switch (idClasse) {
			case Data.DEPLACEMENT_IDCLASSE:
				return deplacEffet;
			default:
				return deffet;
		}
	}

}
