/*
 * 
 * 
 * 
 */
package Serializable.Logs;

import java.io.Serializable;

/**
 * AskLogs.java
 *
 */
public class AskLogs implements Serializable {

	private static final long serialVersionUID = -6224981723781380795L;

	public final String pseudo;
	public final String mdp;

	public AskLogs(String pseudo, String mdp) {
		this.pseudo = pseudo;
		this.mdp = mdp;
	}

}
