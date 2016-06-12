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
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

/**
 * Camera.java
 *
 */
public class Camera {

	private static final double MAX_SCROLL = 256, MIN_SCROLL = 32,
			INITIAL_SCALE = 1, SCALE_INCREMENT = 0.3;
	private static final Duration D_SCALE_DEFAULT = new Duration(500),
			D_TRANSLATE_DEFAULT = new Duration(200),
			D_MOVETONODE_DEFAULT = new Duration(500);

	private final TranslateTransition translater;
	private final ScaleTransition scaler;
	private final ParallelTransition cam;

	private final Grille grille;
	private final Scalable node;

	private final SimpleBooleanProperty midButtonPressed;
	private final Duo<Double, Double> posActu;
	private final SimpleDoubleProperty scaleActu;

	public Camera(Grille grille) {
		this.grille = grille;
		this.node = new Scalable(grille);
		midButtonPressed = new SimpleBooleanProperty(false);
		posActu = new Duo(0d, 0d);
		scaleActu = new SimpleDoubleProperty();

		translater = new TranslateTransition(D_SCALE_DEFAULT);
		scaler = new ScaleTransition(D_SCALE_DEFAULT);
		cam = new ParallelTransition(node, scaler, translater);

		cam.setInterpolator(Interpolator.LINEAR);
		cam.setOnFinished((e) -> {
			if (midButtonPressed.get()) {
				moveTo(posActu.first, posActu.second);
			}
		});
//		scalTran.setOnFinished((e) -> {
//			Bounds b = grille.getTileMap().localToScene(grille.getTileMap().getBoundsInLocal());
//			if (b.getMinX() > 0 || b.getMinY() > 0
//					|| b.getWidth() + b.getMinX() < Vue.PRIMARYSTAGE.getWidth()
//					|| b.getHeight() + b.getMinY() < Vue.PRIMARYSTAGE.getHeight()) {
//				centerCamera();
//			}
//		});

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
				posActu.first = e.getSceneX();
				posActu.second = e.getSceneY();
			}
		});
		grille.setOnScroll((e) -> {
			if (cam.getStatus() != Animation.Status.RUNNING) {
				double s = scaleActu.get() + (e.getDeltaY() < 0 ? -1 : 1) * SCALE_INCREMENT;
				scale(s);
			}
		});
		scale(INITIAL_SCALE);
	}

	public void initScale() {
		Platform.runLater(() -> {
			double s = Vue.PRIMARYSTAGE.getWidth() / grille.getWidth();
			scale(s);
		});
	}

	public void centerCamera() {
		centerCamera(D_TRANSLATE_DEFAULT);
	}

	public void centerCamera(Duration time) {
		moveTo(Vue.PRIMARYSTAGE.getWidth() / 4, Vue.PRIMARYSTAGE.getHeight() / 4, time);
	}

	public void moveToNode(Node node) {
		moveToNode(node, scaleActu.get());
	}

	public void moveToNode(Node node, double scale) {
		moveToNode(node, scale, D_MOVETONODE_DEFAULT);
	}

	public void moveToNode(Node node, double scale, Duration time) {
		Bounds b = node.localToScene(node.getBoundsInLocal());
		moveAndScale(scale,
				b.getMinX() + b.getWidth() / 2,
				b.getMinY() + b.getHeight() / 2,
				time);
	}

	public void moveTo(double x, double y) {
		moveTo(x, y, D_TRANSLATE_DEFAULT);
	}

	public void moveTo(double x, double y, Duration time) {
		moveAndScale(scaleActu.get(), x, y, time);
	}

	public final void scale(double s) {
		scale(s, D_SCALE_DEFAULT);
	}

	public void scale(double s, Duration time) {
		moveAndScale(s,
				Vue.PRIMARYSTAGE.getScene().getWidth() / 2,
				Vue.PRIMARYSTAGE.getScene().getHeight() / 2,
				time);
	}

	public void moveAndScale(double s, double x, double y, Duration time) {
		if (cam.getStatus() == Animation.Status.RUNNING) {
			cam.jumpTo(cam.getTotalDuration());
		}

		if (AbstractMap.TILE_WIDTH * s > MAX_SCROLL) {
			s = MAX_SCROLL / AbstractMap.TILE_WIDTH;
		} else if (AbstractMap.TILE_WIDTH * s < MIN_SCROLL) {
			s = MIN_SCROLL / AbstractMap.TILE_WIDTH;
		}
		double diff = s - scaleActu.get();
		double offsetX = getOffsetX(x);
		double offsetY = getOffsetY(y);
		scaleActu.set(s);

		translater.setDuration(time);
		translater.setByX(node.getTranslateX() * diff + offsetX * (1 + diff));
		translater.setByY(node.getTranslateY() * diff + offsetY * (1 + diff));

		scaler.setDuration(time);
		scaler.setFromX(node.getScaleX());
		scaler.setFromY(node.getScaleY());
		scaler.setToX(s);
		scaler.setToY(s);

		cam.playFromStart();
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
