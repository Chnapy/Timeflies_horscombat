/*
 * 
 * 
 * 
 */
package Modele.Reseau;

import Modele.Modele;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * MoteurReseau.java
 *
 */
public class MoteurReseau implements Runnable {

	static final String SERVERNAME = "localhost";
	static final int SERVERPORT = 9999;
	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;
	private boolean isAlive;
	private final Scanner sc;

	public MoteurReseau() throws IOException, ClassNotFoundException {
		socket = new Socket(SERVERNAME, SERVERPORT);
		System.out.println("Connecté au serveur");
		System.out.println("Socket client: " + socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();

		in = new ObjectInputStream(socket.getInputStream());
		isAlive = true;

		sc = new Scanner(System.in);
//		Terminal terminal = new Terminal(sc);
//		EXEC.submit(terminal);

		System.out.println("Client a cree les flux");
	}

	@Override
	public void run() {
		int attente = 0;
		while (isAlive) {

			try {
				Object o = readObject();
				Platform.runLater(() -> Modele.receiveFromServer(o));
				attente = 0;
			} catch (IOException | ClassNotFoundException ex) {
				if (attente >= 10 || !isAlive) {
					break;
				} else {
					attente++;
					System.out.println("Attente du serveur " + attente + "/10");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex1) {
						Logger.getLogger(MoteurReseau.class.getName()).log(Level.SEVERE, null, ex1);
					}
				}
			}
		}

		if (!socket.isClosed()) {
			close();
		}
	}

	public void send(Object objToSend) throws IOException {
		System.out.println("ENVOI : " + objToSend);
		out.writeObject(objToSend);
		out.flush();
		System.out.println("ENVOI REUSSI");
	}

	public Object readObject() throws IOException, ClassNotFoundException {
		Object o = in.readObject();
		System.out.println("RECU : " + o);
		return o;
	}

	public void close() {
		System.out.println("Déconnection du client");
		isAlive = false;
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException ex) {
		}
	}

	public class Terminal implements Runnable {

		private final Scanner sc;

		public Terminal(Scanner sc) {
			this.sc = sc;
		}

		@Override
		public void run() {
			System.out.println("Terminal lancé");
			try {
				while (isAlive && sc.hasNextLine()) {
					commande(sc.nextLine());
				}
			} catch (IllegalStateException ex) {
			}
//			sc.close();
			System.out.println("Terminal stoppé");
		}

		public void commande(String com) {
			System.out.println("Commande reçue : " + com);
			String[] mots = com.split(" ");
			switch (mots[0]) {
				case "stop":
					close();
					break;
				case "envoi":
					if (mots.length < 2) {
						System.out.println("Mauvaise entrée");
						break;
					}
					try {
						send(mots[1]);
					} catch (IOException ex) {
						System.err.println("Envoi impossible : " + mots[1]);
						ex.printStackTrace();
					}
					break;
				default:
					System.out.println("Commande non reconnue.");
					break;
			}
		}
	}

}
