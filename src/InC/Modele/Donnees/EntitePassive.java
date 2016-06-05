/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.Binding.EntiteBinding;
import Main.Modele.Modele;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.donnee.InEntite;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.Position;
import java.util.ArrayList;

/**
 * EntitePassive.java
 * 
 */
public class EntitePassive {
	
	public final long idEntite;
	public final int idClasse;
	public final long idJoueur;
	public final String nomDonne;
	public final String nomClasse;
	public final int niveau;
	public final CaracteristiquesBindable caracs;
	public final ArrayList<SortPassif> sortsP;
	public final ArrayList<Envoutement> envoutements;
	public final Equipe equipe;
	protected EntiteBinding binding;
	
	public EntitePassive(InEntite ent, Equipe equipe) {
		this(ent.id, ent.idClasse, ent.idJoueur, ent.nomDonne, ent.nomClasse, ent.niveau, 
				ent.position, ent.orientation, ent.caracs, ent.listSP, equipe);
	}

	public EntitePassive(long idEntite, int idClasse, long idJoueur, String nomDonne, 
			String nomClasse, int niveau, Position position, Orientation orientation, 
			CaracteristiquePhysique caracs, ArrayList<InSortPassif> inSP, Equipe equipe) {
		this.idEntite = idEntite;
		this.idClasse = idClasse;
		this.idJoueur = idJoueur;
		this.nomDonne = nomDonne;
		this.nomClasse = nomClasse;
		this.niveau = niveau;
		this.caracs = new CaracteristiquesBindable(caracs);
		this.sortsP = new ArrayList();
		inSP.forEach((sp) -> sortsP.add(new SortPassif(sp)));
		this.envoutements = new ArrayList();
		this.equipe = equipe;
		this.binding = new EntiteBinding(position, orientation);
	}
	
	public boolean isMine() {
		return Modele.infosCompte.idjoueur == idJoueur;
	}
	
	public boolean memeEquipe(EntitePassive e) {
		return e.equipe.equals(equipe);
	}

	public EntiteBinding getBinding() {
		return binding;
	}

}
