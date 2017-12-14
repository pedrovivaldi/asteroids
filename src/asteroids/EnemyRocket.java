package asteroids;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyRocket extends Rocket {

    public EnemyRocket(double fireAngle) {
        Enemy enemy = Space.getInstance().getEnemy();
        if (enemy != null) {
            x = enemy.getX();
            y = enemy.getY();
            sx = modulo * Math.cos(fireAngle);
            sy = modulo * Math.sin(fireAngle);
            dx = Math.cos(fireAngle) * speed;
            dy = Math.sin(fireAngle) * speed;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine((int) x, (int) y, (int) (x + sx), (int) (y - sy));
    }

}
