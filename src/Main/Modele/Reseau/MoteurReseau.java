/*
 * 
 * 
 * 
 */
package Main.Modele.Reseau;

import static Main.Modele.Data.SERVERNAME;
import static Main.Modele.Data.SERVERPORT;
import Main.Controleur.MainControleur;
import Main.Modele.Data;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * MoteurReseau.java
 *
 */
public class MoteurReseau implements Runnable {

	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;
	private boolean isAlive;

	public MoteurReseau() throws IOException, ClassNotFoundException {
		socket = new Socket(SERVERNAME, SERVERPORT);
		System.out.println("Connecté au serveur");
		System.out.println("Socket client: " + socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();

		in = new ObjectInputStream(socket.getInputStream());
		isAlive = true;

		System.out.println("Client a cree les flux");
	}

	@Override
	public void run() {
		int attente = 0;
		while (isAlive) {

			try {
				Object o = readObject();
				Platform.runLater(() -> MainControleur.receiveFromServer(o));
				attente = 0;
			} catch (IOException | ClassNotFoundException ex) {
				if (attente >= 10 || !isAlive) {
					break;
				} else {
					attente++;
					System.out.println("Attente du serveur " + attente + "/10");
					try {
						Thread.sleep(Data.CONNECTION_TIMEOUT / 10);
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

}
