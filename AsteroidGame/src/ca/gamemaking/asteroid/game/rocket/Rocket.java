/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.rocket;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.settings.Settings;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Maxime Lajoie
 */
public class Rocket {
    private static final Stroke STROKE = new BasicStroke(1 * Settings.SCALE);

    private static final double TTL = 1.75;
    private static final double SPEED = 625;
    private static final int NB_POINTS = 5;

    private double aliveTime = 0.0;
    private double angle;

    private Point2D.Double position;
    private Point2D.Double direction;
    private Point2D.Double[] shape;
    private Area area;

    private int screenMinX;
    private int screenMinY;
    private int screenMaxX;
    private int screenMaxY;

    public Rocket(double posX, double posY, double offset, double angle) {
        direction = new Point2D.Double();
        direction.x = Math.cos(Math.toRadians(angle));
        direction.y = Math.sin(Math.toRadians(angle));

        position = new Point2D.Double(posX + direction.x * offset, posY + direction.y * offset);
        
        this.angle = angle;
        CreateShape();
        CalculateScreen();
    }
    
    private void CreateShape() {
        shape = new Point2D.Double[NB_POINTS];
        
        shape[0] = new Point2D.Double(0, 4 * Settings.SCALE);
        shape[1] = new Point2D.Double(0, -4 * Settings.SCALE);
        shape[2] = new Point2D.Double(20 * Settings.SCALE, 4 * Settings.SCALE);
        shape[3] = new Point2D.Double(27 * Settings.SCALE, 0);
        shape[4] = new Point2D.Double(20 * Settings.SCALE, -4 * Settings.SCALE);
    }
    
    private double RotateX(double x,double y, double angle) {
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x,double y, double angle) {
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }

    private void CalculateScreen() {
        int offset = (int)(27 * Settings.SCALE);

        screenMinX = Launcher.getGameFrame().insets.left - offset;
        screenMaxX = Settings.RESOLUTION.getX() - Launcher.getGameFrame().insets.right + offset;

        screenMinY = Launcher.getGameFrame().insets.top - offset;
        screenMaxY = Settings.RESOLUTION.getY() - Launcher.getGameFrame().insets.bottom + offset;
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
    
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);

        double x,y;
        x = RotateX(shape[0].x, shape[0].y, angle) + position.x;
        y = RotateY(shape[0].x, shape[0].y, angle) + position.y;

        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (int i = 1; i < NB_POINTS; i++) {
            x = RotateX(shape[i].x, shape[i].y, angle) + position.x;
            y = RotateY(shape[i].x, shape[i].y, angle) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();

        area = new Area(path);

        g2d.setStroke(STROKE);
        g2d.draw(area);
    }
    
    public void update(double delta) {
        aliveTime += delta;
        if (aliveTime >= TTL) {
            destroy();
        }

        position.setLocation(position.x + (direction.x * (SPEED * delta)), position.y + (direction.y * (SPEED * delta)));
        Teleportation();
    }

    public Area getArea() {
        return area;
    }

    public void destroy() {
        Launcher.getGameFrame().rockets.remove(this);
    }
}
