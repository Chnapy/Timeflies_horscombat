/*
 * 
 * 
 * 
 */
package InC.Modele.Timer;

import InC.Controleur.InCControleur;
import InC.Modele.Donnees.SortActif;
import InC.Vue.HUD.Module.PileBox;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

/**
 * PileAction.java
 *
 */
public class PileAction extends SimpleListProperty<ActionSort> {

	private final TourTimer timer;

	public PileAction(InCControleur controleur) {
		super(FXCollections.observableArrayList());
		this.timer = controleur.timer;
		addListener((ListChangeListener.Change<? extends ActionSort> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach((as) -> {
						PileBox.Action a = controleur.getEcran().hud.pileA.addSort(as);
						a.setOnAction((e) -> removeSort(as));
					});
				} else if (change.wasRemoved()) {
					change.getRemoved().forEach((as) -> {
						controleur.getEcran().hud.pileA.removeSort(as.tempsLance);
					});
				}
			}
		});
	}

	public void addSort(int tempsLance, SortActif sort, Position source, ArrayList<Position> dest) {
//		System.out.println(tempsLance);
		if (!isEmpty()) {
			ActionSort derniereAction = get(size() - 1);
			if (derniereAction.tempsLance.get()
					+ derniereAction.sort.tempsAction.get() > tempsLance) {
				tempsLance = derniereAction.tempsLance.get()
						+ derniereAction.sort.tempsAction.get();
			}
		}
//		System.out.println(tempsLance);
		add(new ActionSort(tempsLance, sort, source, dest));
	}

	public void removeSort(ActionSort action) {
		ActionSort as, ps;
		int tp;
		for (int i = indexOf(action) + 1; i < size(); i++) {
			ps = get(i - 1);
			as = get(i);

			if (as.tempsLance.get() <= timer.getTempsParcouru()) {
				return;
			}

			tp = timer.getTempsParcouru();
			if (tp > ps.tempsLance.get()) {
				as.tempsLance.set(tp);
			} else {
				as.tempsLance.set(ps.tempsLance.get());
			}
		}
		remove(action);
	}

}
