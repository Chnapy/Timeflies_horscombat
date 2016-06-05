/*
 * 
 * 
 * 
 */
package Main.Vue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Vue.java
 *
 */
public class Vue {

	public static final HashMap<String, Image> PRELOADIMAGE = new HashMap();

	private static Alert alert;
	private static Alert exAlert;
	public static Stage PRIMARYSTAGE;
	private static StringWriter alertSW;
	private static PrintWriter alertPW;
	private static TextArea alertTA;

	public static void init(Stage primaryStage) {
		Vue.PRIMARYSTAGE = primaryStage;

		loadAllImages();

//		primaryStage.setResizable(false);
		primaryStage.setTitle("Test_Client");

		primaryStage.setScene(new Scene(new Group()));
		primaryStage.getScene().getStylesheets().add("style.css");

		alert = new Alert(AlertType.NONE);
		initExAlert();
	}

	private static void initExAlert() {
		exAlert = new Alert(AlertType.ERROR);
		exAlert.setTitle("Erreur - Exception");

		alertSW = new StringWriter();
		alertPW = new PrintWriter(alertSW);

		Label label = new Label("Détails de l'exception :");

		alertTA = new TextArea();
		alertTA.setEditable(false);
		alertTA.setWrapText(true);

		alertTA.setMaxWidth(Double.MAX_VALUE);
		alertTA.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(alertTA, Priority.ALWAYS);
		GridPane.setHgrow(alertTA, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(alertTA, 0, 1);

		exAlert.getDialogPane().setExpandableContent(expContent);
	}

	public static void setEcran(Ecran ecran) {
		PRIMARYSTAGE.getScene().setRoot(ecran.root);
		PRIMARYSTAGE.getScene().setFill(ecran.bg);
		if (ecran.width > 0 && ecran.height > 0) {
			PRIMARYSTAGE.setWidth(ecran.width);
			PRIMARYSTAGE.setHeight(ecran.height);
		} else {
			PRIMARYSTAGE.sizeToScene();
		}
	}

	public static void show() {
		PRIMARYSTAGE.show();
	}

	public static void exception(String message, Exception ex) {
		ex.printStackTrace(alertPW);

		exAlert.setHeaderText(message);
		alertTA.setText(alertSW.toString());
		exAlert.show();
	}

	public static void erreur(String content) {
		alert(AlertType.ERROR, "Erreur", content);
	}

	public static void info(String content) {
		alert(AlertType.INFORMATION, "Info", content);
	}

	public static boolean ask(String title, String content) {
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);

		return alert.showAndWait().get() == ButtonType.OK;
	}

	public static void alert(AlertType type, String title, String content) {
		alert.setAlertType(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.show();
	}

	public static final void turnOffPickOnBoundsFor(Node n) {
		try {
			n.setPickOnBounds(false);
		} catch (RuntimeException re) {
		}
		n.setOnMouseClicked((e) -> System.out.println(n));
		if (n instanceof Parent) {
			for (Node c : ((Parent) n).getChildrenUnmodifiable()) {
				turnOffPickOnBoundsFor(c);
			}
		}
	}

	public static void loadAllImages() {
		PRELOADIMAGE.put("pret", new Image("file:assets/img/icons/pret.png"));
	}

}
