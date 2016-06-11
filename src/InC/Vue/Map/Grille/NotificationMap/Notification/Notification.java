/*
 * 
 * 
 * 
 */
package InC.Vue.Map.Grille.NotificationMap.Notification;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * Notification.java
 *
 */
public class Notification extends HBox {

	protected static final double IMG_SIZE = 16;
	protected static final Duration DJUMP = new Duration(500),
			D1 = new Duration(500),
			D2 = new Duration(3000),
			D3 = new Duration(500);

	private final TranslateTransition jumpTran;

	private final SequentialTransition sTran;

	private final ParallelTransition pTran1;
	private final FadeTransition fTran1;
	private final TranslateTransition tTran1;

	private final PauseTransition paTran;

	private final ParallelTransition pTran2;
	private final FadeTransition fTran2;
	private final TranslateTransition tTran2;
	
	protected final HBox center;
	protected final HBox right;

	public Notification(Image source) {
		getStyleClass().add("notification");
		ImageView srcView = new ImageView(source);
		srcView.setPreserveRatio(true);
		srcView.setFitWidth(IMG_SIZE);

		tTran1 = new TranslateTransition(D1);
		fTran1 = new FadeTransition(D1);
		pTran1 = new ParallelTransition(tTran1, fTran1);

		paTran = new PauseTransition(D2);

		tTran2 = new TranslateTransition(D3);
		fTran2 = new FadeTransition(D3);
		pTran2 = new ParallelTransition(tTran2, fTran2);

		sTran = new SequentialTransition(this, pTran1, paTran, pTran2);
		jumpTran = new TranslateTransition(DJUMP, this);
		
		Region space1 = new Region();
		center = new HBox();
		Region space2 = new Region();
		right = new HBox();
		
		getChildren().addAll(srcView, space1, center, space2, right);
		HBox.setHgrow(space1, Priority.ALWAYS);
		HBox.setHgrow(space2, Priority.ALWAYS);
	}

	public void start(EventHandler<ActionEvent> onEnd) {

		//First
		fTran1.setFromValue(0);
		fTran1.setToValue(1);
		tTran1.setFromY(getPrefHeight());
		tTran1.setToY(0);

		//Third
		fTran2.setFromValue(1);
		fTran2.setToValue(0);
		tTran2.setFromX(0);
		tTran2.setToX(40);

		sTran.setOnFinished(onEnd);
		sTran.play();
	}

	public void stop() {
		sTran.stop();
		jumpTran.stop();
	}

	public void jump(int from) {
		jumpTran.setFromY(-getPrefHeight() * from);
		jumpTran.setToY(-getPrefHeight() * (from + 1));
		jumpTran.playFromStart();
	}

}
