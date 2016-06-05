/*
 * 
 * 
 * 
 */
package InC.Modele.Timer;

import InC.Modele.ValeurCarac;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;

/**
 * TourTimer.java
 *
 */
public class TourTimer extends AnimationTimer {

	private ValeurCarac<IntegerProperty> ta;
	private long firstTime;

	public int getTempsParcouru() {
		return ta.second.subtract(ta.first).intValue();
	}

	@Override
	public void handle(long l) {
		long newT = (l - firstTime) / 1000000;
		if (newT < ta.second.get()) {
			ta.first.set(ta.second.get() - (int) newT);
		} else {
			ta.first.set(0);
			stop();
		}
	}

	public void start(ValeurCarac<IntegerProperty> ta) {
		this.ta = ta;
		firstTime = System.nanoTime();
		start();
	}

	@Override
	public void stop() {
		super.stop();
		onStop();
	}

	public void onStop() {
	}

}
