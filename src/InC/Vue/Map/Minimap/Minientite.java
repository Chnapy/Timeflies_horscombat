/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Minimap;

import InC.Modele.Donnees.EntitePassive;
import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.VueEntite;
import Serializable.InCombat.Orientation;
import Serializable.Position;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

/**
 * Minientite.java
 *
 */
public class Minientite extends StackPane implements VueEntite<Minientite> {

	private static final double POLYORIENT_WIDTH = 8, POLYORIENT_HEIGHT = 3;
	private static final double[][] POLYORIENT_POINTS = new double[][]{
		new double[]{ //NORD
			POLYORIENT_WIDTH / 2, 0,
			0, POLYORIENT_HEIGHT,
			POLYORIENT_WIDTH, POLYORIENT_HEIGHT
		},
		new double[]{ //SUD
			0, 0,
			POLYORIENT_WIDTH, 0,
			POLYORIENT_WIDTH / 2, POLYORIENT_HEIGHT
		},
		new double[]{ //OUEST
			POLYORIENT_HEIGHT, 0,
			POLYORIENT_HEIGHT, POLYORIENT_WIDTH,
			0, POLYORIENT_WIDTH / 2
		},
		new double[]{ //EST
			0, 0,
			POLYORIENT_HEIGHT, POLYORIENT_WIDTH / 2,
			0, POLYORIENT_WIDTH
		}
	};

	public final FadeTransition fadeT;

	private final Circle circle;
	private final Polygon polyOrient;
	private final Position posStart;

	public Minientite(EntitePassive e, Position posStart) {
		init();
		this.posStart = posStart;

		circle = new Circle();
		circle.setFill(Color.valueOf(e.equipe.colorCode + "FF"));
		circle.radiusProperty().bind(widthProperty().divide(4d).add(0.5));
		getChildren().add(circle);
		StackPane.setAlignment(circle, Pos.CENTER);

		polyOrient = new Polygon();
		polyOrient.getStyleClass().add("fleche_orient");
		polyOrient.setFill(Color.valueOf(e.equipe.colorCode + "FF"));
		getChildren().add(polyOrient);

		fadeT = new FadeTransition(new Duration(500), circle);
		fadeT.setFromValue(0.8);
		fadeT.setToValue(0.6);
		fadeT.setAutoReverse(true);
		fadeT.setCycleCount(Transition.INDEFINITE);

		changeOrientation(e.getBinding().orientation.get());
	}

	@Override
	public void hover(boolean hover) {
		if (hover) {
			fadeT.playFromStart();
		} else {
			fadeT.stop();
			circle.setOpacity(1);
		}
	}

	@Override
	public void pressed(boolean pressed) {
	}

	@Override
	public void position(Position position) {
		Platform.runLater(() -> {
			setTranslateX((position.x - posStart.x) * getWidth());
			setTranslateY((position.y - posStart.y) * getWidth());
		});
	}

	@Override
	public void orientation(Orientation orientation) {
		int i = -1;
		switch (orientation) {
			case NORD:
				i = 0;
				StackPane.setAlignment(polyOrient, Pos.TOP_CENTER);
				break;
			case SUD:
				i = 1;
				StackPane.setAlignment(polyOrient, Pos.BOTTOM_CENTER);
				break;
			case OUEST:
				i = 2;
				StackPane.setAlignment(polyOrient, Pos.CENTER_LEFT);
				break;
			case EST:
				i = 3;
				StackPane.setAlignment(polyOrient, Pos.CENTER_RIGHT);
				break;
		}
		polyOrient.getPoints().clear();
		for (double d : POLYORIENT_POINTS[i]) {
			polyOrient.getPoints().add(d);
		}
	}

	@Override
	public void estCible(boolean estCible) {
	}

	@Override
	public void alive(boolean alive) {
		if (!alive) {
			setDisable(true);
			setVisible(false);
		}
	}
}
