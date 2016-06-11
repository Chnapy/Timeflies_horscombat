/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import Main.Modele.Modele;
import Serializable.InCombat.ChargementCombat;
import Serializable.InCombat.donnee.InEntiteActive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Combat.java
 *
 */
public class Combat {

	public final HashMap<Integer, Equipe> equipes;
	public final TreeMap<Long, EntitePassive> entites;
	public final SimpleIntegerProperty tourActu;
	public final SimpleIntegerProperty tourGlobalActu;

	public Equipe monEquipe;

	public Combat(ChargementCombat pack) {
		this.equipes = new HashMap();
		this.entites = new TreeMap();
		this.monEquipe = null;
		pack.equipes.forEach((eq) -> {
			Equipe equipe = new Equipe(eq);
			equipes.put(eq.numero, equipe);
			eq.listInEntites.forEach((e) -> {
				if (e instanceof InEntiteActive) {
					entites.put(e.id, new EntiteActive(((InEntiteActive) e), equipe, ((InEntiteActive) e).tempsDeplacement));
				} else {
					entites.put(e.id, new EntitePassive(e, equipe));
				}
				if (monEquipe == null && e.idJoueur == Modele.infosCompte.idjoueur) {
					monEquipe = equipe;
//					System.out.println(equipe.numero);
				}
			});
		});
		tourActu = new SimpleIntegerProperty(-1);
		tourGlobalActu = new SimpleIntegerProperty(-1);
	}

	public boolean dansMonEquipe(EntitePassive e) {
		return true;
//		return e.equipe.equals(monEquipe);
	}

}
