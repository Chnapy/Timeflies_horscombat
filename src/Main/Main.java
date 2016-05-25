/*
 * 
 * 
 * 
 */
package Main;

import Main.Controleur.MainControleur;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Richard
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		MainControleur.start(primaryStage);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
