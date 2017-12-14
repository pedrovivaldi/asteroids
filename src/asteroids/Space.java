package asteroids;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class Space extends JPanel implements Runnable {

    private int frameWidth = 500;
    private int frameHeight = 400;
    private List<Agent> agents;
    private Station station;
    private Enemy enemy;
    private List<FriendRocket> friendRockets;
    private List<EnemyRocket> enemyRockets;
    private List<Asteroid> asteroids;
    private AsteroidAdder asteroidAdder;
    private final int lifeBarX = 10;
    private final int lifeBarY = 15;
    private final int lifeBarWidth = 100;
    private final int lifeBarHeight = 50;

    private static Space singleton;

    synchronized public static Space getInstance() {
        if (singleton == null) {
            singleton = new Space();
        }
        return singleton;
    }

    private Space() {
        agents = new CopyOnWriteArrayList<>();
        friendRockets = new CopyOnWriteArrayList<>();
        enemyRockets = new CopyOnWriteArrayList<>();
        asteroids = new CopyOnWriteArrayList<>();
    }

    public void reset() {
        agents = new CopyOnWriteArrayList<>();
        friendRockets = new CopyOnWriteArrayList<>();
        enemyRockets = new CopyOnWriteArrayList<>();
        asteroids = new CopyOnWriteArrayList<>();
        enemy = new Enemy();
        agents.add(enemy);
        Thread t = new Thread(enemy);
        t.start();
        this.setFocusable(false);

        for (int i = 0; i < 10; i++) {
            asteroids.add(new Asteroid());
        }

        for (Asteroid a : asteroids) {
            agents.add(a);
        }

    }

    public void updateStation() {
        if (station == null) {
            station = new Station();

            agents.add(station);
        } else {
            station.resetStation();
        }

        agents.remove(enemy);
        enemy = new Enemy();
        Thread t = new Thread(enemy);
        t.start();
        agents.add(enemy);
    }

    public void removeStation() {
        agents.remove(station);
        station = null;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, frameWidth, frameHeight);

        for (Agent a : agents) {
            a.paint(g);
        }

        if (station != null) {
            double stationLifePerc = station.getLifePercentage();

            if (stationLifePerc < 0.3) {
                g.setColor(Color.RED);
            } else if (stationLifePerc > 0.7) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.YELLOW);
            }
            if (stationLifePerc <= 0) {
                stationLifePerc = 0.01;
            }
            g.fillRect(lifeBarX, lifeBarY, (int) (lifeBarWidth * stationLifePerc), lifeBarHeight);

            g.setColor(Color.BLACK);
            g.drawRect(lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight);

            g.setFont(new Font("Consolas", 0, 12));
            g.drawString("LIFE", 10, 10);
            g.drawString((int) (stationLifePerc * 100) + "%", 50, 45);
        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Consolas", 0, 18));
            g.drawString("Press [S] to (re)start", 140, 170);
            g.drawString("Press [Q] to quit", 140, 190);
            g.drawString("Press [X] to exit", 140, 210);
        }

        if (enemy != null) {
            double enemyLifePerc = enemy.getLifePercentage();

            if (enemyLifePerc < 0.3) {
                g.setColor(Color.RED);
            } else if (enemyLifePerc > 0.7) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.YELLOW);
            }
            if (enemyLifePerc <= 0) {
                enemyLifePerc = 0.01;
            }
            g.fillRect(lifeBarX + 240, lifeBarY, (int) (lifeBarWidth * enemyLifePerc), lifeBarHeight);

            g.setColor(Color.BLACK);
            g.drawRect(lifeBarX + 240, lifeBarY, lifeBarWidth, lifeBarHeight);

            g.setFont(new Font("Consolas", 0, 12));
            g.drawString("ENEMY LIFE", 250, 10);
            g.drawString((int) (enemyLifePerc * 100) + "%", 290, 45);

        }
    }

    @Override
    public void run() {
        reset();
        asteroidAdder = new AsteroidAdder(10000);
        asteroidAdder.start();

        try {
            while (true) {

                for (Agent a : agents) {
                    a.move();
                }

                // Check de interação
                if (station != null) {

                    if (station.isAlive()) {

                        // Check colisao entre station e asteroid
                        station.interactWith(asteroids);

                        // Check colisao entre rocket station
                        for (Rocket rocket : enemyRockets) {
                            rocket.interactWith(station);
                        }

                        if (enemy != null) {

                            if (enemy.isAlive()) {

                                for (Rocket rocket : friendRockets) {
                                    rocket.interactWith(enemy);
                                    rocket.interactWith(station);
                                }
                            } else {
                                agents.remove(enemy);
                                enemy = null;
                                Applet.newAudioClip(getClass().getResource("Explosion.wav")).play();
                            }
                        }
                    } else {
                        agents.remove(station);
                        station = null;
                        Applet.newAudioClip(getClass().getResource("Explosion.wav")).play();
                    }
                }

                // Check colisao entre rocket e asteroid
                // Para cada rocket: rocket interage com asteroid
                for (Rocket rocket : friendRockets) {
                    rocket.interactWith(asteroids);
                }

                //enemy.interactWith(friendRockets);
                // Remover todos elementos mortos
                removeDeadAgents();

                // Criar novos asteroids
                this.repaint();
                Thread.sleep(30);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void removeDeadAgents() {
        for (Agent a : agents) {
            if (!a.isAlive()) {
                agents.remove(a);
            }
        }

        for (FriendRocket fr : friendRockets) {
            if (!fr.isAlive()) {
                friendRockets.remove(fr);
            }
        }

        for (EnemyRocket er : enemyRockets) {
            if (!er.isAlive()) {
                enemyRockets.remove(er);
            }
        }

        for (Asteroid as : asteroids) {
            if (!as.isAlive()) {
                asteroids.remove(as);
            }
        }

        // Keep 5 asteroids in the space
        while (asteroids.size() < 5) {
            asteroids.add(new Asteroid());
        }
    }

    public void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
        agents.add(asteroid);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public Station getStation() {
        return station;
    }

    public void turnStationCW() {
        if (station == null) {
            return;
        }
        station.setTurningCW(true);
    }

    public void turnStationCCW() {
        if (station == null) {
            return;
        }
        station.setTurningCCW(true);
    }

    public void stopTurningStationCW() {
        if (station == null) {
            return;
        }
        station.setTurningCW(false);
    }

    public void stopTurningStationCCW() {
        if (station == null) {
            return;
        }
        station.setTurningCCW(false);
    }

    public void acceleratingStation(double a) {
        if (station == null) {
            return;
        }
        station.accelerate(a);
    }

    public void resetStationAcceleration() {
        if (station == null) {
            return;
        }

        station.resetAcceleration();
    }

    public void addFriendRocket(FriendRocket rocket) {
        if (friendRockets.size() < 5) {
            Applet.newAudioClip(getClass().getResource("Shot.wav")).play();
            agents.add(rocket);
            friendRockets.add(rocket);
        }
    }

    public void addEnemyRocket(EnemyRocket rocket) {
        if (enemyRockets.size() < 5) {
            agents.add(rocket);
            enemyRockets.add(rocket);
        }
    }
}
