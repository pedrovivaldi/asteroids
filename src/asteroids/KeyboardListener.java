package asteroids;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                Space.getInstance().turnStationCW();
                break;
            case KeyEvent.VK_LEFT:
                Space.getInstance().turnStationCCW();
                break;
            case KeyEvent.VK_UP:
                Space.getInstance().acceleratingStation(+1);
                break;
            case KeyEvent.VK_DOWN:
                Space.getInstance().acceleratingStation(-1);
                break;
            case KeyEvent.VK_S:
                Space.getInstance().updateStation();
                break;
            case KeyEvent.VK_X:
                System.exit(0);
                break;
            case KeyEvent.VK_Q:
                Space.getInstance().removeStation();
                break;
            case KeyEvent.VK_SPACE:
                if (Space.getInstance().getStation() != null) {
                    Space.getInstance().getStation().fire();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                Space.getInstance().stopTurningStationCW();
                break;
            case KeyEvent.VK_LEFT:
                Space.getInstance().stopTurningStationCCW();
                break;
            case KeyEvent.VK_UP:
                Space.getInstance().resetStationAcceleration();
                break;
            case KeyEvent.VK_DOWN:
                Space.getInstance().resetStationAcceleration();
                break;

        }
    }

}
