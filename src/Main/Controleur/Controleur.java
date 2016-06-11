/*
 * 
 * 
 * 
 */
package Main.Controleur;

import Main.Vue.Ecran;

/**
 * Controleur.java
 * 
 * @param <E> type de vue
 * @param <P> type de paquet reseau
 */
public abstract class Controleur<E extends Ecran, P> {
	
	protected final E ecran;
	
	public Controleur(E ecran) {
		this.ecran = ecran;
	}
	
	public abstract void start();
	
	public abstract void packetRecu(P pack);

	public E getEcran() {
		return ecran;
	}

}
