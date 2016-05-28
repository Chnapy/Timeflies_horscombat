/*
 * 
 * 
 * 
 */
package InC.Vue.Module.Timeline;

import InC.Modele.ValeurCarac;
import Main.Vue.DataVue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Timeline.java
 *
 */
public class Timeline extends VBox {

	private final ListEntite listEntite;
	private final Pane entiteEnCours;

	public Timeline() {
		setId("timeline");
		setAlignment(Pos.BOTTOM_RIGHT);

		listEntite = new ListEntite();
		listEntite.setAlignment(Pos.BOTTOM_RIGHT);
		getChildren().add(listEntite);
		
		entiteEnCours = new Pane();
		entiteEnCours.setPrefHeight(300);
		getChildren().add(entiteEnCours);

		ValeurCarac<IntegerProperty> vie = new ValeurCarac(new SimpleIntegerProperty(), new SimpleIntegerProperty());
		vie.first.set(40);
		vie.second.set(90);
		RowEntite tle = new RowEntite(DataVue.getSortIcone(0), "FF0000", "Guerrier", "Jojo", 18, vie, vie, vie, vie, vie, 180);
		listEntite.add(tle);
	}

	public static class ListEntite extends VBox {

		private void add(RowEntite tle) {
			getChildren().add(tle);
			tle.setAlignment(Pos.BOTTOM_RIGHT);
		}

	}

}
