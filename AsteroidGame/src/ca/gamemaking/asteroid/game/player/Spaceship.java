/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.player;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.game.asteroid.Asteroid;
import ca.gamemaking.asteroid.game.rocket.Rocket;
import ca.gamemaking.asteroid.settings.Settings;
import ca.gamemaking.asteroid.sound.SoundPlayer;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Maxime Lajoie
 */
public class Spaceship {
    private static final Stroke SHIP_STROKE = new BasicStroke(3 * Settings.SCALE);
    private static final Stroke FIRE_STROKE = new BasicStroke(2 * Settings.SCALE);

    private static final Color STANDARD_COLOR = Color.WHITE;
    private static final Color INVULNERABILITY_COLOR = Color.CYAN;

    private static final double INVULNERABILITY_TIME = 5.0;

    private static final double MAX_SPEED = 500.0;
    private static final double SPEED_INCREMENT = 350.0;
    private static final double ROTATION_SPEED = 210;
    private static final double SHOT_DELAY = 0.5;

    private static final int NB_POINTS_SHIP = 4;
    private static final int NB_POINTS_FIRE = 11;

    private Point2D.Double position;
    private Point2D.Double direction;
    private Area area;

    private double currentInvulnerabilityTime = 0.0;
    private double currentSpeed = 0.0;
    private boolean forward = false;
    private double rotation = 90.0;
    private double delay = SHOT_DELAY;

    private Point2D.Double[] ship;
    private Point2D.Double[] fire;

    private int screenMinX;
    private int screenMinY;
    private int screenMaxX;
    private int screenMaxY;

    public Spaceship() {
        position = new Point2D.Double(Settings.RESOLUTION.getX() / 2, Settings.RESOLUTION.getY() / 2);

        CreateShape();
        CreateFire();
        CalculateScreen();

        direction = new Point2D.Double(0.0,0.0);
    }
    
    public Spaceship(Point pos) {
        position = new Point2D.Double(pos.x, pos.y);

        CreateShape();
        CreateFire();
        CalculateScreen();

        direction = new Point2D.Double(0.0,0.0);
    }

    private void CreateShape() {
        ship = new Point2D.Double[NB_POINTS_SHIP];
        
        ship[0] = new Point2D.Double(Settings.SCALE * 24, 0);
        ship[1] = new Point2D.Double(-Settings.SCALE * 24, -Settings.SCALE * 18);
        ship[2] = new Point2D.Double(-Settings.SCALE * 18, 0);
        ship[3] = new Point2D.Double(-Settings.SCALE * 24, Settings.SCALE * 18);
    }
    
    private void CreateFire() {
        fire = new Point2D.Double[NB_POINTS_FIRE];
        
        fire[0] = new Point2D.Double(-Settings.SCALE * 20, Settings.SCALE * 6);
        fire[1] = new Point2D.Double(-Settings.SCALE * 30, Settings.SCALE * 16);
        fire[2] = new Point2D.Double(-Settings.SCALE * 26, Settings.SCALE * 8);
        fire[3] = new Point2D.Double(-Settings.SCALE * 30, Settings.SCALE * 10);
        fire[4] = new Point2D.Double(-Settings.SCALE * 26, Settings.SCALE * 4);
        fire[5] = new Point2D.Double(-Settings.SCALE * 30, 0);
        fire[6] = new Point2D.Double(-Settings.SCALE * 26, -Settings.SCALE * 4);
        fire[7] = new Point2D.Double(-Settings.SCALE * 30, -Settings.SCALE * 10);
        fire[8] = new Point2D.Double(-Settings.SCALE * 26, -Settings.SCALE * 8);
        fire[9] = new Point2D.Double(-Settings.SCALE * 30, -Settings.SCALE * 16);
        fire[10] = new Point2D.Double(-Settings.SCALE * 20, -Settings.SCALE * 6);
    }

    private void CalculateScreen() {
        int offset = (int)(24 * Settings.SCALE);

        screenMinX = Launcher.getGameFrame().insets.left - offset;
        screenMaxX = Settings.RESOLUTION.getX() - Launcher.getGameFrame().insets.right + offset;

        screenMinY = Launcher.getGameFrame().insets.top - offset;
        screenMaxY = Settings.RESOLUTION.getY() - Launcher.getGameFrame().insets.bottom + offset;
    }
    
    public void paint(Graphics2D g2d) {
        Path2D.Double path = DrawPath(ship, NB_POINTS_SHIP);
        path.closePath();
        
        area = new Area(path);

        g2d.setColor(currentInvulnerabilityTime < INVULNERABILITY_TIME ? INVULNERABILITY_COLOR : STANDARD_COLOR);
        g2d.setStroke(SHIP_STROKE);
        g2d.draw(area);
        
        if (forward) {
            Path2D.Double pathFire = DrawPath(fire, NB_POINTS_FIRE);
            g2d.setStroke(FIRE_STROKE);
            g2d.draw(pathFire);
        }
    }

    private Path2D.Double DrawPath(Point2D.Double[] shape, int nbPoints) {
        double x,y;
        Path2D.Double path = new Path2D.Double();
        path.moveTo(RotateX(shape[0].x, shape[0].y, rotation) + position.x, RotateY(shape[0].x, shape[0].y, rotation) + position.y);
        for (int i = 1; i < nbPoints; i++) {
            x = RotateX(shape[i].x, shape[i].y, rotation) + position.x;
            y = RotateY(shape[i].x, shape[i].y, rotation) + position.y;
            path.lineTo(x, y);
        }

        return path;
    }
    
    private double RotateX(double x, double y, double angle) {
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x, double y, double angle) {
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }

    private void Teleportation() {
        if (position.x > screenMaxX)
            position.x = screenMinX;
        if (position.x < screenMinX)
            position.x = screenMaxX;

        if (position.y > screenMaxY)
            position.y = screenMinY;
        if (position.y < screenMinY)
            position.y = screenMaxY;
    }
    
    public void update(double delta, HashSet<Integer> inputs) {
        if (currentInvulnerabilityTime < INVULNERABILITY_TIME) {
            currentInvulnerabilityTime += delta;
        }

        if (inputs.contains(Settings.CONTROLS.getFORWARD())) {
            forward = true;
            SoundPlayer.play(SoundPlayer.THRUST);
            currentSpeed += SPEED_INCREMENT * delta;
            if (currentSpeed >= MAX_SPEED) {
                currentSpeed = MAX_SPEED;
            }
        } else {
            forward = false;
            currentSpeed -= SPEED_INCREMENT * delta;
            if (currentSpeed <= 0) {
                currentSpeed = 0;
            }
        }
        
        if (inputs.contains(Settings.CONTROLS.getTURN_RIGHT())) {
            rotation %= 360;
            rotation += ROTATION_SPEED * delta;
        }
        
        if (inputs.contains(Settings.CONTROLS.getTURN_LEFT())) {
            rotation %= 360;
            rotation -= ROTATION_SPEED * delta;
        }
        
        direction.x = Math.cos(Math.toRadians(rotation));
        direction.y = Math.sin(Math.toRadians(rotation));
        
        delay += delta;
        if (inputs.contains(Settings.CONTROLS.getSHOOT())) {
            if (delay >= SHOT_DELAY) {
                SoundPlayer.play(SoundPlayer.SHOOT);
                Launcher.getGameFrame().rockets.add(new Rocket(position.x, position.y, Settings.SCALE * 24, rotation));
                delay = 0.0;
            }
        }
        
        position.setLocation(position.x + (direction.x * (currentSpeed * delta)), position.y + (direction.y * (currentSpeed * delta)));
        Teleportation();
    }
    
    public void collideAsteroids(List<Asteroid> ars) {
        if (currentInvulnerabilityTime < INVULNERABILITY_TIME)
            return;

        for (Asteroid ar : ars) {
            if (ar == null)
                continue;

            if (ar.getArea() != null && area != null) {
                Area a1 = new Area(area);
                a1.intersect(new Area(ar.getArea()));

                if(!a1.isEmpty()) {
                    ar.destroy();
                    this.Kill();

                    break;
                }
            }
        }
    }

    public Point2D.Double getPosition() {
        return position;
    }
    
    private void Kill() {
        SoundPlayer.play(SoundPlayer.SPACESHIP_EXPLOSION);
        Launcher.getGameFrame().removeLife();
        Launcher.getGameFrame().player = new Spaceship();
    }
}
