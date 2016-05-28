/*
 * 
 * 
 * 
 */
package InC.Vue.Module;

import Main.Vue.DataVue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * BarreSortsActifs.java
 *
 */
public class BarreSortsActifs extends FlowPane {

	private static final double BOUTON_WIDTH = 64, BOUTON_HEIGHT = BOUTON_WIDTH,
			SPACE = 4, PADDING = SPACE;

	public BarreSortsActifs() {
		super(Orientation.HORIZONTAL);
		setId("barreSA");
		setPadding(new Insets(PADDING));
		setHgap(SPACE);
		setVgap(SPACE);
	}
	
	public void addButton(ButSortActif b) {
		getChildren().add(b);
		b.setPrefSize(BOUTON_WIDTH, BOUTON_HEIGHT);
	}

	public static class ButSortActif extends ToggleButton {

		private final ImageView lFond;
		private final Label lTempsA;
		private final Label lCooldown;
		private final Label lFatigue;
		private final Label lNiveau;

		public ButSortActif(Image fond, double tempsA, int cooldown, int fatigue, int niveau) {
			getStyleClass().add("sortBut");
			setPadding(Insets.EMPTY);
			
			BorderPane apane = new BorderPane();
			getChildren().add(apane);

			this.lFond = new ImageView(fond);
			this.lTempsA = new Label(tempsA / 1000 + "");
			this.lCooldown = new Label(cooldown + "");
			this.lFatigue = new Label(fatigue + "");
			this.lNiveau = new Label(niveau + "");
			
			lTempsA.getStyleClass().add("sortAtemps");
			lCooldown.getStyleClass().add("sortAcooldown");
			lFatigue.getStyleClass().add("sortAfatigue");
			lNiveau.getStyleClass().add("sortAniveau");

			lFond.setFitWidth(BOUTON_WIDTH);
			lFond.setPreserveRatio(true);
			setGraphic(apane);
			
			apane.getChildren().add(lFond);

			apane.setTop(lTempsA);
			BorderPane.setAlignment(lTempsA, Pos.TOP_CENTER);

			apane.setLeft(lCooldown);
			BorderPane.setAlignment(lCooldown, Pos.CENTER_LEFT);

			apane.setRight(lFatigue);
			BorderPane.setAlignment(lFatigue, Pos.CENTER_RIGHT);

			apane.setBottom(lNiveau);
			BorderPane.setAlignment(lNiveau, Pos.BOTTOM_CENTER);
			
			lFond.toBack();
		}

	}

}
