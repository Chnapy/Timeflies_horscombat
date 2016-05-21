/*
 * 
 * 
 * 
 */
package Vue;

import Vue.Accueil.PagePrincipale;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Vue.java
 *
 */
public final class Vue {
	
	public static final HashMap<String, Image> PRELOADIMAGE = new HashMap();
	static {
		loadAllImages();
	}

	public final Stage primaryStage;
	public final LogVue logScene;
	public final PagePrincipale pagePrincipale;
	
	public Vue(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Test_Client");

		logScene = new LogVue();
		pagePrincipale = new PagePrincipale();
		
		primaryStage.setScene(new Scene(new Group()));
		primaryStage.getScene().getStylesheets().add("accueil.css");
		setEcran(logScene);
	}

	public void setEcran(Ecran ecran) {
		primaryStage.getScene().setRoot(ecran.root);
		primaryStage.getScene().setFill(ecran.bg);
		if (ecran.width > 0 && ecran.height > 0) {
			primaryStage.setWidth(ecran.width);
			primaryStage.setHeight(ecran.height);
		} else {
			primaryStage.sizeToScene();
		}
	}

	public void show() {
		primaryStage.show();
	}

	public void stop() {
	}
	
	private static void loadAllImages() {
		PRELOADIMAGE.put("pret", new Image("file:assets/img/icons/pret.png"));
	}

}
