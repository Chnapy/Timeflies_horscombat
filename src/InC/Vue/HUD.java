/*
 * 
 * 
 * 
 */
package InC.Vue;

import InC.Vue.Module.BarreEnvoutements;
import InC.Vue.Module.BarreSortsActifs;
import InC.Vue.Module.BarreSortsPassifs;
import InC.Vue.Module.ChatBox;
import InC.Vue.Module.Divers;
import InC.Vue.Module.Minimap;
import InC.Vue.Module.PileBox;
import InC.Vue.Module.Timeline.Timeline;
import Main.Vue.DataVue;
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

	private static final double TIMELINE_PREFWIDTH = 200, TIMELINE_MAXWIDTH = 1000,
			CHAT_WIDTH = 300, CHAT_HEIGHT = 300,
			MINIMAP_WIDTH = 100, MINIMAP_HEIGHT = MINIMAP_WIDTH,
			BARRE_SA_HEIGHT = 50, BARRE_SP_HEIGHT = 20, BARRE_E_HEIGHT = BARRE_SP_HEIGHT,
			PILE_A_HEIGHT = 100, DIVERS_HEIGHT = 50;

	public final ChatBox chat;
	public final BarreSortsActifs barreSA;
	public final BarreSortsPassifs barreSP;
	public final BarreEnvoutements barreE;
	public final Timeline timeline;
	public final Minimap minimap;
	public final PileBox pileA;
	public final Divers divers;

	public HUD() {
		setId("hud");
		setPadding(new Insets(MARGIN));

		timeline = new Timeline();
		timeline.setMinWidth(TIMELINE_PREFWIDTH);
		timeline.setMaxWidth(TIMELINE_MAXWIDTH);
		
		divers = new Divers();
		divers.setPrefSize(TIMELINE_PREFWIDTH, DIVERS_HEIGHT);
		
		GridPane right = new GridPane();
		right.setVgap(MARGIN);
		right.setHgap(MARGIN);
		right.addColumn(0, divers, timeline);
		GridPane.setVgrow(timeline, Priority.ALWAYS);
		getChildren().add(right);
		AnchorPane.setRightAnchor(right, 0d);
		AnchorPane.setBottomAnchor(right, 0d);
		AnchorPane.setTopAnchor(right, 0d);

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
		barres.setAlignment(Pos.BOTTOM_CENTER);

		Pane spaceForTimeline = new Pane();
		spaceForTimeline.setPrefWidth(TIMELINE_PREFWIDTH);

		GridPane bottom = new GridPane();
		bottom.setHgap(MARGIN);
		bottom.addRow(0, chat, barres, spaceForTimeline);
		GridPane.setHgrow(barres, Priority.ALWAYS);
		getChildren().add(bottom);
		AnchorPane.setBottomAnchor(bottom, 0d);
		AnchorPane.setLeftAnchor(bottom, 0d);
		AnchorPane.setRightAnchor(bottom, 0d);

		minimap = new Minimap();
		minimap.setPrefSize(MINIMAP_WIDTH, MINIMAP_HEIGHT);

		pileA = new PileBox();
		pileA.setPrefHeight(PILE_A_HEIGHT);

		Pane spaceForTimeline2 = new Pane();
		spaceForTimeline2.setPrefWidth(TIMELINE_PREFWIDTH);

		GridPane top = new GridPane();
		top.setHgap(MARGIN);
		top.addRow(0, minimap, pileA, spaceForTimeline2);
		GridPane.setHgrow(pileA, Priority.ALWAYS);
		getChildren().add(top);
		AnchorPane.setTopAnchor(top, 0d);
		AnchorPane.setLeftAnchor(top, 0d);
		AnchorPane.setRightAnchor(top, 0d);
		
		debug();
	}

	private void debug() {
		barreSA.addButton(new BarreSortsActifs.ButSortActif(DataVue.getSortIcone(0), 5400, 1, 8, 12));
		barreSP.addButton(new BarreSortsPassifs.ButSortPassif(DataVue.getSortIcone(1)));
		barreE.addButton(new BarreEnvoutements.ButEnvoutement(DataVue.getSortIcone(2)));
		
		pileA.lancer(30000);
		pileA.addSort(DataVue.getSortIcone(0), 100, 1500);
		pileA.addSort(DataVue.getSortIcone(1), 5000, 3000);
		pileA.addSort(DataVue.getSortIcone(2), 8500, 5000);
		pileA.addSort(DataVue.getSortIcone(3), 28000, 1000);
	}

}
