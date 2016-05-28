/*
 * 
 * 
 * 
 */
package InC.Controleur;

import InC.Vue.InCVue;
import Main.Controleur.Controleur;

/**
 * InCControleur.java
 * 
 */
public class InCControleur extends Controleur<InCVue> {

	public InCControleur() {
		super(new InCVue());
	}

	@Override
	public void start() {
		System.out.println("START");
	}

	@Override
	public void packetRecu(Object pack) {
		
	}

}
