/*
 * 
 * 
 * 
 */
package InC.Vue.HUD;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * CompteurDebutCombat.java
 * 
 */
public class CompteurDebutCombat extends BorderPane {
	
	private static final double JC_WIDTH = 200;
	
	public final JaugeCirculaire jc;
	
	public CompteurDebutCombat() {
		setMaxSize(JC_WIDTH, JC_WIDTH);
		jc = new JaugeCirculaire(JaugeCirculaire.TypeText.SIMPLE);
		jc.label.setText("10");
		jc.label.setFont(Font.font(70));
		StackPane.setAlignment(jc.label, Pos.CENTER);
		StackPane.setMargin(jc.label, new Insets(0, 0, 15, 0));
		
		setCenter(jc);
		BorderPane.setAlignment(jc, Pos.CENTER);
	}

}
