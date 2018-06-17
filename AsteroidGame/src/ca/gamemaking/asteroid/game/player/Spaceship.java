/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.player;

import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author maxla
 */
public class Spaceship {
    
    Point position;
    
    int nbPoints = 4;
    int[] shapeX;
    int[] shapeY;
    
    public Spaceship(){
        position = new Point(Settings.RESOLUTION.getX()/2, Settings.RESOLUTION.getY()/2);
        UpdateShape();
    }
    
    public Spaceship(Point pos){
        position = pos;
        UpdateShape();
    }
    
    private void UpdateShape(){
        shapeX = new int[nbPoints];
        shapeY = new int[nbPoints];
        
        shapeX[0] = position.x;
        shapeY[0] = position.y - 24;
        
        shapeX[1] = position.x - 18;
        shapeY[1] = position.y + 24;
        
        shapeX[2] = position.x;
        shapeY[2] = position.y + 18;
        
        shapeX[3] = position.x + 18;
        shapeY[3] = position.y + 24;
    }
    
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        
        g2d.drawPolygon(shapeX, shapeY, nbPoints);
    }
    
}
