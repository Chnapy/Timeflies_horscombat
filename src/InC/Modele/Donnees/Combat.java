/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import Main.Modele.Modele;
import Serializable.InCombat.ChargementCombat;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Combat.java
 *
 */
public class Combat {

	public final ArrayList<Equipe> equipes;
	public final HashMap<Long, EntitePassive> entites;
	public final SimpleIntegerProperty tourActu;
	public final SimpleIntegerProperty tourGlobalActu;

	public Equipe monEquipe;

	public Combat(ChargementCombat pack) {
		this.equipes = new ArrayList();
		this.entites = new HashMap();
		this.monEquipe = null;
		pack.equipes.forEach((eq) -> {
			Equipe equipe = new Equipe(eq);
			equipes.add(equipe);
			eq.listInEntites.forEach((e) -> {
				if (e.listSA != null) {
					entites.put(e.id, new EntiteActive(e, equipe,
							Modele.infosCompte.idjoueur == e.idJoueur));
				} else {
					entites.put(e.id, new EntitePassive(e, equipe));
				}
				if (monEquipe == null && e.idJoueur == Modele.infosCompte.idjoueur) {
					monEquipe = equipe;
					System.out.println(equipe.numero);
				}
			});
		});
		tourActu = new SimpleIntegerProperty(-1);
		tourGlobalActu = new SimpleIntegerProperty(-1);
	}
	
	public boolean dansMonEquipe(EntitePassive e) {
		return e.equipe.equals(monEquipe);
	}

}
