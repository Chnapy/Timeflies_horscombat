/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap;

import InC.Vue.Map.Grille.AbstractMap;
import InC.Vue.Map.Grille.NotificationMap.Notification.Notification;
import Serializable.Duo;
import Serializable.Position;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.layout.Pane;

/**
 * NotificationMap.java
 *
 */
public class NotificationMap extends Pane {

	private static final double NOTIF_WIDTH = 50, NOTIF_HEIGHT = 20;

	private final SimpleListProperty<Duo<Position, Notification>> listNotifications;

	public NotificationMap() {
		listNotifications = new SimpleListProperty(FXCollections.observableArrayList());
		listNotifications.addListener((Change<? extends Duo<Position, Notification>> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach((d) -> onNAjout(d));
				} else if (change.wasRemoved()) {
					change.getRemoved().forEach((d) -> onNRemove(d));
				}
			}
		});
	}

	private void onNAjout(Duo<Position, Notification> d) {
		getChildren().add(d.second);
		for (int i = listNotifications.indexOf(d) - 1, j = 0; i >= 0; i--, j++) {
			if (d.first.equals(listNotifications.get(i).first)) {
				listNotifications.get(i).second.jump(j);
			}
		}
		d.second.start((e) -> listNotifications.remove(d));
	}

	private void onNRemove(Duo<Position, Notification> d) {
		Platform.runLater(() -> {
			d.second.stop();
			getChildren().remove(d.second);
		});
	}

	public void notifierAction(Notification n, Position p) {
		n.setLayoutX(p.x + AbstractMap.TILE_WIDTH / 4 * 3);
		n.setLayoutY(p.y - AbstractMap.TILE_HEIGHT * 1.5);
		n.setPrefWidth(NOTIF_WIDTH);
		n.setPrefHeight(NOTIF_HEIGHT);	//Important !
		Platform.runLater(() -> {
			listNotifications.add(new Duo(p, n));
		});
	}
}
