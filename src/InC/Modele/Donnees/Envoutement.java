/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Envoutement.java
 *
 */
public class Envoutement extends SortPassif {

	public final SimpleIntegerProperty nbrTours;

	public Envoutement(int idClasse, String nom, String description,
			int nbrTours) {
		super(idClasse, nom, description, -1);
		this.nbrTours = new SimpleIntegerProperty(nbrTours);
	}

}
