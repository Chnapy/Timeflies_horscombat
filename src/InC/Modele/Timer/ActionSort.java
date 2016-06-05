/*
 * 
 * 
 * 
 */
package InC.Modele.Timer;

import InC.Modele.Donnees.SortActif;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * ActionSort.java
 *
 */
public class ActionSort {

	public final SimpleIntegerProperty tempsLance;
	public final SortActif sort;
	public final Position source;
	public final ArrayList<Position> dest;

	public ActionSort(int tempsLance, SortActif sort, Position source, ArrayList<Position> dest) {
		this.tempsLance = new SimpleIntegerProperty(tempsLance);
		this.sort = sort;
		this.source = source;
		this.dest = dest;
	}
}
