/*
 * 
 * 
 * 
 */
package testclient;

import Controleur.MainControleur;
import Modele.Modele;
import Vue.Vue;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Richard
 */
public class TestClient extends Application {

	@Override
	public void start(Stage primaryStage) {

		Modele modele = new Modele();
		Vue vue = new Vue(primaryStage);

		new MainControleur(vue).start();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
