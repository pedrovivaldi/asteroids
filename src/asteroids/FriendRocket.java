package asteroids;

import java.awt.Color;
import java.awt.Graphics;

public class FriendRocket extends Rocket {

    public FriendRocket() {
        Station station = Space.getInstance().getStation();
        if (station != null) {
            double angle = station.getAngle();

            x = station.getTopPointX() + 5 * Math.cos(angle);
            y = station.getTopPointY() - 5 * Math.sin(angle);

            sx = modulo * Math.cos(angle);
            sy = modulo * Math.sin(angle);
            dx = Math.cos(angle) * speed;
            dy = Math.sin(angle) * speed;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) x, (int) y, (int) (x + sx), (int) (y - sy));

    }

}
