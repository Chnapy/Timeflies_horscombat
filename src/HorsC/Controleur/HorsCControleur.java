/*
 * 
 * 
 * 
 */
package HorsC.Controleur;

import Main.Controleur.Controleur;
import HorsC.Modele.HorsCReseau;
import HorsC.Vue.HorsCVue;
import Main.Controleur.MainControleur;
import Main.Modele.Modele;
import Serializable.HorsCombat.HCPersonnage;
import Serializable.HorsCombat.GestionPersos;
import Serializable.HorsCombat.GestionPersos.AllClassePerso;
import Serializable.HorsCombat.GestionPersos.IdCreaPerso;
import Serializable.HorsCombat.HorsCombat.TypeCombat;
import Serializable.HorsCombat.SalonCombat;
import Serializable.HorsCombat.SalonCombat.EstPret;
import Serializable.HorsCombat.SalonCombat.NewJoueur;
import Serializable.HorsCombat.SalonCombat.PartieTrouvee;
import Serializable.HorsCombat.SalonCombat.RmJoueur;
import Serializable.InCombat.DebutCombat;
import java.util.ArrayList;

/**
 * HorsCControleur.java
 *
 */
public class HorsCControleur extends Controleur<HorsCVue> {

	public HorsCControleur() {
		super(new HorsCVue());
	}

	@Override
	public void start() {
		ecran.setData(Modele.infosCompte, Modele.persos);

		ecran.bAccueil.setOnAction((e) -> goToAccueil());
		ecran.accueil.addPersoBtn.setOnAction((e) -> switchToAddPerso());
		ecran.accueil.bSolo.setOnAction((e) -> entrerSalon(TypeCombat.SOLO, ecran.accueil.getPersos()));
		ecran.accueil.bEquipeCps.setOnAction((e) -> entrerSalon(TypeCombat.EQUIPE_CPS, ecran.accueil.getPersos()));
		ecran.accueil.bEquipe.setOnAction((e) -> entrerSalon(TypeCombat.EQUIPE, ecran.accueil.getPersos()));

		ecran.creaPerso.submit.setOnAction((e) -> creaPerso(ecran.creaPerso.getPersos().get(0),
				ecran.creaPerso.tf.getText().trim()));

		ecran.attente.readyBut.setOnAction((e) -> pret(ecran.attente.readyBut.isSelected()));
	}
	
	private void goToAccueil() {
		if(ecran.getContent().toAccueil()) {
			if(ecran.getContent() == ecran.attente) {
				HorsCReseau.sendQuitSalon(Modele.infosCompte.idjoueur);
			}
			ecran.setContent(ecran.accueil);
		}
	}

	private void creaPerso(HCPersonnage perso, String nomDonne) {
		boolean envoi = HorsCReseau.sendCreaPerso((int) perso.idClasse, nomDonne);
		if (!envoi) {
			return;
		}

		perso.nom = nomDonne;
		ecran.accueil.addPerso(perso);

		ecran.setContent(ecran.accueil);
	}

	private void switchToAddPerso() {
		if (ecran.creaPerso.listPersos.isEmpty()) {
			boolean envoi = HorsCReseau.sendGetAllClassePerso();
			if (!envoi) {
				return;
			}
		}

		ecran.setContent(ecran.creaPerso);
	}

	private void pret(boolean pret) {
		boolean envoi = HorsCReseau.sendPret(pret);
		if (!envoi) {
			return;
		}

		ecran.attente.estPret(Modele.infosCompte.idjoueur, pret);
	}

	private void entrerSalon(TypeCombat type, ArrayList<HCPersonnage> persos) {
		System.out.println(type + " " + persos.size());
		boolean envoi = HorsCReseau.sendReqSalon(type, persos);
		if (!envoi) {
			return;
		}
		
		ecran.attente.newAttente(type);
		ecran.setContent(ecran.attente);
	}

	private void gestionPersos(GestionPersos pack) {
		if (pack instanceof AllClassePerso) {
			ecran.creaPerso.loadData(((AllClassePerso) pack).allPersos);
		} else if (pack instanceof IdCreaPerso) {
			ecran.accueil.setIdNewPerso(((IdCreaPerso) pack).idEntite);
		}
	}

	private void salonCombat(SalonCombat pack) {
		if (pack instanceof PartieTrouvee) {
			ecran.attente.partieTrouvee(((PartieTrouvee) pack).donneesJoueur, ((PartieTrouvee) pack).getNbrPersos(), ((PartieTrouvee) pack).mapS);
		} else if (pack instanceof NewJoueur) {
			ecran.attente.newJoueur(((NewJoueur) pack).dj);
		} else if (pack instanceof RmJoueur) {
			ecran.attente.rmJoueur(((RmJoueur) pack).idJoueur);
		} else if (pack instanceof EstPret) {
			ecran.attente.estPret(((EstPret) pack).idJoueur, ((EstPret) pack).pret);
		}
	}

	@Override
	public void packetRecu(Object pack) {
		if (pack instanceof GestionPersos) {
			gestionPersos((GestionPersos) pack);
		} else if (pack instanceof SalonCombat) {
			salonCombat((SalonCombat) pack);
		} else if (pack instanceof DebutCombat) {
			MainControleur.lancementCombat((DebutCombat) pack);
		}
	}

}
