/*
 * 
 * 
 * 
 */
package InC.Modele.Binding;

import InC.Modele.Donnees.SortActif;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * SortBinding.java
 * 
 */
public class SortBinding {
	
	private final SimpleObjectProperty<SortActif> sortActu;
	private final SimpleObjectProperty<Position> source;
	private final SimpleListProperty<Position> cibles;
	private final SimpleBooleanProperty estLance;

	public SortBinding() {
		this.sortActu = new SimpleObjectProperty();
		this.estLance = new SimpleBooleanProperty();
		this.source = new SimpleObjectProperty();
		this.cibles = new SimpleListProperty();
	}
	
	public SortActif getSortActu() {
		return sortActu.get();
	}
	
	public void setSortActu(SortActif sa) {
		sortActu.set(sa);
	}
	
	public void lancerSort(Position src, ArrayList<Position> dest) {
		source.set(src);
		cibles.setAll(dest);
		estLance.set(true);
	}
	
	public void stopSort() {
		estLance.set(false);
		source.set(null);
		cibles.clear();
		sortActu.set(null);
	}

}
