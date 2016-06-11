/*
 * 
 * 
 * 
 */
package InC.Modele.Donnees;

import InC.Modele.Binding.EntiteBinding;
import Main.Modele.Modele;
import Main.Modele.TextManager;
import Serializable.InCombat.CaracteristiquePhysique;
import Serializable.InCombat.Orientation;
import Serializable.InCombat.donnee.InEntitePassive;
import Serializable.InCombat.donnee.InSortPassif;
import Serializable.Position;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 * EntitePassive.java
 *
 */
public class EntitePassive {

	public final long idEntite;
	public final int idClasse;
	public final long idJoueur;
	public final SimpleStringProperty nomClasse;
	public final int niveau;
	public final CaracteristiquesBindable caracs;
	public final SimpleMapProperty<Integer, SortPassif> sortsP;
	public final SimpleListProperty<Envoutement> envoutements;
	public final Equipe equipe;
	protected EntiteBinding binding;
	public final SimpleIntegerProperty ordreJeu;

	public EntitePassive(InEntitePassive ent, Equipe equipe) {
		this(ent.id, ent.idClasse, ent.idJoueur, ent.niveau,
				ent.position, ent.orientation, ent.caracs, ent.listSP, equipe);
	}

	public EntitePassive(long idEntite, int idClasse, long idJoueur,
			int niveau, Position position, Orientation orientation,
			CaracteristiquePhysique caracs, ArrayList<InSortPassif> inSP, Equipe equipe) {
		this.idEntite = idEntite;
		this.idClasse = idClasse;
		this.idJoueur = idJoueur;
		this.nomClasse = TextManager.getEntiteName(idClasse);
		this.niveau = niveau;
		this.caracs = new CaracteristiquesBindable(caracs);
		this.sortsP = new SimpleMapProperty(FXCollections.observableHashMap());
		inSP.forEach((sp) -> sortsP.putIfAbsent(sp.idClasseSort, new SortPassif(sp)));
		this.envoutements = new SimpleListProperty(FXCollections.observableArrayList());
		this.equipe = equipe;
		this.binding = new EntiteBinding(position, orientation);
		this.ordreJeu = new SimpleIntegerProperty();
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

	public boolean isAlive() {
		return binding.alive.get();
	}

}
