/*
 * 
 * 
 * 
 */
package InC.Modele.Camera;

import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.Grille.Grille;
import Main.Vue.Vue;
import Serializable.Duo;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

/**
 * Camera.java
 *
 */
public class Camera {

	private static final double MAX_SCROLL = 256, MIN_SCROLL = 32,
			INITIAL_SCALE = 1, SCALE_INCREMENT = 0.1;
	private static final Duration D_SCALE_DEFAULT = new Duration(500),
			D_TRANSLATE_DEFAULT = new Duration(200);

	private final TranslateTransition tranTran;
	private final ScaleTransition scalTran;

	private final Grille grille;
	private final Scalable node;
	private final SimpleBooleanProperty midButtonPressed;
	private final Duo<Double, Double> pos;

	private double scale;

	public Camera(Grille grille) {
		this.grille = grille;
		this.node = new Scalable(grille);
		midButtonPressed = new SimpleBooleanProperty(false);
		pos = new Duo(0d, 0d);

		this.tranTran = new TranslateTransition();
		tranTran.setNode(node);
		tranTran.setInterpolator(Interpolator.LINEAR);
		tranTran.setOnFinished((e) -> {
			if (midButtonPressed.get()) {
				moveTo(pos.first, pos.second);
			}
		});
		this.scalTran = new ScaleTransition(D_SCALE_DEFAULT, node);
		scalTran.setOnFinished((e) -> {
			Bounds b = grille.getTileMap().localToScene(grille.getTileMap().getBoundsInLocal());
			if (b.getMinX() > 0 || b.getMinY() > 0
					|| b.getWidth() + b.getMinX() < Vue.PRIMARYSTAGE.getWidth()
					|| b.getHeight() + b.getMinY() < Vue.PRIMARYSTAGE.getHeight()) {
				centerCamera();
			}
		});

		grille.setOnMousePressed((e) -> {
			if (e.getButton() == MouseButton.MIDDLE) {
				midButtonPressed.set(true);
				moveTo(e.getSceneX(), e.getSceneY());
			}
		});
		grille.setOnMouseReleased((e) -> {
			if (e.getButton() == MouseButton.MIDDLE) {
				midButtonPressed.set(false);
			}
		});
		grille.setOnMouseDragged((e) -> {
			if (e.isMiddleButtonDown()) {
				pos.first = e.getSceneX();
				pos.second = e.getSceneY();
			}
		});
		grille.setOnScroll((e) -> {
			double s = scale + (e.getDeltaY() < 0 ? -1 : 1) * SCALE_INCREMENT;
			scale(s);
		});
		scale(INITIAL_SCALE);
	}

	public void centerCamera() {
		centerCamera(D_TRANSLATE_DEFAULT);
	}

	public void centerCamera(Duration time) {
		moveTo(Vue.PRIMARYSTAGE.getWidth() / 4, Vue.PRIMARYSTAGE.getHeight() / 4, time);
	}

	public void moveTo(double x, double y) {
		moveTo(x, y, D_TRANSLATE_DEFAULT);
	}

	public void moveTo(double x, double y, Duration time) {
		tranTran.stop();
		tranTran.setDuration(time);
		double offsetX = getOffsetX(x);
		double offsetY = getOffsetY(y);
		tranTran.setByX(offsetX);
		tranTran.setByY(offsetY);

		tranTran.playFromStart();
	}

	private double getOffsetX(double x) {
		double sceneWidth = Vue.PRIMARYSTAGE.getScene().getWidth();
		double byX = sceneWidth / 2 - x;
		Bounds b = grille.getTileMap().localToScene(grille.getTileMap().getBoundsInLocal());
		double minX = b.getMinX();
		double bWidth = b.getWidth();

		if (minX + byX > 0) {
			return -minX;
		} else if (Math.abs(minX + byX) > bWidth - sceneWidth) {
			return Math.abs(minX) - (bWidth - sceneWidth);
		}

		return byX;
	}

	private double getOffsetY(double y) {
		double sceneHeight = Vue.PRIMARYSTAGE.getScene().getHeight();
		double byY = sceneHeight / 2 - y;
		Bounds b = grille.getTileMap().localToScene(grille.getTileMap().getBoundsInLocal());
		double minY = b.getMinY();
		double bHeight = b.getHeight();

		if (minY + byY > 0) {
			return -minY;
		} else if (Math.abs(minY + byY) > bHeight - sceneHeight) {
			return Math.abs(minY) - (bHeight - sceneHeight);
		}

		return byY;
	}

	public void jumpRight(double x) {
		node.setTranslateX(node.getTranslateX() - x);
	}

	public void jumpBottom(double y) {
		node.setTranslateY(node.getTranslateY() - y);
	}

	public void initScale() {
		Platform.runLater(() -> {
			double s = Vue.PRIMARYSTAGE.getWidth() / grille.getWidth();
			scale(s);
		});
	}

	public final void scale(double s) {
		scale(s, D_SCALE_DEFAULT);
	}

	public void scale(double s, Duration time) {
		if (AbstractMap.TILE_WIDTH * s > MAX_SCROLL) {
			s = MAX_SCROLL / AbstractMap.TILE_WIDTH;
		} else if (AbstractMap.TILE_WIDTH * s < MIN_SCROLL) {
			s = MIN_SCROLL / AbstractMap.TILE_WIDTH;
		}
		scale = s;
		scalTran.stop();
		scalTran.setDuration(time);
		scalTran.setFromX(node.getScaleX());
		scalTran.setFromY(node.getScaleY());
		scalTran.setToX(s);
		scalTran.setToY(s);
		scalTran.playFromStart();
	}

	private static class Scalable extends Node {

		public Scalable(Grille grille) {
			for (Node n : grille.scalables) {
				n.scaleXProperty().bind(scaleXProperty());
				n.scaleYProperty().bind(scaleYProperty());
			}
			grille.translateXProperty().bind(translateXProperty());
			grille.translateYProperty().bind(translateYProperty());
//			grille.cacheProperty().bind(cacheProperty());
//			grille.cacheHintProperty().bind(cacheHintProperty());
//			setCache(true);
//			setCacheHint(CacheHint.SPEED);
		}

		@Override
		protected NGNode impl_createPeer() {
			return null;
		}

		@Override
		public BaseBounds impl_computeGeomBounds(BaseBounds bb, BaseTransform bt) {
			return null;
		}

		@Override
		protected boolean impl_computeContains(double d, double d1) {
			return false;
		}

		@Override
		public Object impl_processMXNode(MXNodeAlgorithm mxna, MXNodeAlgorithmContext mxnac) {
			return null;
		}

	}

}
