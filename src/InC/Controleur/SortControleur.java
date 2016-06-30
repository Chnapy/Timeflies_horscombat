/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Modele.Donnees.EntiteActive;
import InC.Modele.Donnees.EntitePassive;
import InC.Modele.Donnees.SortActif;
import InC.Modele.Donnees.SortPassif;
import InC.Modele.Map.Map;
import InC.Modele.Timer.ActionSort;
import InC.Modele.Timer.PileAction;
import Serializable.InCombat.action.Action;
import Serializable.InCombat.action.Rotation;
import Serializable.InCombat.sort.ActionsToSort;
import Serializable.InCombat.sort.AnnulerSort;
import Serializable.InCombat.sort.DeclencherSortPassif;
import Serializable.InCombat.sort.Deplacement;
import Serializable.InCombat.sort.LancerSort;
import Serializable.InCombat.sort.ListDeplacement;
import Serializable.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * SortControleur.java
 *
 */
public class SortControleur {

	private final InCControleur controleur;
	private final TreeMap<Long, EntitePassive> entites;
	private final SimpleIntegerProperty tourActu;
	private final ArrayList<Position> zoneAction;
	private final Map map;
	private final PileAction pileAction;
	private final ObjectProperty<EntiteActive> entiteEnCours;

	public SortControleur(InCControleur controleur) {
		this.controleur = controleur;
		entites = controleur.combatActu.entites;
		tourActu = controleur.combatActu.tourActu;
		zoneAction = controleur.zoneAction;
		map = controleur.map;
		pileAction = controleur.pileAction;
		entiteEnCours = controleur.entiteEnCours;
	}

	public void ajouterSort(LancerSort pack) {
		if (pack instanceof Deplacement) {
			System.err.println("probleme : sort deplacement !");
			return;
		}
		EntiteActive ea;
		try {
			ea = (EntiteActive) entites.get(pack.idEntite);
		} catch (ClassCastException e) {
			System.err.println("Une entité passive ne peut pas lancer de sort !");
			return;
		}
		if (pack.tour != tourActu.get() || ea == null) {
			System.err.println("probleme : tour ou entite incorrecte");
			return;
		}
		SortActif sa;
		if (pack.sort != null) {
			sa = ea.sortsA.putIfAbsent(pack.idClasseSort, new SortActif(pack.sort));
		} else {
			sa = ea.sortsA.get(pack.idClasseSort);
		}
		if (sa == null) {
			System.err.println("probleme : sort incorrecte");
			return;
		}

		zoneAction.clear();
		map.getZoneAction(pack.position, sa.zoneAction.getZoneIntermediaire(), zoneAction);
		ActionSort as = new ActionSort(pack.idLancer,
				(int) (pack.beginTime - pileAction.beginTime), sa,
				pack.dureeLancer,
				pack.idEntite,
				controleur.getPositionReference(),
				(ArrayList<Position>) zoneAction.clone(), pack.actions);
		pileAction.addSort(as);
	}

	public void actionsToSort(ActionsToSort pack) {
		try {
			pileAction.getById(pack.idActionSort).actions = pack.actions;
		} catch (NullPointerException e) {
			System.err.println("ID non reconnu : " + pack.idActionSort);
		}
	}

	public void annulerSort(AnnulerSort pack) {
		pileAction.removeSort(pileAction.getById(pack.idActionSort));
	}

	public boolean ajouterDeplacement(Deplacement pack) {
		if (pack.idEntite != entiteEnCours.get().idEntite) {
			System.err.println("Une entité passive ne peut pas se déplacer !");
			return false;
		}
		ActionSort as = new ActionSort(pack.idLancer,
				(int) (pack.beginTime - pileAction.beginTime),
				entiteEnCours.get().deplacement,
				pack.dureeLancer,
				pack.idEntite,
				pack.previousPosition,
				Arrays.asList(pack.position), pack.actions);
		return pileAction.addSort(as);
	}

	public void listDeplacement(ListDeplacement pack) {
		if (pack.deplacements.isEmpty()) {
			return;
		}
		int i;
		for (i = 0; i < pack.deplacements.size(); i++) {
			if (!ajouterDeplacement(pack.deplacements.get(i))) {
				break;
			}
		}
		if (i > 0) {
			entiteEnCours.get().getBinding().positionReference.set(
					pack.deplacements.get(i - 1).position);
			for (; i > 0; i--) {
				for (Action a : pack.deplacements.get(i - 1).actions) {
					if (a instanceof Rotation) {
						entiteEnCours.get().getBinding().orientationReference.set(
								((Rotation) a).direction);
					}
				}
			}
		}
	}

	public void declencherSortPassif(DeclencherSortPassif pack) {

		EntitePassive ep = entites.get(pack.idEntite);
		if (pack.tour != tourActu.get() || ep == null) {
			System.err.println("probleme : tour ou entite incorrecte");
			return;
		}
		SortPassif sp;
		if (pack.sort != null) {
			sp = ep.sortsP.putIfAbsent(pack.idClasseSort, new SortPassif(pack.sort));
		} else {
			sp = ep.sortsP.get(pack.idClasseSort);
		}
		if (sp == null) {
			System.err.println("probleme : sort incorrecte");
			return;
		}
		for (Action a : pack.actions) {
			controleur.packetRecu(a);
		}
	}

}
