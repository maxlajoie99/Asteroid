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
    private static final double TTL = 0.6;
    private static final double GROW_SPEED = 200 * Settings.SCALE;

    private static final Stroke STROKE = new BasicStroke(1 * Settings.SCALE);

    private double aliveTime = 0.0;
    private double size = 0;

    private Point2D.Double position;

    public Explosion(double posX, double posY){
        position = new Point2D.Double(posX, posY);
    }
    
    public void paint(Graphics2D g2d) {
        Ellipse2D.Double boom = new Ellipse2D.Double(position.x - size / 2, position.y - size / 2, size, size);
        g2d.setStroke(STROKE);
        g2d.draw(boom);
    }
    
    public void update(double delta) {
        size += GROW_SPEED * delta;
        aliveTime += delta;
        if (aliveTime >= TTL) {
            destroy();
        }
    }
    
    public void destroy(){
        Launcher.getGameFrame().explosions.remove(this);
    }
}
