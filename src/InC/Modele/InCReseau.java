/*
 * 
 * 
 * 
 */
package InC.Modele;

import Main.Controleur.MainControleur;
import Main.Modele.Data;
import static Main.Modele.Modele.CLIENT;
import Serializable.InCombat.LancerSort;
import Serializable.Position;
import java.util.ArrayList;

/**
 * InCReseau.java
 *
 */
public class InCReseau {

	public static boolean sendDeplacement(long idEntite, int tour, ArrayList<Position> positions) {
		try {
			ArrayList<LancerSort> ld = new ArrayList();
			positions.forEach((p) -> ld.add(new LancerSort(Data.DEPLACEMENT_IDCLASSE, idEntite, tour, p)));
			CLIENT.send(ld);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

	public static boolean sendLancerSort(int idClasseSort, long idEntite, int tour, Position position) {
		try {
			LancerSort ls = new LancerSort(idClasseSort, idEntite, tour, position);
			CLIENT.send(ls);
			return true;
		} catch (Exception ex) {
			MainControleur.alertException(MainControleur.typeErreur.SEND, ex);
			return false;
		}
	}

}
