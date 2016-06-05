/*
 * 
 * 
 * 
 */
package InC.Vue.HUD;

import InC.Modele.ValeurCarac;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Jauge.java
 *
 */
public class Jauge extends StackPane {

	private final Orientation orientation;
	private final Rectangle background;
	private final Rectangle foreground;

	public Jauge(Orientation orientation, String classe) {
		this(orientation);
		getStyleClass().add(classe);
	}

	public Jauge(Orientation orientation) {
		this.orientation = orientation;
		getStyleClass().add("jauge");

		background = new Rectangle();
		foreground = new Rectangle();

		background.getStyleClass().add("jauge-bg");
		foreground.getStyleClass().add("jauge-fg");

		getChildren().addAll(background, foreground);
		StackPane.setAlignment(foreground, Pos.BOTTOM_LEFT);

		background.widthProperty().bind(prefWidthProperty());
		background.heightProperty().bind(prefHeightProperty());

		if (orientation == Orientation.HORIZONTAL) {
			foreground.heightProperty().bind(prefHeightProperty());
			setPrefWidth(100);
			setPrefHeight(20);
		} else {
			foreground.widthProperty().bind(prefWidthProperty());
			setPrefWidth(20);
			setPrefHeight(100);
		}
	}

	public void bind(ValeurCarac<? extends NumberExpression> carac) {
		bind(carac.first, carac.second);
	}

	public void bind(NumberExpression actu, NumberExpression max) {
		bind(new DoubleBinding() {

			{
				super.bind(actu, max);
			}

			@Override
			protected double computeValue() {
				return Math.min(1, Math.max(0, actu.doubleValue() / max.doubleValue()));
			}
		});
	}

	public void bind(NumberExpression ov) {
		if (orientation == Orientation.HORIZONTAL) {
			foreground.widthProperty().bind(ov.multiply(background.widthProperty()));
		} else {
			foreground.heightProperty().bind(ov.multiply(background.heightProperty()));
		}
	}

//	public static class VJauge extends Jauge {
//
//		public VJauge(DoubleProperty heightP) {
////			translateYProperty().bind(heightP.multiply(-1));
////			translateXProperty().bind(widthProperty().subtract(heightProperty()).divide(2));
////			
////			setRotate(-90);
//		}
//
////		@Override
////		public void setWidth(double d1) {
////			System.out.println(d1);
////			super.setHeight(d1); //TODO
////		}
////
////		@Override
////		public void setHeight(double d1) {
////			System.out.println(d1);
////			super.setWidth(d1); //TODO
////		}
//	}
}
