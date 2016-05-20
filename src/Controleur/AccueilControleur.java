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
import Serializable.Combat.InfosCombat.LancementCombat;
import Serializable.Combat.InfosCombat.NewJoueur;
import Serializable.Combat.InfosCombat.PartieTrouvee;
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

		ecran.bAccueil.setOnAction((e) -> ecran.accueil());
		ecran.bPersos.setOnAction((e) -> ecran.persos());

		ecran.accueil.bSolo.setOnAction((e) -> lancerCombat(TypeCombat.SOLO, ecran.accueil.getPersos()));
		ecran.accueil.bEquipe.setOnAction((e) -> lancerCombat(TypeCombat.EQUIPE, ecran.accueil.getPersos()));
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
		} else if (infos instanceof LancementCombat) {
			ecran.attente.lancementCombat();
			CONTROLEUR.lancementCombat();
		}
	}

}
