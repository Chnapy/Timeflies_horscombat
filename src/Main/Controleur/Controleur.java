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
 * @param <E>
 */
public abstract class Controleur<E extends Ecran> {
	
	protected final E ecran;
	
	public Controleur(E ecran) {
		this.ecran = ecran;
	}
	
	public abstract void start();
	
	public abstract void packetRecu(Object pack);

	public E getEcran() {
		return ecran;
	}

}
