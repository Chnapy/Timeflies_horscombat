/*
 * 
 * 
 * 
 */
package Controleur;

import Vue.Ecran;

/**
 * Controleur.java
 * 
 * @param <E>
 */
public abstract class Controleur<E extends Ecran> {
	
	protected E ecran;
	
	public Controleur(E ecran) {
		this.ecran = ecran;
	}
	
	public abstract void start();

}
