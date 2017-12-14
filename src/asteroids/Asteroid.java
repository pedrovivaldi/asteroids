package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Asteroid extends Agent {

    private final double maxspeed = 1;

    public Asteroid() {
        this.x = (Math.random() * (Space.getInstance().getFrameWidth() - sx));
        this.y = (Math.random() * (Space.getInstance().getFrameHeight() - sx));
        this.dx = (Math.random() * maxspeed);
        this.dy = (Math.random() * maxspeed);
        this.sx = 40;
    }

    @Override
    public void move() {
        x += dx;
        y -= dy;
        fixCoordinates();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval((int) x, (int) y, (int) sx, (int) sx);
    }

    @Override
    public void interactWith(List<? extends Agent> others) {
        for (Agent a : others) {
            interactWith(a);
        }
    }

    @Override
    public void interactWith(Agent other) {
        // TODO Auto-generated method stub

    }

    @Override
    public void hit(Agent agent) {
        sx -= 10;
        if (sx <= 0) {
            sx = 0;
            alive = false;
        }
    }

}
