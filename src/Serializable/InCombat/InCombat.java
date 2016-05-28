/*
 * 
 * 
 * 
 */

package Serializable.InCombat;

import java.io.Serializable;

/**
 * InCombat
 * Interface
 */
public class InCombat implements Serializable {

	private static final long serialVersionUID = 7772423113499906950L;
	
	public final long sendTime;
	
	public InCombat() {
		this(System.currentTimeMillis());
	}
	
	public InCombat(long sendTime) {
		this.sendTime = sendTime;
	}

}
