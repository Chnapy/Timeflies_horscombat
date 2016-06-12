/*
 * 
 * 
 * 
 */
package InC.Vue.HUD;

import InC.Vue.HUD.Module.Barres.BarreEnvoutements;
import InC.Vue.HUD.Module.Barres.BarreSortsActifs;
import InC.Vue.HUD.Module.Barres.BarreSortsPassifs;
import InC.Vue.HUD.Module.Chat.ChatBox;
import InC.Vue.HUD.Module.Divers;
import InC.Vue.HUD.Module.EntiteCours;
import InC.Vue.Map.Minimap.Minimap;
import InC.Vue.HUD.Module.PileBox;
import InC.Vue.HUD.Module.Timeline.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * HUD.java
 *
 */
public class HUD extends AnchorPane {

	private static final double MARGIN = 5;

	public static final double TIMELINE_PREFWIDTH = 420, TIMELINE_MAXWIDTH = 1000,
			CHAT_WIDTH = 300, CHAT_HEIGHT = 300,
			MINIMAP_WIDTH = 100, MINIMAP_HEIGHT = MINIMAP_WIDTH,
			BARRE_SA_HEIGHT = 50, BARRE_SP_HEIGHT = 20, BARRE_E_HEIGHT = BARRE_SP_HEIGHT,
			PILE_A_HEIGHT = 100, DIVERS_WIDTH = 100, DIVERS_HEIGHT = 50, ENTITECOURS_HEIGHT = 200;

	public final ChatBox chat;
	public final BarreSortsActifs barreSA;
	public final BarreSortsPassifs barreSP;
	public final BarreEnvoutements barreE;
	public final EntiteCours entiteCours;
	public final Timeline timeline;
	public final Minimap minimap;
	public final PileBox pileA;
	public final Divers divers;

	public HUD(Minimap minimap) {
		setId("hud");
		setPadding(new Insets(MARGIN));

		chat = new ChatBox();
		chat.setPrefSize(CHAT_WIDTH, CHAT_HEIGHT);

		barreSA = new BarreSortsActifs();
		barreSA.setPrefHeight(BARRE_SA_HEIGHT);

		barreSP = new BarreSortsPassifs();
		barreSP.setPrefHeight(BARRE_SP_HEIGHT);

		barreE = new BarreEnvoutements();
		barreE.setPrefHeight(BARRE_E_HEIGHT);

		VBox barres = new VBox(barreE, barreSP, barreSA);
		barres.setId("barres");
		barres.getStyleClass().add("module");
		barres.setAlignment(Pos.BOTTOM_CENTER);
		barres.setFillWidth(false);

		Pane spaceForTimeline = new Pane();

		GridPane bottom = new GridPane();
		bottom.setHgap(MARGIN);
		bottom.addRow(0, chat, barres, spaceForTimeline);
		GridPane.setHgrow(barres, Priority.ALWAYS);
		getChildren().add(bottom);
		AnchorPane.setBottomAnchor(bottom, 0d);
		AnchorPane.setLeftAnchor(bottom, 0d);
		AnchorPane.setRightAnchor(bottom, 0d);

		this.minimap = minimap;
		minimap.setPrefWidth(MINIMAP_WIDTH);

		pileA = new PileBox();
		pileA.setPrefHeight(PILE_A_HEIGHT);

		Pane spaceForTimeline2 = new Pane();

		GridPane top = new GridPane();
		top.setHgap(MARGIN);
		top.addRow(0, new Pane(minimap), pileA, spaceForTimeline2);
		GridPane.setHgrow(pileA, Priority.ALWAYS);
		getChildren().add(top);
		AnchorPane.setTopAnchor(top, 0d);
		AnchorPane.setLeftAnchor(top, 0d);
		AnchorPane.setRightAnchor(top, 0d);

		divers = new Divers();
		divers.setPrefSize(DIVERS_WIDTH, DIVERS_HEIGHT);
		spaceForTimeline2.prefWidthProperty().bind(divers.widthProperty());

		timeline = new Timeline();
		timeline.setPrefWidth(TIMELINE_PREFWIDTH);

		entiteCours = new EntiteCours();
		spaceForTimeline.prefWidthProperty().bind(entiteCours.widthProperty());
		AnchorPane pe = new AnchorPane(entiteCours);
		entiteCours.setPrefWidth(TIMELINE_PREFWIDTH);
		AnchorPane.setRightAnchor(entiteCours, 0d);
		AnchorPane.setBottomAnchor(entiteCours, 0d);

		GridPane right = new GridPane();
		right.setAlignment(Pos.BOTTOM_RIGHT);
		right.setVgap(MARGIN);
		right.setHgap(MARGIN);
		right.addColumn(0, divers, timeline, pe);
		GridPane.setFillWidth(divers, false);
		GridPane.setVgrow(timeline, Priority.ALWAYS);
		GridPane.setHalignment(divers, HPos.RIGHT);
		GridPane.setHalignment(pe, HPos.RIGHT);
		getChildren().add(right);
		AnchorPane.setRightAnchor(right, 0d);
		AnchorPane.setBottomAnchor(right, 0d);
		AnchorPane.setTopAnchor(right, 0d);

		debug();
	}

	private void debug() {
//		barreSA.addSA(DataVue.getSortIcone(0), new SimpleIntegerProperty(5400), new SimpleIntegerProperty(1), new SimpleIntegerProperty(8), 12);
//		barreSP.addButton(new BarreSortsPassifs.ButSortPassif(DataVue.getSortIcone(1)));
//		barreE.addButton(new BarreEnvoutements.ButEnvoutement(DataVue.getSortIcone(2)));

//		pileA.lancer(30000);
//		pileA.addSort(DataVue.getSortIcone(0), 100, 1500);
//		pileA.addSort(DataVue.getSortIcone(1), 5000, 3000);
//		pileA.addSort(DataVue.getSortIcone(2), 8500, 5000);
//		pileA.addSort(DataVue.getSortIcone(3), 28000, 1000);
//		ValeurCarac<IntegerProperty> vie = new ValeurCarac(new SimpleIntegerProperty(), new SimpleIntegerProperty());
//		vie.first.set(40);
//		vie.second.set(90);
//		timeline.addEntiteActive(-1, -1, "FF0000", "Jojo", 18, vie, vie, vie, vie, vie, vie);
//		timeline.addEntiteActive(0, -1, "FF0000", "Jojo", 18, vie, vie, vie, vie, vie, vie);
	}

}
