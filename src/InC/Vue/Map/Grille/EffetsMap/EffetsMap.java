/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap;

import InC.Vue.Map.Grille.EffetsMap.Effet.DefaultEffet;
import InC.Vue.Map.Grille.EffetsMap.Effet.Effet;
import static Main.Controleur.MainControleur.EXEC;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;

/**
 * EffetsMap.java
 *
 */
public class EffetsMap extends Pane {

	private Runnable wait;
	private final SimpleObjectProperty<Effet> effetActu;
	private final DefaultEffet deffet;

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
	}

	public void lancerEffet(int idClasse, int duration, Position from, ArrayList<Position> to) {
		effetActu.set(getEffet(idClasse));
		effetActu.get().lancerEffet(duration, from, to);
		wait = () -> {
			System.out.println("DEBUT : " + duration);
			try {
				Thread.sleep(duration);
			} catch (InterruptedException ex) {
			}
			System.out.println("FIN");
			effetActu.get().stop();
			getChildren().remove(effetActu.get());
		};
		EXEC.submit(wait);
	}

	public void stopEffet() {
		effetActu.get().stop();
	}

	private Effet getEffet(int idClasse) {
		return deffet;
	}

}
