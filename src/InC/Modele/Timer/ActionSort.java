/*
 * 
 * 
 * 
 */
package InC.Modele.Timer;

import InC.Modele.Donnees.SortActif;
import Serializable.InCombat.action.Action;
import Serializable.Position;
import java.util.List;
import java.util.concurrent.Future;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * ActionSort.java
 *
 */
public class ActionSort implements Comparable<ActionSort> {

	public final int id;
	public SimpleIntegerProperty tempsLance;
	public SortActif sort;
	public SimpleIntegerProperty duree;
	public long idLanceur;
	public Position source;
	public List<Position> dest;
	public Action[] actions;
	public boolean enCours;
	public Runnable onStart;
	public Future future;

	public ActionSort(int id, int tempsLance, SortActif sort, int duree, long idLanceur, Position source, List<Position> dest, Action... actions) {
		this.id = id;
		this.tempsLance = new SimpleIntegerProperty(tempsLance);
		this.sort = sort;
		this.duree = new SimpleIntegerProperty(duree);
		this.idLanceur = idLanceur;
		this.source = source;
		this.dest = dest;
		this.actions = actions;
		this.enCours = false;
		this.onStart = null;
		this.future = null;
	}

	@Override
	public int compareTo(ActionSort t) {
		if(t.tempsLance.greaterThan(tempsLance).get()) {
			return -1;
		} else if(t.tempsLance.lessThan(tempsLance).get()) {
			return 1;
		}
		return 0;
	}
}
