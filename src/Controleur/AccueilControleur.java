/*
 * 
 * 
 * 
 */
package Controleur;

import static Controleur.MainControleur.CONTROLEUR;
import Modele.Modele;
import Modele.Reseau.InterfaceReseau;
import Serializable.Combat.AskCombat.TypeCombat;
import Serializable.Combat.InfosCombat;
import Serializable.Combat.InfosCombat.AllClassePerso;
import Serializable.Combat.InfosCombat.EstPret;
import Serializable.Combat.InfosCombat.IdCreaPerso;
import Serializable.Combat.InfosCombat.LancementCombat;
import Serializable.Combat.InfosCombat.NewJoueur;
import Serializable.Combat.InfosCombat.PartieTrouvee;
import Serializable.Combat.InfosCombat.RmJoueur;
import Serializable.Personnages.HCPersonnage;
import Vue.Accueil.PagePrincipale;
import java.util.ArrayList;

/**
 * AccueilControleur.java
 *
 */
public class AccueilControleur extends Controleur<PagePrincipale> {

	public AccueilControleur(PagePrincipale ecran) {
		super(ecran);
	}

	@Override
	public void start() {
		ecran.setData(Modele.infosCompte, Modele.persos, Modele.equipes);

		ecran.accueil.addPersoBtn.setOnAction((e) -> switchToAddPerso());
		ecran.accueil.bSolo.setOnAction((e) -> lancerCombat(TypeCombat.SOLO, ecran.accueil.getPersos()));
		ecran.accueil.bEquipe.setOnAction((e) -> lancerCombat(TypeCombat.EQUIPE, ecran.accueil.getPersos()));
		
		ecran.creaPerso.submit.setOnAction((e) -> creaPerso(ecran.creaPerso.getPersos().get(0), 
				ecran.creaPerso.tf.getText().trim()));

		ecran.attente.readyBut.setOnAction((e) -> pret(ecran.attente.readyBut.isSelected()));
	}
	
	private void creaPerso(HCPersonnage perso, String nomDonne) {
		boolean envoi = InterfaceReseau.sendCreaPerso((int) perso.idClasse, nomDonne);
		if (!envoi) {
			return;
		}
		
		perso.nom = nomDonne;
		ecran.accueil.addPerso(perso);
		
		ecran.setContent(ecran.accueil);
	}

	private void switchToAddPerso() {
		if (ecran.creaPerso.listPersos.isEmpty()) {
			boolean envoi = InterfaceReseau.sendGetAllClassePerso();
			if (!envoi) {
				return;
			}
		}

		ecran.setContent(ecran.creaPerso);
	}

	private void pret(boolean pret) {
		boolean envoi = InterfaceReseau.sendPret(pret);
		if (!envoi) {
			return;
		}

		ecran.attente.estPret(Modele.infosCompte.idjoueur, pret);
	}

	private void lancerCombat(TypeCombat type, ArrayList<HCPersonnage> persos) {
		boolean envoi = InterfaceReseau.sendReqCombat(type, persos);
		if (!envoi) {
			return;
		}

		ecran.attente.newAttente(type);
		ecran.setContent(ecran.attente);
	}

	public void majAttenteCombat(InfosCombat infos) {
		if (infos instanceof PartieTrouvee) {
			ecran.attente.partieTrouvee(((PartieTrouvee) infos).donneesJoueur, ((PartieTrouvee) infos).getNbrPersos(), ((PartieTrouvee) infos).nomMap);
		} else if (infos instanceof NewJoueur) {
			ecran.attente.newJoueur(((NewJoueur) infos).dj);
		} else if (infos instanceof RmJoueur) {
			ecran.attente.rmJoueur(((RmJoueur) infos).idJoueur);
		} else if (infos instanceof EstPret) {
			ecran.attente.estPret(((EstPret) infos).idJoueur, ((EstPret) infos).pret);
		} else if (infos instanceof LancementCombat) {
			ecran.attente.lancementCombat();
			CONTROLEUR.lancementCombat();
		} else if (infos instanceof AllClassePerso) {
			ecran.creaPerso.loadData(((AllClassePerso) infos).allPersos);
		} else if (infos instanceof IdCreaPerso) {
			ecran.accueil.setIdNewPerso(((IdCreaPerso)infos).idEntite);
		}
	}

}
