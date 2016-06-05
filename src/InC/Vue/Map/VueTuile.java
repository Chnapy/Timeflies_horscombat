/*
 * 
 * 
 * 
 */
package InC.Vue.Map;

import InC.Modele.Map.Tuile;
import InC.Modele.Map.Tuile.TuileState;
import Serializable.InCombat.Orientation;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 * VueTuile
 * Interface
 * @param <S>
 */
public abstract class VueTuile<S extends Shape> extends Pane implements VueItem<VueTuile> {

	public final S poly;
	protected final CubicCurve deplacementLine;
	protected final Circle beginDep, endDep;
	protected final Ellipse cibleSort;

	public VueTuile(Tuile tuile, S poly, double begin_radius, double end_radius, 
			double cible_sort_radius) {
		init();
		getThis().getStyleClass().addAll("tuile", tuile.type.toString().toLowerCase());
//		getThis().setOnMouseEntered((e) -> System.out.println(tuile.id));

		this.poly = poly;
		poly.getStyleClass().add("polygon");

		deplacementLine = new CubicCurve();
		deplacementLine.getStyleClass().add("deplacementLine");

		beginDep = new Circle(begin_radius);
		beginDep.getStyleClass().addAll("anchor", "begin");

		endDep = new Circle(end_radius);
		endDep.getStyleClass().addAll("anchor", "end");

		deplacementLine.startXProperty().bind(beginDep.centerXProperty());
		deplacementLine.startYProperty().bind(beginDep.centerYProperty());
		deplacementLine.endXProperty().bind(endDep.centerXProperty());
		deplacementLine.endYProperty().bind(endDep.centerYProperty());
		deplacementLine.controlX1Property().bind(beginDep.centerXProperty());
		deplacementLine.controlY1Property().bind(beginDep.centerYProperty());
		deplacementLine.controlX2Property().bind(widthProperty().divide(2d));
		deplacementLine.controlY2Property().bind(heightProperty().divide(2d));
		
		cibleSort = new Ellipse();
		cibleSort.getStyleClass().addAll("cible_sort", "action");
		cibleSort.centerXProperty().bind(widthProperty().divide(2d));
		cibleSort.centerYProperty().bind(heightProperty().divide(2d));
		cibleSort.radiusXProperty().bind(widthProperty().multiply(cible_sort_radius / 2));
		cibleSort.radiusYProperty().bind(heightProperty().multiply(cible_sort_radius / 2));
		
		getChildren().addAll(poly, deplacementLine, beginDep, endDep/*, cibleSort*/);
		setPickOnBounds(false);

		setBegin(null);
		setEnd(null);
	}

	protected abstract DoubleBinding getSizeValue(Orientation o, boolean horizontal);

	protected void placerExtremite(Circle extr, Orientation o) {
		Platform.runLater(() -> {
			if (o == null) {
				extr.setCenterX(getThis().widthProperty().divide(2d).get());
				extr.setCenterY(getThis().heightProperty().divide(2d).get());
				return;
			}
			switch (o) {
				case NORD:
					extr.setCenterX(getSizeValue(o, true).get());
					extr.setCenterY(getSizeValue(o, false).get());
					break;
				case SUD:
					extr.setCenterX(getSizeValue(o, true).get());
					extr.setCenterY(getSizeValue(o, false).get());
					break;
				case OUEST:
					extr.setCenterX(getSizeValue(o, true).get());
					extr.setCenterY(getSizeValue(o, false).get());
					break;
				case EST:
					extr.setCenterX(getSizeValue(o, true).get());
					extr.setCenterY(getSizeValue(o, false).get());
					break;
			}
		});
	}

	public void changeState(TuileState state) {
		for (TuileState ts : TuileState.values()) {
			getThis().getStyleClass().remove(ts.toString().toLowerCase());
		}
		if (state != TuileState.NONE) {
			getThis().getStyleClass().add(state.toString().toLowerCase());
		}
		state(state);
	}

	protected abstract void state(TuileState state);

	public final void setBegin(Orientation o) {
		placerExtremite(beginDep, o);
	}

	public final void setEnd(Orientation o) {
		placerExtremite(endDep, o);
	}

}
