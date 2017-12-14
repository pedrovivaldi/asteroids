package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Station extends Agent {

    protected double angle = 0;
    protected final double modulo = 20.0;
    protected boolean isTurningCW = false;
    protected boolean isTurningCCW = false;
    protected double speed = 2;
    protected double acceleration = 0;
    protected final double maxspeed = 1.5;
    protected int life;
    protected int totalLife = 500;
    protected double middleLineY;
    protected double middleLineX;
    protected double topPointX;
    protected double topPointY;
    protected double baseLineX1;
    protected double baseLineX2;
    protected double baseLineY1;
    protected double baseLineY2;

    public Station() {
        resetStation();
    }

    public void resetStation() {
        this.x = (int) (0.5 * Space.getInstance().getFrameWidth());
        this.y = (int) (0.5 * Space.getInstance().getFrameHeight());
        sx = 20;
        sy = 20;
        life = totalLife;
        acceleration = 0;
        angle = 0;
    }

    @Override
    public void move() {

        if (isTurningCW) {
            angle -= 0.1;
        }
        if (isTurningCCW) {
            angle += 0.1;
        }

        this.dx += Math.cos(angle) * speed * acceleration;
        this.dy += Math.sin(angle) * speed * acceleration;

        if (dx > maxspeed) {
            dx = maxspeed;
        }
        if (dy > maxspeed) {
            dy = maxspeed;
        }
        if (dx < (-maxspeed)) {
            dx = (-maxspeed);
        }
        if (dy < (-maxspeed)) {
            dy = (-maxspeed);
        }

        x += dx;
        y -= dy;

        fixCoordinates();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);

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

        /* To draw the station without using Polygon class
         g.drawLine((int) x, (int) y, (int) topPointX, (int) topPointY);
         g.drawLine((int) baseLineX1, (int) baseLineY1, (int) baseLineX2, (int) baseLineY2
         g.drawLine((int) baseLineX1, (int) baseLineY1, (int) topPointX, (int) topPointY); 
         g.drawLine((int) baseLineX2, (int) baseLineY2, (int) topPointX, (int) topPointY); */
        //g.setColor(Color.BLUE);
        //g.drawLine((int) x, (int) y, (int) topPointX, (int) topPointY);
    }

    @Override
    public void interactWith(List<? extends Agent> others) {
        for (Agent other : others) {
            interactWith(other);
        }
    }

    @Override
    public void interactWith(Agent other) {
        //check colisao linhas laterais 
        if (other instanceof Asteroid) {
            if (collisionDetection(x, y, baseLineX1, baseLineY1, other.x + 20, other.y + 20, other.sx)) {
                other.hit(this);
                this.hit(other);
            }
            if (collisionDetection(x, y, baseLineX2, baseLineY2, other.x, other.y, other.sx)) {
                other.hit(this);
                this.hit(other);
            }
        }
    }

    @Override
    public void hit(Agent agent) {
        int damageTaken = 10;
        if (agent instanceof EnemyRocket) {
            damageTaken = 100;
        }
        if (agent instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) agent;
            damageTaken = (int) asteroid.sx;
        }

        life -= damageTaken;

        if (life <= 0) {
            alive = false;
        }
    }

    public void fire() {
        if (this.alive) {
            
            int a = 1;
            long asd = (int) a;
            
            FriendRocket rocket = new FriendRocket();
            Space.getInstance().addFriendRocket(rocket);
        }
    }

    public boolean isTurningCW() {
        return isTurningCW;
    }

    public void setTurningCW(boolean isTurningCW) {
        this.isTurningCW = isTurningCW;
    }

    public boolean isTurningCCW() {
        return isTurningCCW;
    }

    public void setTurningCCW(boolean isTurningCCW) {
        this.isTurningCCW = isTurningCCW;
    }

    public void accelerate(double a) {
        this.acceleration += a;
    }

    public void resetAcceleration() {
        this.acceleration = 0;
    }

    public double getAngle() {
        return angle;
    }

    public double getModulo() {
        return modulo;
    }

    public boolean isIsTurningCW() {
        return isTurningCW;
    }

    public boolean isIsTurningCCW() {
        return isTurningCCW;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getMaxspeed() {
        return maxspeed;
    }

    public int getLife() {
        return life;
    }

    public double getLifePercentage() {
        return life / (double) totalLife;
    }

    public int getTotalLife() {
        return totalLife;
    }

    public double getMiddleLineY() {
        return middleLineY;
    }

    public double getMiddleLineX() {
        return middleLineX;
    }

    public double getTopPointX() {
        return topPointX;
    }

    public double getTopPointY() {
        return topPointY;
    }

    public double getBaseLineX1() {
        return baseLineX1;
    }

    public double getBaseLineX2() {
        return baseLineX2;
    }

    public double getBaseLineY1() {
        return baseLineY1;
    }

    public double getBaseLineY2() {
        return baseLineY2;
    }
}
