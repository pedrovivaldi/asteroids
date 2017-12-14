package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enemy extends Station implements Runnable {

    public Enemy() {
        this.x = (int) (Math.random() * Space.getInstance().getFrameWidth());
        this.y = (int) (Math.random() * Space.getInstance().getFrameHeight());
        acceleration = 0.5;
        totalLife = 250;
        life = totalLife;
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);

        middleLineY = modulo * Math.sin(angle);
        middleLineX = modulo * Math.cos(angle);
        topPointX = (x + middleLineX);
        topPointY = (y - middleLineY);
        baseLineX1 = x - (modulo / 4) * Math.sin(angle);
        baseLineX2 = x + (modulo / 4) * Math.sin(angle);
        baseLineY1 = y + ((-modulo) / 4) * Math.cos(angle);
        baseLineY2 = y + +(modulo / 4) * Math.cos(angle);

        int[] pointsX = {(int) topPointX, (int) baseLineX1, (int) baseLineX2};
        int[] pointsY = {(int) topPointY, (int) baseLineY1, (int) baseLineY2};

        g.fillPolygon(pointsX, pointsY, 3);
    }

    public void fire(Station station) {
        if (station != null) {
            double theta = Math.atan(Math.abs(y - station.y) / Math.abs(x - station.x));

            if (station.x > x && station.y > y) {
                theta = -theta;
            } else if (station.x > x && station.y < y) {
                //theta = 2 * Math.PI - theta;
            } else if (station.x < x && station.y > y) {
                theta += Math.PI;
            } else {
                theta += Math.PI/2;
            }

            EnemyRocket rocket = new EnemyRocket(theta);
            Space.getInstance().addEnemyRocket(rocket);
        }
    }

    @Override
    public void hit(Agent agent) {
        int damageTaken = 10;
        if (agent instanceof FriendRocket) {
            damageTaken = 100;
        }

        if (life <= 0) {
            alive = false;
        } else {
            life -= damageTaken;
        }
    }

    @Override
    public void run() {
        for (;;) {
            this.angle = Math.round(Math.random() * 2 * Math.PI);
            this.speed = Math.round(Math.random() * 2);

            fire(Space.getInstance().getStation());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
