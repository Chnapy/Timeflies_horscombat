/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.EffetsMap.Effet;

import InC.Controleur.InCControleur;
import InC.Modele.Timer.ActionSort;
import InC.Vue.Map.Grille.AbstractMap;
import Serializable.Position;
import java.util.List;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * DefaultEffet.java
 *
 */
public class DefaultEffet extends Effet {

	private static final double MARGIN_BOTTOM = 100;

	private final SequentialTransition st;
	private final PauseTransition pt;
	private final ParallelTransition pat;

	public DefaultEffet() {

		pt = new PauseTransition();
		pat = new ParallelTransition();
		st = new SequentialTransition(pt, pat);
		st.setOnFinished((e) -> stop());
	}

	@Override
	public void lancerEffet(ActionSort as, InCControleur controleur) {
		pt.setDuration(new Duration(0));
		
		List<Position> to = as.dest;
		Position from = as.source;
		int duration = as.duree.get();
		
		to.forEach((p) -> {
			TranslateTransition tt = new TranslateTransition(new Duration(duration));
			tt.setFromX(AbstractMap.getRealTileX(from.x, from.y) + AbstractMap.TILE_WIDTH / 2);
			tt.setFromY(AbstractMap.getRealTileY(from.x, from.y) + AbstractMap.TILE_HEIGHT / 2 - MARGIN_BOTTOM);
			tt.setToX(AbstractMap.getRealTileX(p.x, p.y) + AbstractMap.TILE_WIDTH / 2);
			tt.setToY(AbstractMap.getRealTileY(p.x, p.y) + AbstractMap.TILE_HEIGHT / 2);
			pat.getChildren().add(tt);
			
			Circle c = new Circle(10);
			tt.setNode(c);
			getChildren().add(c);
		});

		setVisible(true);
		st.playFromStart();
	}
	
	@Override
	public void stop() {
		setVisible(false);
		st.stop();
		pat.getChildren().clear();
		getChildren().clear();
	}

	@Override
	public void interrupt() {
		stop();
	}

}
