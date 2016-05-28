/*
 * 
 * 
 * 
 */
package Serializable;

import java.io.Serializable;

/**
 * Duo.java
 * 
 * @param <F>
 * @param <S>
 */
public class Duo<F, S> implements Serializable {

	private static final long serialVersionUID = 7407244569731176999L;
	
	public F first;
	public S second;

	public Duo(F first, S second) {
		this.first = first;
		this.second = second;
	}

}
