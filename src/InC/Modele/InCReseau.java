/*
 * 
 * 
 * 
 */
package InC.Modele;

import Main.Controleur.MainControleur;
import static Main.Modele.Modele.CLIENT;
import Serializable.InCombat.sort.Deplacement;
import Serializable.Position;

/**
 * InCReseau.java
 *
 */
public class InCReseau {

	public static boolean sendDeplacement(Deplacement deplacement) {
		try {
			CLIENT.send(deplacement);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

	public static boolean sendLancerSort(int idClasseSort, long idEntite, int tour, Position position) {
		try {
//			LancerSort ls = new LancerSort(idClasseSort, idEntite, tour, position);
//			CLIENT.send(ls);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

}
