/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.asteroid;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.game.rocket.Rocket;
import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @author Maxime Lajoie
 */
public class Asteroid {
    
    Point2D.Double position;
    Point2D.Double direction;
    
    Point2D.Double[] shape;
    Area a = null;
    
    double speed = 50;
    int min_speed = 50;
    int max_speed = 250;
    
    Random rnd;
    
    double angle = 0;
    double angleSpeed = 0;
    int min_angleSpeed = 15;
    int max_angleSpeed = 90;
    
    public Asteroid(double posX, double posY){
        position = new Point2D.Double(posX, posY);
        rnd = new Random();
        
        speed = rnd.nextInt(max_speed - min_speed) + min_speed;
        angleSpeed = rnd.nextInt(max_angleSpeed - min_angleSpeed) + min_angleSpeed;
        
        GenerateDirection();
        CreateShape();
    }
    
    private void GenerateDirection(){ 
        double startAngle = rnd.nextInt(360);
        
        direction = new Point2D.Double();
        
        direction.x = Math.cos(Math.toRadians(startAngle));
        direction.y = Math.sin(Math.toRadians(startAngle));
    }
    
    private void CreateShape(){
        switch(rnd.nextInt(4)){
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
    
    private double RotateX(double x,double y, double angle){
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x,double y, double angle){
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }
    
    private void Teleportation(){
        
        int offset = a != null ? Math.min(a.getBounds().width, a.getBounds().height): (int)(24 * Settings.SCALE);
        
        int minX = Launcher.getGameFrame().is.left - offset;
        int maxX = Settings.RESOLUTION.getX() - Launcher.getGameFrame().is.right + offset;
        
        int minY = Launcher.getGameFrame().is.top - offset;
        int maxY = Settings.RESOLUTION.getY() - Launcher.getGameFrame().is.bottom + offset;
        
        if (position.x > maxX)
            position.x = minX;
        if (position.x < minX)
            position.x = maxX;
        
        if (position.y > maxY)
            position.y = minY;
        if (position.y < minY)
            position.y = maxY;
        
    }
    
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1 * Settings.SCALE));
        
        double x,y;
        x = RotateX(shape[0].x, shape[0].y, angle) + position.x;
        y = RotateY(shape[0].x, shape[0].y, angle) + position.y;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (int i = 1; i < shape.length; i++) {
            x = RotateX(shape[i].x, shape[i].y, angle) + position.x;
            y = RotateY(shape[i].x, shape[i].y, angle) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();
        
        a = new Area(path);
        
        g2d.draw(a);
    }
    
    public void Update(double delta){
        angle += angleSpeed * delta;
        
        position.setLocation(position.x + (direction.x * (speed * delta)), position.y + (direction.y * (speed * delta)));
        Teleportation();
    }
    
    public void RocketCollision(Rocket m){
        if (m.GetArea() != null && a != null){
            Area a1 = new Area(a);
            a1.intersect(new Area(m.GetArea()));

            if(!a1.isEmpty())
            {
                //TODO Animate this shit
                //TODO Split asteroid?
                m.Destroy();
                this.Destroy();
            }
        }
    }
    
    public Area GetArea(){
        return a;
    }
    
    public void Destroy(){
        Launcher.getGameFrame().asteroids.remove(this);
    }
    
}
