/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.Entitemap;

import InC.Modele.Donnees.EntitePassive;
import InC.Vue.Map.Grille.MicroHUD.EntiteHUD;
import InC.Vue.Map.VueEntite;
import Main.Vue.DataVue;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * EntiteSprite.java
 *
 */
public class EntiteSprite extends ImageView implements VueEntite<ImageView> {

	private final EntiteHUD ehud;
	public final FadeTransition fadeT;

	public EntiteSprite(EntitePassive ep) {
		super(DataVue.getEntiteSprite(ep.idClasse));
		init();
		ehud = new EntiteHUD(ep);
		setPreserveRatio(true);
		setFocusTraversable(true);
		setFitWidth(getImage().getWidth());
		setFitHeight(getImage().getHeight());

		fadeT = new FadeTransition(new Duration(500), this);
		fadeT.setFromValue(0.8);
		fadeT.setToValue(0.6);
		fadeT.setAutoReverse(true);
		fadeT.setCycleCount(Transition.INDEFINITE);
	}

	public EntiteHUD getEhud() {
		return ehud;
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
		hover(estCible);
	}

}
