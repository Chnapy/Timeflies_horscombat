/*
 * 
 * 
 * 
 */
package InC.Vue.HUD.Module;

import InC.Modele.Timer.ActionSort;
import InC.Modele.ValeurCarac;
import Main.Vue.DataVue;
import java.util.HashMap;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * PileBox.java
 *
 */
public class PileBox extends VBox {

	private static final double MIDDLE_HEIGHT = 50, PADDING = 5,
			ACTION_WIDTH = 16, SPACE = 3;

	private final HBox top;
	private final StackPane middle;
	private final Pane bottom;
	private final Pane onMiddle;
	private final Label lTemps;

	private final ProgressBar pBar;
	private final ProgressIndicator pInd;

	private IntegerProperty tempsTotal;
	private IntegerProperty tempsActu;
	private final HashMap<IntegerProperty, Action> actions;
	private int idAction;

	public PileBox() {
		setId("pileA");
		setPadding(new Insets(PADDING));

		top = new HBox();
		middle = new StackPane();
		middle.setMinHeight(MIDDLE_HEIGHT);
		middle.setPrefHeight(MIDDLE_HEIGHT);
		bottom = new AnchorPane();
		getChildren().addAll(top, middle, bottom);

		pBar = new ProgressBar();
		middle.getChildren().add(pBar);
		pBar.setRotate(180);
		pBar.setPadding(Insets.EMPTY);
		pBar.prefWidthProperty().bind(middle.widthProperty());
		pBar.prefHeightProperty().bind(middle.heightProperty());

		onMiddle = new Pane();
		middle.getChildren().add(onMiddle);

		onMiddle.getStyleClass().add("anchor");
		bottom.getStyleClass().add("anchor");

		tempsTotal = new SimpleIntegerProperty(0);
		tempsActu = new SimpleIntegerProperty(0);

		lTemps = new Label();
		StringBinding lTempsBind = new StringBinding() {
			{
				super.bind(tempsActu, tempsTotal);
			}

			@Override
			protected String computeValue() {
				return Math.floor(tempsActu.doubleValue() / 100) / 10
						+ " / " + Math.floor(tempsTotal.doubleValue() / 100) / 10;
			}
		};
		lTemps.textProperty().bind(lTempsBind);
		middle.getChildren().add(lTemps);

		DoubleBinding pIndBind = new DoubleBinding() {

			{
				super.bind(tempsActu);
			}

			@Override
			protected double computeValue() {
				return tempsActu.doubleValue() % 1000 / 1000;
			}
		};
		pInd = new ProgressIndicator(0);
		pInd.progressProperty().bind(pIndBind);
		pInd.widthProperty().addListener((obs, d, d1) -> {
			if (d.doubleValue() > 0) {
				pInd.setClip(new Rectangle(d1.doubleValue(), d1.doubleValue()));
			}
		});
		bottom.getChildren().add(pInd);
		AnchorPane.setRightAnchor(pInd, SPACE - 10);
		AnchorPane.setTopAnchor(pInd, SPACE);

		actions = new HashMap();
		idAction = 0;

	}

	public Action addSort(ActionSort as) {
		IntegerProperty momentLancer = as.tempsLance, duree = as.sort.tempsAction;

		DoubleBinding pourcentDebut = momentLancer.multiply(1d).divide(tempsTotal);
		DoubleBinding pourcentFin = momentLancer.multiply(1d).add(duree).divide(tempsTotal);

		Action a = new Action(DataVue.getSortIcone(as.sort.idClasse), idAction);
		idAction++;
		actions.put(momentLancer, a);

		onMiddle.getChildren().add(a.rect);
		a.rect.widthProperty().bind(pBar.widthProperty()
				.multiply(pourcentFin.subtract(pourcentDebut)));
		a.rect.translateXProperty().bind(pBar.widthProperty().multiply(pourcentDebut));

		bottom.getChildren().add(a);
		a.translateXProperty().bind(pBar.widthProperty().multiply(pourcentDebut));

		return a;
	}

	public void removeSort(IntegerProperty momentLancer) {
		Action rm = actions.remove(momentLancer);
		onMiddle.getChildren().remove(rm.rect);
		bottom.getChildren().remove(rm);
	}

	public final void lancer(ValeurCarac<IntegerProperty> ta) {
		pBar.progressProperty().bind(ta.first.divide(ta.second.multiply(1d)));
		tempsActu.bind(ta.first);
		tempsTotal.bind(ta.second);
	}

	public static class Action extends Button {

		private static final String[] ACTION_COLOR_CODE = {
			"16BAFB",
			"EE4C20",
			"F5E321",
			"6605CA",
			"62B701",
			"34120B",
			"058A85",
			"13B317",
			"D3559B",
			"6C2942",};

		public final Rectangle rect;

		public Action(Image image, int idAction) {
			ImageView img = new ImageView(image);
			img.setFitWidth(ACTION_WIDTH);
			img.setPreserveRatio(true);
			setWidth(ACTION_WIDTH);
			setGraphic(img);
			setPadding(Insets.EMPTY);
			rect = new Rectangle(0, MIDDLE_HEIGHT);
			rect.setFill(Color.valueOf(ACTION_COLOR_CODE[idAction % ACTION_COLOR_CODE.length] + "CC"));
		}
	}

}
