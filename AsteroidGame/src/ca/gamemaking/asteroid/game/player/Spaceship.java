/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.player;

import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 *
 * @author Maxime Lajoie
 */
public class Spaceship {
    
    Point2D.Double position;
    Point2D.Double direction;
    
    final double MAX_SPEED = 500.0;
    final double SPEED_INCREMENT = 35;
    double current_speed = 0.0;
    
    double rotation = 90.0;
    final double ROTATION_SPEED = 180;//3.5;
    
    int nbPointsShape = 4;
    Point2D.Double[] shape;
    
    int nbPointsFire = 11;
    Point2D.Double[] fire;
    
    boolean forward = false;
    
    public Spaceship(){
        position = new Point2D.Double(Settings.RESOLUTION.getX()/2, Settings.RESOLUTION.getY()/2);
        CreateShape();
        CreateFire();
        direction = new Point2D.Double(0.0,0.0);
    }
    
    public Spaceship(Point pos){
        position = new Point2D.Double(pos.x, pos.y);
        CreateShape();
        CreateFire();
        direction = new Point2D.Double(0.0,0.0);
    }
    
    //http://www.rocketshipgames.com/blogs/tjkopena/2015/02/asteroids-drawing-objects/
    private void CreateShape(){
        shape = new Point2D.Double[nbPointsShape];
        
        shape[0] = new Point2D.Double(Settings.SCALE * 24, 0);
        shape[1] = new Point2D.Double(-Settings.SCALE * 24, -Settings.SCALE * 18);
        shape[2] = new Point2D.Double(-Settings.SCALE * 18, 0);
        shape[3] = new Point2D.Double(-Settings.SCALE * 24, Settings.SCALE * 18);
    }
    
    private void CreateFire(){
        fire = new Point2D.Double[nbPointsFire];
        
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
    
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3 * Settings.SCALE));
        
        double x,y;
        x = RotateX(shape[0].x, shape[0].y, rotation) + position.x;
        y = RotateY(shape[0].x, shape[0].y, rotation) + position.y;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (int i = 1; i < nbPointsShape; i++) {
            x = RotateX(shape[i].x, shape[i].y, rotation) + position.x;
            y = RotateY(shape[i].x, shape[i].y, rotation) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();
        
        g2d.draw(path);
        
        if(forward){
            //Draw little fire
            g2d.setStroke(new BasicStroke(2 * Settings.SCALE));
            
            double xFire,yFire;
            xFire = RotateX(fire[0].x, fire[0].y, rotation) + position.x;
            yFire = RotateY(fire[0].x, fire[0].y, rotation) + position.y;
        
            Path2D.Double pathFire = new Path2D.Double();
            pathFire.moveTo(xFire,yFire);
            for (int i = 1; i < nbPointsFire; i++) {
                xFire = RotateX(fire[i].x, fire[i].y, rotation) + position.x;
                yFire = RotateY(fire[i].x, fire[i].y, rotation) + position.y;
                pathFire.lineTo(xFire, yFire);
            }
            g2d.draw(pathFire);
        }
    }
    
    private double RotateX(double x,double y, double angle){
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x,double y, double angle){
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }
    
    public void Move(double delta, HashSet<Integer> inputs){
        if (inputs.contains(Settings.CONTROLS.getFORWARD())){
            forward = true;
            current_speed += SPEED_INCREMENT;
            if (current_speed >= MAX_SPEED)
                current_speed = MAX_SPEED;
        }
        else{
            forward = false;
            current_speed -= SPEED_INCREMENT/2;
            if (current_speed <= 0)
                current_speed = 0;
        }
        
        if (inputs.contains(Settings.CONTROLS.getTURN_RIGHT())){
            rotation %= 360;
            rotation += ROTATION_SPEED * delta;
        }
        
        if (inputs.contains(Settings.CONTROLS.getTURN_LEFT())){
            rotation %= 360;
            rotation -= ROTATION_SPEED * delta;
        }
        
        direction.x = Math.cos(Math.toRadians(rotation));
        direction.y = Math.sin(Math.toRadians(rotation));
        
        position.setLocation(position.x + (direction.x * (current_speed * delta)), position.y + (direction.y * (current_speed * delta)));
    }
}
