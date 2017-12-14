package asteroids;

import java.awt.Graphics;
import java.util.List;

public abstract class Agent {

    double x, y, sx, sy, dx, dy;
    boolean alive = true;

    public abstract void move();

    public abstract void paint(Graphics g);

    public abstract void interactWith(List<? extends Agent> others);

    public abstract void interactWith(Agent other);

    public abstract void hit(Agent agent);

    protected boolean collisionDetection(double x1, double y1, double x2, double y2, double xa, double ya, double ra) {
        double dist = Math.sqrt(Math.pow(x1 - xa, 2) + Math.pow(y1 - ya, 2));
        if (dist < ra) {
            return true;
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSx() {
        return sx;
    }

    public double getSy() {
        return sy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void fixCoordinates() {
        if (x > Space.getInstance().getFrameWidth()) {
            x = 0;
        }
        if (x < 0) {
            x = Space.getInstance().getFrameWidth();
        }
        if (y > Space.getInstance().getFrameHeight()) {
            y = 0;
        }
        if (y < 0) {
            y = Space.getInstance().getFrameHeight();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
