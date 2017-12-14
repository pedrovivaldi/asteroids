package asteroids;

import java.util.List;

public abstract class Rocket extends Agent {

    int distance = 0;
    final int speed = 3;
    final double modulo = 3;

    public void move() {
        if (distance < 600) {
            x += dx;
            y -= dy;
            fixCoordinates();
            distance += Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        } else {
            this.alive = false;
        }
    }

    public void interactWith(List<? extends Agent> others) {
        for (Agent other : others) {
            interactWith(other);
        }

    }

    public void hit(Agent agent) {
        this.alive = false;
    }

    public void interactWith(Agent other) {
        if (other instanceof Asteroid) {
            if (collisionDetection((int) x, (int) y, (int) (x + sx), (int) (y + sy), other.x, other.y, other.sx)) {
                other.hit(this);
                this.hit(other);
            }
        }
        
        if (other instanceof Station) {
            if (collisionDetection((int) x, (int) y, (int) (x + sx), (int) (y + sy), other.x, other.y, other.sx)) {
                other.hit(this);
                this.hit(other);
            }
        }
    }
}
