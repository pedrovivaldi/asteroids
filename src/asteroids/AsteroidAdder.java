package asteroids;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PedroHenrique
 */
public class AsteroidAdder extends Thread {

    private int delay;

    public AsteroidAdder(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        Asteroid d = null;
        for (;;) {
            Space.getInstance().addAsteroid(new Asteroid());

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(AsteroidAdder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
