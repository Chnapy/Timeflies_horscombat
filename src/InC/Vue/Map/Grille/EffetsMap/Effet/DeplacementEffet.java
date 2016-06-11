/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap.Effet;

import InC.Controleur.InCControleur;
import InC.Modele.Timer.ActionSort;
import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.Grille.Entitemap.EntiteSprite;
import Serializable.Position;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * DeplacementEffet.java
 *
 */
public class DeplacementEffet extends Effet {

	private final TranslateTransition tTran;

	public DeplacementEffet() {
		tTran = new TranslateTransition();
	}

	@Override
	public void lancerEffet(ActionSort as, InCControleur controleur) {
		EntiteSprite es = controleur.getEcran().maps.getEntiteSprite(as.idLanceur);

		Position from = AbstractMap.getRealTilePos(as.source),
				to = AbstractMap.getRealTilePos(as.dest.get(0));

		tTran.setByX(to.x - from.x);
		tTran.setByY(to.y - from.y);

		tTran.setDuration(new Duration(as.duree.get()));
		tTran.setNode(es);
		tTran.play();
	}

	@Override
	public void stop() {
		tTran.stop();
	}

	@Override
	public void interrupt() {
		tTran.jumpTo(Duration.ZERO);
		stop();
	}

}
