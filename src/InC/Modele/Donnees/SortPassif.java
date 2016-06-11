/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import Main.Modele.TextManager;
import Serializable.InCombat.donnee.InSortPassif;
import javafx.beans.property.SimpleStringProperty;

/**
 * SortPassif.java
 * 
 */
public class SortPassif {
	
	public final int idClasse;
	public final SimpleStringProperty nom;
	public final SimpleStringProperty description;
	public final int niveau;

	public SortPassif(InSortPassif sp) {
		this(sp.idClasseSort, sp.niveau);
	}

	public SortPassif(int idClasse, int niveau) {
		this.idClasse = idClasse;
		this.nom = TextManager.getSortName(idClasse);
		this.description = TextManager.getSortName(idClasse);
		this.niveau = niveau;
	}

}
