/*
 * 
 * 
 * 
 */
package Vue.Accueil.Content;

import Serializable.Personnages.HCPersonnage;
import Serializable.Personnages.Sort.HCSort;
import Serializable.Personnages.Sort.HCSortActif;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * ChoixPersos.java
 *
 */
public abstract class ChoixPersos extends Content {

	protected final FlowPane persosPane;
	protected final FlowPane saPane;
	protected final FlowPane spPane;
	protected final GridPane gp;
	protected final StringProperty[] caracValues;
	protected final StringProperty sortDescrip;
	public final ArrayList<ButPerso> listPersos;

	protected final Text title;
	protected final VBox top;
	protected final HBox bottom;

	public ChoixPersos(String sTitle) {

		title = new Text(sTitle);
		title.setFont(Font.font(40));

		top = new VBox(title);
		top.setAlignment(Pos.CENTER);
		top.setId("top");
		setTop(top);

		caracValues = new StringProperty[8];

		Text tLeft = new Text("Caractéristiques");
		tLeft.setFont(Font.font(16));
		gp = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		gp.getColumnConstraints().addAll(column1, column2);
		gp.setPrefWidth(200);
		gp.setAlignment(Pos.CENTER_RIGHT);
		gp.setHgap(20);
		gp.setVgap(10);
		gp.setPadding(new Insets(5));
		VBox left = new VBox(tLeft, gp);
		left.setId("left");
		left.setAlignment(Pos.TOP_CENTER);
		setLeft(left);

		Text tPersos = new Text("Personnages");
		tPersos.setFont(Font.font(16));
		persosPane = new FlowPane();
		persosPane.setAlignment(Pos.CENTER);
		persosPane.setPadding(new Insets(5));
		persosPane.setHgap(5);
		persosPane.setVgap(5);
		persosPane.setPrefWidth(500);
		VBox center = new VBox(tPersos, persosPane);
		center.setId("center");
		center.setAlignment(Pos.TOP_CENTER);
		setCenter(center);

		listPersos = new ArrayList<ButPerso>();

		Text tSa = new Text("Sorts actifs");
		tSa.setFont(Font.font(16));
		saPane = new FlowPane();
		saPane.setPrefSize(150, 2000);
		saPane.setAlignment(Pos.TOP_CENTER);
		saPane.setPadding(new Insets(5));
		saPane.setHgap(5);
		saPane.setVgap(5);
		saPane.setId("sa");
		VBox vSa = new VBox(tSa, saPane);
		vSa.setAlignment(Pos.TOP_CENTER);
		Text tSp = new Text("Sorts passifs");
		tSp.setFont(Font.font(16));
		spPane = new FlowPane();
		spPane.setPrefSize(150, 2000);
		spPane.setAlignment(Pos.TOP_CENTER);
		spPane.setPadding(new Insets(5));
		spPane.setHgap(5);
		spPane.setVgap(5);
		spPane.setId("sp");
		VBox vSp = new VBox(tSp, spPane);
		vSp.setAlignment(Pos.TOP_CENTER);
		HBox rightTop = new HBox(vSa, vSp);
		rightTop.setFillHeight(true);
		Text sortDescription = new Text();
		sortDescrip = new SimpleStringProperty();
		sortDescription.textProperty().bind(sortDescrip);
		VBox right = new VBox(rightTop, sortDescription);
		right.setPrefWidth(300);
		right.setId("right");
		setRight(right);

		bottom = new HBox();
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(20);
		bottom.setId("bottom");
		setBottom(bottom);
	}

	protected final void setGpBinding() {
		for (Node node : gp.getChildren()) {
			if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) < caracValues.length) {
				caracValues[GridPane.getRowIndex(node)] = new SimpleStringProperty("-");
				if (node instanceof Labeled) {
					((Labeled) node).textProperty().bind(caracValues[GridPane.getRowIndex(node)]);
				}
				GridPane.setHalignment(node, HPos.RIGHT);
			}
		}
	}

	public abstract void actuBtn();

	protected void setPersos(HCPersonnage[] persos) {
		for (HCPersonnage perso : persos) {
			addPerso(perso);
		}
	}

	public void addPerso(HCPersonnage perso) {
		ButPerso btn = new ButPerso(perso);
		listPersos.add(btn);
		persosPane.getChildren().add(btn);
	}

	public ArrayList<HCPersonnage> getPersos() {
		ArrayList<HCPersonnage> persos = new ArrayList();
		for (ButPerso btn : listPersos) {
			if (btn.isSelected()) {
				persos.add(btn.perso);
			}
		}
		return persos;
	}

	protected void applyCaracs(HCPersonnage perso) {
		caracValues[0].set(perso.nom);
		caracValues[1].set(perso.classe);
		caracValues[2].set(perso.niveauS + "");
		caracValues[3].set(perso.vitalite + "");
		caracValues[4].set(perso.tempsA + "");
		caracValues[5].set(perso.tempsS + "");
		caracValues[6].set(perso.vitesse + "");
		caracValues[7].set(perso.fatigue + "");
	}

	public class ButPerso extends ToggleButton {

		final HCPersonnage perso;
		ButSort[] sortsActifs;
		ButSort[] sortsPassifs;

		public ButPerso(HCPersonnage perso) {
			super(perso.nom);
			this.perso = perso;
			sortsActifs = null;
			sortsPassifs = null;
			setPrefSize(100, 100);
			setOnAction((e) -> actuBtn());
			setOnMouseEntered((e) -> {
				sortDescrip.set("");
				applyCaracs(perso);
				applySortsActifs();
				applySortsPassifs();
			});
		}

		private void applySortsActifs() {
			saPane.getChildren().clear();
			if (sortsActifs == null) {
				sortsActifs = new ButSortActif[perso.sortsActifs.length];
				for (int i = 0; i < sortsActifs.length; i++) {
					sortsActifs[i] = new ButSortActif(perso.sortsActifs[i]);
				}
			}
			saPane.getChildren().addAll(sortsActifs);
		}

		private void applySortsPassifs() {
			spPane.getChildren().clear();
			if (sortsPassifs == null) {
				sortsPassifs = new ButSort[perso.sortsPassifs.length];
				for (int i = 0; i < sortsPassifs.length; i++) {
					sortsPassifs[i] = new ButSort(perso.sortsPassifs[i]);
				}
			}
			spPane.getChildren().addAll(sortsPassifs);
		}

	}

	public class ButSort extends Button {

		final HCSort sort;

		public ButSort(HCSort sort) {
			super(sort.nom);
			this.sort = sort;
			setPrefSize(60, 60);
			setOnMouseEntered((e) -> {
				hover();
			});
		}

		protected void hover() {
			sortDescrip.set(sort.nom + "\n"
					+ "N." + sort.niveau.niveau + " XP." + sort.niveau.xp + "\n"
					+ sort.description
			);
		}

	}

	public class ButSortActif extends ButSort {

		public ButSortActif(HCSortActif sort) {
			super(sort);
		}

		@Override
		protected void hover() {
			sortDescrip.set(sort.nom + "\n"
					+ "N. " + sort.niveau.niveau + " XP. " + sort.niveau.xp + "\n"
					+ "TA. " + ((HCSortActif) sort).tempsAction + " CD. " + ((HCSortActif) sort).cooldown + " F. " + ((HCSortActif) sort).fatigue + "\n"
					+ sort.description
			);
		}
	}
}
