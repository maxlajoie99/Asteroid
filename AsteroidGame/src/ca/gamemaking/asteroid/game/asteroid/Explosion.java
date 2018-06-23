/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.asteroid;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Maxime Lajoie
 */
public class Explosion {
    
    double aliveTime = 0.0;
    final double TTL = 0.6;
    
    double growth = 200 * Settings.SCALE;
    
    Point2D.Double position;
    double size = 0;
    
    public Explosion(double posX, double posY){
        position = new Point2D.Double(posX, posY);
    }
    
    public void paint(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(1 * Settings.SCALE));
        Ellipse2D.Double boom = new Ellipse2D.Double(position.x - size/2, position.y - size/2, size, size);
        g2d.draw(boom);
    }
    
    public void Update(double delta){
        size += growth * delta;
        aliveTime += delta;
        if (aliveTime >= TTL)
            Destroy();
    }
    
    public void Destroy(){
        Launcher.getGameFrame().explosions.remove(this);
    }
    
}
