/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.player;

import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Maxime Lajoie
 */
public class Spaceship {
    
    Point2D.Double position;
    Point2D.Double direction;
    
    final double MAX_SPEED = 5.0;
    double current_speed = 0.0;
    
    double rotation = 90.0;
    
    int nbPoints = 4;
    Point2D.Double[] shape;
    
    int offset18 = (int)(Settings.SCALE * 18);
    int offset24 = (int)(Settings.SCALE * 24);
    
    KeyEvent input;
    
    public Spaceship(){
        position = new Point2D.Double(Settings.RESOLUTION.getX()/2, Settings.RESOLUTION.getY()/2);
        CreateShape();
        direction = new Point2D.Double(0.0,0.0);
    }
    
    public Spaceship(Point pos){
        position = new Point2D.Double(pos.x, pos.y);
        CreateShape();
        direction = new Point2D.Double(0.0,0.0);
    }
    
    //http://www.rocketshipgames.com/blogs/tjkopena/2015/02/asteroids-drawing-objects/
    private void CreateShape(){
        shape = new Point2D.Double[nbPoints];
        
        shape[0] = new Point2D.Double(offset24, 0);
        shape[1] = new Point2D.Double(-offset24, -offset18);
        shape[2] = new Point2D.Double(-offset18, 0);
        shape[3] = new Point2D.Double(-offset24, offset18);
    }
    
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3 * Settings.SCALE));
        
        double x,y;
        x = RotateX(shape[0].x, shape[0].y, rotation) + position.x;
        y = RotateY(shape[0].x, shape[0].y, rotation) + position.y;
        
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x,y);
        for (int i = 1; i < nbPoints; i++) {
            x = RotateX(shape[i].x, shape[i].y, rotation) + position.x;
            y = RotateY(shape[i].x, shape[i].y, rotation) + position.y;
            path.lineTo(x, y);
        }
        path.closePath();
        
        g2d.draw(path);
        
    }
    
    private double RotateX(double x,double y, double angle){
        return (x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle)));
    }
    
    private double RotateY(double x,double y, double angle){
        return (x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle)));
    }
    
    public void Input(KeyEvent e){
        input = e;
    }
    
    public void Move(double delta){
        //Manage spaceship speed
        if (input != null && input.getKeyCode() == Settings.CONTROLS.getFORWARD()){
            current_speed += 0.1;
            if (current_speed >= MAX_SPEED)
                current_speed = MAX_SPEED;
        }
        else{
            current_speed -= 0.1;
            if (current_speed <= 0)
                current_speed = 0;
        }
        
        if (input != null && input.getKeyCode() == Settings.CONTROLS.getTURN_RIGHT()){
            rotation %= 360;
            rotation += 5;
        }
        
        if (input != null && input.getKeyCode() == Settings.CONTROLS.getTURN_LEFT()){
            rotation %= 360;
            rotation -= 5;
        }
        
        direction.x = Math.cos(Math.toRadians(rotation));
        direction.y = Math.sin(Math.toRadians(rotation));
        
        position.setLocation(position.x + direction.x *(current_speed*delta), position.y + direction.y *(current_speed*delta));
    }
}
