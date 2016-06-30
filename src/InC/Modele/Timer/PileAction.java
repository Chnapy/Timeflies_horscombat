/*
 * 
 * 
 * 
 */
package InC.Modele.Timer;

import InC.Controleur.InCControleur;
import InC.Vue.HUD.Module.PileBox;
import static Main.Controleur.MainControleur.EXEC;
import Main.Modele.Data;
import Serializable.InCombat.action.Action;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

/**
 * PileAction.java
 *
 */
public class PileAction extends SimpleListProperty<ActionSort> {

	private static int ID = -1;

	private final InCControleur controleur;
	private final TourTimer timer;
	public long beginTime;

	public PileAction(InCControleur controleur) {
		super(FXCollections.observableArrayList());
		this.controleur = controleur;
		this.timer = controleur.timer;
		addListener((ListChangeListener.Change<? extends ActionSort> change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach((as) -> {
						PileBox.Action a = controleur.getEcran().hud.pileA.addSort(as);
						a.setOnAction((e) -> removeSort(as));
					});
				} else if (change.wasRemoved()) {
					change.getRemoved().forEach((as) -> {
						try {
							as.future.cancel(true);
						} catch (NullPointerException e) {
						}
						controleur.getEcran().hud.pileA.removeSort(as.tempsLance);
					});
				}
			}
		});
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	//Retourne true si le sort est accepté && premier à etre lancé
	public boolean addSort(ActionSort as) {
		boolean premier = true;

//		System.out.println("-----------------");
//		System.out.println("NS time: " + as.tempsLance.get() + " duree: " + as.duree.get());
		if (!isEmpty()) {
			ActionSort derniereAction = get(size() - 1);
			System.out.println("OS time: " + derniereAction.tempsLance.get() + " duree: " + derniereAction.duree.get());
			if (derniereAction.tempsLance.get()
					+ derniereAction.duree.get() >= as.tempsLance.get()) {
				as.tempsLance.set(derniereAction.tempsLance.get()
						+ derniereAction.duree.get());
				premier = false;
//				System.out.println("Premier: " + premier);
			}
		}
//		System.out.println("-----------------");

		boolean accepte = add(as);
		if (accepte) {
			as.onStart = () -> {
				if (as.enCours) {
					return;
				}
				as.enCours = true;
				Platform.runLater(() -> {
					if (as.sort.idClasse == Data.DEPLACEMENT_IDCLASSE) {
						controleur.getEcran().hud.chat.chatCombat.startDeplacement(
								as.id, as.duree.get());
					} else {
						controleur.getEcran().hud.chat.chatCombat.startSort(
								as.id, as.sort.idClasse, as.sort.nom.get(), as.duree.get());
					}
					controleur.getEcran().maps.grille.effetsMap.lancerEffet(as);
				});
				try {
					Thread.sleep(as.duree.get());
					for (Action a : as.actions) {
						controleur.actionControleur.action(a);
					}
					Platform.runLater(()
							-> controleur.getEcran().maps.grille.effetsMap.stopEffet());
				} catch (InterruptedException ex) {
					System.out.println("SORT interrompu");
					Platform.runLater(() -> {
						controleur.getEcran().hud.chat.chatCombat.annulerSort();
						controleur.getEcran().maps.grille.effetsMap.interruptEffet();
					});
				}
				as.enCours = false;
				try {
					ActionSort next = get(indexOf(as) + 1);
					if (!next.enCours) {
						next.future = EXEC.submit(next.onStart);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			};
			if (premier) {
				as.future = EXEC.submit(as.onStart);
			}
		}
		return accepte;
	}

	@Override
	public boolean add(ActionSort as) {
		if (getById(as.id) != null) {
			System.err.println("Ajout de l'ActionSort annulé : id déjà existant " + as.id);
			return false;
		}
		return super.add(as);
	}

	public void removeSort(ActionSort action) {
		ActionSort as, ps;
		int tp;
		for (int i = indexOf(action) + 1; i < size(); i++) {
			ps = get(i - 1);
			as = get(i);

			if (as.tempsLance.get() <= timer.getTempsParcouru()) {
				return;
			}

			tp = timer.getTempsParcouru();
			if (tp > ps.tempsLance.get()) {
				as.tempsLance.set(tp);
			} else {
				as.tempsLance.set(ps.tempsLance.get());
			}
		}
		remove(action);
	}

//	public Position getLastPositionIfExist(long idLanceur) {
//		ActionSort as;
//		for (int i = size() - 1; i >= 0; i--) {
//			as = get(i);
//			for (Action a : as.actions) {
//				if (a.idLanceur == idLanceur) {
//					if (a instanceof Teleportation) {
//						return ((Teleportation) a).posCible;
//					}
//				}
//			}
//		}
//		return null;
//	}
	public ActionSort getById(int id) {
		for (ActionSort as : get()) {
			if (as.id == id) {
				return as;
			}
		}
		return null;
	}

	public static int getNewID() {
		ID++;
		return ID;
	}

}
