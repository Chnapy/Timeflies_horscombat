/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Minimap;

import InC.Modele.Donnees.EntitePassive;
import InC.Vue.Map.VueEntite;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Minientite.java
 *
 */
public class Minientite extends Circle implements VueEntite<Minientite> {

	public final FadeTransition fadeT;
	
	public Minientite(EntitePassive e) {
		init();
		setFill(Color.valueOf(e.equipe.colorCode + "FF"));
		
		fadeT = new FadeTransition(new Duration(500), this);
		fadeT.setFromValue(0.8);
		fadeT.setToValue(0.6);
		fadeT.setAutoReverse(true);
		fadeT.setCycleCount(Transition.INDEFINITE);
	}

	@Override
	public void hover(boolean hover) {
		if (hover) {
			fadeT.playFromStart();
		} else {
			fadeT.stop();
			setOpacity(1);
		}
	}

	@Override
	public void pressed(boolean pressed) {
	}

	@Override
	public void position(Position position) {
	}

	@Override
	public void orientation(Orientation orientation) {
	}

	@Override
	public void estCible(boolean estCible) {
	}
}
