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
	private ValeurCarac<IntegerProperty> ts;
	private long firstTime;
	private long newT;

	public int getTempsParcouru() {
		return ta.second.subtract(ta.first).intValue();
	}

	@Override
	public void handle(long l) {
		newT = (l - firstTime) / 1000000;
		if (newT < ta.second.get()) {
			ta.first.set(ta.second.get() - (int) newT);
		} else {
			ta.first.set(0);
			if (newT < ta.second.get() + ts.second.get()) {
				ts.first.set(ts.second.get() + ta.second.get() - (int) newT);
			} else {
				ts.first.set(0);
				stop();
			}
		}
	}

	public void start(ValeurCarac<IntegerProperty> ta, ValeurCarac<IntegerProperty> ts) {
		this.ta = ta;
		this.ts = ts;
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
