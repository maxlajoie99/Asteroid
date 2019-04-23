/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.asteroid;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.game.rocket.Rocket;
import ca.gamemaking.asteroid.settings.Settings;
import ca.gamemaking.asteroid.sound.SoundPlayer;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Maxime Lajoie
 */
public class Asteroid {

    private static final int VALUE = 100;
    private static final int MIN_SPEED = 50;
    private static final int MAX_SPEED = 250;
    private static final int MIN_ANGULAR_SPEED = 15;
    private static final int MAX_ANGULAR_SPEED = 90;

    private static final Stroke STROKE = new BasicStroke(1 * Settings.SCALE);

    private Point2D.Double position;
    private Point2D.Double direction;
    private Point2D.Double[] shape;
    private Area area;

    private double moveSpeed;
    private double angularSpeed;

    private double currentAngle = 0;

    private int screenMinX;
    private int screenMinY;
    private int screenMaxX;
    private int screenMaxY;

    public Asteroid(double posX, double posY) {
        position = new Point2D.Double(posX, posY);

        moveSpeed = Settings.RANDOM.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
        angularSpeed = Settings.RANDOM.nextInt(MAX_ANGULAR_SPEED - MIN_ANGULAR_SPEED) + MIN_ANGULAR_SPEED;
        
        GenerateDirection();
        CreateShape();
        CalculateScreen();
    }
    
    private void GenerateDirection() {
        double startAngle = Settings.RANDOM.nextInt(360);
        
        direction = new Point2D.Double();
        
        direction.x = Math.cos(Math.toRadians(startAngle));
        direction.y = Math.sin(Math.toRadians(startAngle));
    }
    
    private void CreateShape() {
        switch(Settings.RANDOM.nextInt(4)) {
            case 0: 
                shape = new Point2D.Double[9];
                shape[0] = new Point2D.Double(0, 48 * Settings.SCALE);
                shape[1] = new Point2D.Double(48 * Settings.SCALE, 16 * Settings.SCALE);
                shape[2] = new Point2D.Double(64 * Settings.SCALE, -16 * Settings.SCALE);
                shape[3] = new Point2D.Double(48 * Settings.SCALE, -48 * Settings.SCALE);
                shape[4] = new Point2D.Double(0, -64 * Settings.SCALE);
                shape[5] = new Point2D.Double(-16 * Settings.SCALE, -32 * Settings.SCALE);
                shape[6] = new Point2D.Double(-48 * Settings.SCALE, -16 * Settings.SCALE);
                shape[7] = new Point2D.Double(-64 * Settings.SCALE, 0);
                shape[8] = new Point2D.Double(-48 * Settings.SCALE, 32 * Settings.SCALE);
                break;
            case 1:
                shape = new Point2D.Double[9];
                shape[0] = new Point2D.Double(0, 48 * Settings.SCALE);
                shape[1] = new Point2D.Double(48 * Settings.SCALE, 32 * Settings.SCALE);
                shape[2] = new Point2D.Double(64 * Settings.SCALE, 0);
                shape[3] = new Point2D.Double(48 * Settings.SCALE, -32 * Settings.SCALE);
                shape[4] = new Point2D.Double(16 * Settings.SCALE, -64 * Settings.SCALE);
                shape[5] = new Point2D.Double(-16 * Settings.SCALE, -64 * Settings.SCALE);
                shape[6] = new Point2D.Double(-48 * Settings.SCALE, -32 * Settings.SCALE);
                shape[7] = new Point2D.Double(-64 * Settings.SCALE, 0);
                shape[8] = new Point2D.Double(-48 * Settings.SCALE, 48 * Settings.SCALE);
                break;
            case 2:
                shape = new Point2D.Double[10];
                shape[0] = new Point2D.Double(16 * Settings.SCALE, 32 * Settings.SCALE);
                shape[1] = new Point2D.Double(64 * Settings.SCALE, 32 * Settings.SCALE);
                shape[2] = new Point2D.Double(80 * Settings.SCALE, 0);
                shape[3] = new Point2D.Double(48 * Settings.SCALE, -48 * Settings.SCALE);
                shape[4] = new Point2D.Double(0, -32 * Settings.SCALE);
                shape[5] = new Point2D.Double(-16 * Settings.SCALE, -16 * Settings.SCALE);
                shape[6] = new Point2D.Double(-48 * Settings.SCALE, -16 * Settings.SCALE);
                shape[7] = new Point2D.Double(-64 * Settings.SCALE, 0);
                shape[8] = new Point2D.Double(-64 * Settings.SCALE, 32 * Settings.SCALE);
                shape[9] = new Point2D.Double(-32 * Settings.SCALE, 64 * Settings.SCALE);
                break;
            case 3:
                shape = new Point2D.Double[7];
                shape[0] = new Point2D.Double(0, 32 * Settings.SCALE);
                shape[1] = new Point2D.Double(32 * Settings.SCALE, 32 * Settings.SCALE);
                shape[2] = new Point2D.Double(48 * Settings.SCALE, 0);
                shape[3] = new Point2D.Double(16 * Settings.SCALE, -32 * Settings.SCALE);
                shape[4] = new Point2D.Double(-32 * Settings.SCALE, -32 * Settings.SCALE);
                shape[5] = new Point2D.Double(-48 * Settings.SCALE, 0);
                shape[6] = new Point2D.Double(-48 * Settings.SCALE, 48 * Settings.SCALE);
                break;
        }
    }

    private void CalculateScreen() {
        int offset = (int)(24 * Settings.SCALE);

        screenMinX = Launcher.getGameFrame().insets.left - offset;
        screenMaxX = Settings.RESOLUTION.getX() - Launcher.getGameFrame().insets.right + offset;

        screenMinY = Launcher.getGameFrame().insets.top - offset;
        screenMaxY = Settings.RESOLUTION.getY() - Launcher.getGameFrame().insets.bottom + offset;
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
    
    public void paint(Graphics2D g2d) {
        double x, y;
        x = RotateX(shape[0].x, shape[0].y, currentAngle) + position.x;
        y = RotateY(shape[0].x, shape[0].y, currentAngle) + position.y;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (Point2D.Double point : shape) {
            x = RotateX(point.x, point.y, currentAngle) + position.x;
            y = RotateY(point.x, point.y, currentAngle) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();
        
        area = new Area(path);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(STROKE);
        g2d.draw(area);
    }
    
    public void update(double delta) {
        currentAngle += angularSpeed * delta;
        
        position.setLocation(position.x + (direction.x * (moveSpeed * delta)), position.y + (direction.y * (moveSpeed * delta)));
        Teleportation();
    }
    
    public void collideRocket(Rocket m) {
        if (m.getArea() != null && area != null) {
            Area a1 = new Area(area);
            a1.intersect(m.getArea());

            if (!a1.isEmpty()) {
                SoundPlayer.play(SoundPlayer.ASTEROID_EXPLOSION);
                Launcher.getGameFrame().addPoints(VALUE);

                m.destroy();
                this.destroy();
            }
        }
    }
    
    public Area getArea(){
        return area;
    }
    
    public void destroy(){
        Launcher.getGameFrame().explosions.add(new Explosion(position.x, position.y));
        Launcher.getGameFrame().asteroids.remove(this);
    }
}
