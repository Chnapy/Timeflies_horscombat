/*
 * 
 * 
 * 
 */
package InC.Vue.HUD;

import InC.Modele.ValeurCarac;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;

/**
 * JaugeCirculaire.java
 *
 */
public class JaugeCirculaire extends StackPane {

	protected final TypeText type;
	public final ProgressIndicator pInd;
	public final Label label;

	public JaugeCirculaire(TypeText type, ValeurCarac carac) {
		this(type);
		bind(carac);
	}

	public JaugeCirculaire(TypeText type, String classe) {
		this(type);
		getStyleClass().add(classe);
		pInd.setProgress(0);
	}

	public JaugeCirculaire(TypeText type) {
		getStyleClass().addAll("jauge", "jauge_circu");
		this.type = type;
		pInd = new ProgressIndicator(0);
		label = new Label();
		getChildren().add(pInd);
		pInd.prefWidthProperty().bind(prefWidthProperty());
		pInd.minHeightProperty().bind(prefWidthProperty());
		if (type == TypeText.NONE) {
			getStyleClass().add("notext");
			StackPane.setMargin(pInd, new Insets(0, 0, -18, 0));
		} else {
			getChildren().add(label);
			StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
			minWidthProperty().bind(label.textProperty().length().multiply(7));
		}
//		setStyle("-fx-border-color: blue");
//		pInd.setStyle("-fx-border-color: white");
//		label.setStyle("-fx-border-color: red");
	}

	public final void bind(ValeurCarac<? extends NumberExpression> carac) {
		bind(carac.first, carac.second);
	}

	public void bind(NumberExpression actu, NumberExpression max) {
		pInd.progressProperty().bind(new DoubleBinding() {

			{
				super.bind(actu, max);
			}

			@Override
			protected double computeValue() {
				return Math.max(actu.doubleValue() / max.doubleValue(), 0);
			}
		});
		pInd.progressProperty().addListener((ov, t, newValue) -> act(actu, max));
		act(actu, max);
	}

	public void bind(NumberExpression actu) {
		bind(actu, actu);
	}

	protected void act(NumberExpression actu, NumberExpression max) {
		Platform.runLater(() -> {
			switch (type) {
				case NONE:
					break;
				case MIN_MAX:
					if (actu instanceof IntegerProperty && max instanceof IntegerProperty) {
						label.setText(actu.intValue() + "/" + max.intValue());
					} else {
						label.setText(actu.doubleValue() + "/" + max.doubleValue());
					}
					break;
				case SIMPLE:
					if (actu instanceof IntegerProperty) {
						label.setText(actu.intValue() + "");
					} else {
						label.setText(actu.doubleValue() + "");
					}
					break;
				case POURCENTAGE:
					label.setText((int) (actu.doubleValue() / max.doubleValue() * 100) + "%");
					break;
			}
		});
	}

	public enum TypeText {
		NONE,
		SIMPLE,
		MIN_MAX,
		POURCENTAGE;
	}

	public static class JaugeCirculaireStack extends JaugeCirculaire {

		public JaugeCirculaireStack(TypeText type, ValeurCarac carac) {
			this(type);
			bind(carac);
		}

		public JaugeCirculaireStack(TypeText type, String classe) {
			this(type);
			getStyleClass().add(classe);
			pInd.setProgress(0);
		}

		public JaugeCirculaireStack(TypeText type) {
			super(type);
			getStyleClass().add("jauge_circu_stack");
			if (type != TypeText.NONE) {
				StackPane.setMargin(pInd, new Insets(0, 0, -18, 0));
				StackPane.setAlignment(label, Pos.CENTER);
			}
		}

	}

}
