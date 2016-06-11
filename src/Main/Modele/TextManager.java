/*
 * 
 * 
 * 
 */
package Main.Modele;

import javafx.beans.property.SimpleStringProperty;

/**
 * TextManager.java
 *
 */
public class TextManager {

	public static SimpleStringProperty getEntiteName(int idClasse) {
		try {
			return XMLLoader.LIST_ENTITES.get(idClasse).get("nom");
		} catch (NullPointerException e) {
			printError("entite", idClasse);
			return new SimpleStringProperty("unknown");
		}
	}

	public static SimpleStringProperty getEntiteDesc(int idClasse) {
		try {
			return XMLLoader.LIST_ENTITES.get(idClasse).get("description");
		} catch (NullPointerException e) {
			printError("entite", idClasse);
			return new SimpleStringProperty("unknown");
		}
	}

	public static SimpleStringProperty getSortName(int idClasse) {
		try {
			return XMLLoader.LIST_SORTS.get(idClasse).get("nom");
		} catch (NullPointerException e) {
			printError("sort", idClasse);
			return new SimpleStringProperty("unknown");
		}
	}

	public static SimpleStringProperty getSortDesc(int idClasse) {
		try {
			return XMLLoader.LIST_SORTS.get(idClasse).get("description");
		} catch (NullPointerException e) {
			printError("sort", idClasse);
			return new SimpleStringProperty("unknown");
		}
	}

	public static SimpleStringProperty getInterface(int id) {
		try {
			return XMLLoader.LIST_INTERFACE.get(id);
		} catch (NullPointerException e) {
			printError("interface", id);
			return new SimpleStringProperty("unknown");
		}
	}
	
	private static void printError(String nomList, int id) {
//		System.err.println("String introuvable : " + nomList + " id : " + id);
	}

}
