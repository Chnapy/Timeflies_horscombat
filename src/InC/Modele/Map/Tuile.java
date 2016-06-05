/*
 * 
 * 
 * 
 */
package InC.Modele.Map;

import InC.Modele.Binding.TuileBinding;
import Serializable.HorsCombat.Map.TypeTuile;
import Serializable.InCombat.Orientation;
import Serializable.Position;

/**
 * Tuile.java
 * 
 */
public class Tuile {
	
	public final int id;
	public final TypeTuile type;
	public final Position position;
	
	public boolean visited;
	private TuileBinding binding;
	
	public Tuile(int id, TypeTuile type, int x, int y) {
		this.id = id;
		this.type = type;
		position = new Position(x, y);
	}
	
	public void setBinding(TuileBinding binding) {
		this.binding = binding;
	}

	public TuileBinding getBinding() {
		return binding;
	}
	
	public void setDeplacement(Orientation before, Orientation after) {
		binding.state.set(TuileState.DEPLACEMENT);
		binding.direction.first.set(before);
		binding.direction.second.set(after);
	}
	
	public boolean estTraversable() {
		return type == TypeTuile.SIMPLE || type == TypeTuile.ECRAN;
	}
	
	public enum TuileState {
		NONE,
		DEPLACEMENT,
		ZONE_PORTEE,
		ZONE_ACTION;
	}

}
