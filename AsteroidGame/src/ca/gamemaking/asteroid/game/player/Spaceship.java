/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game.player;

import ca.gamemaking.asteroid.settings.Settings;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author maxla
 */
public class Spaceship {
    
    Graphics g;
    int x;
    int y;
    
    public Spaceship(Graphics g){
        this.g = g;
        x = Settings.RESOLUTION.getX()/2;
        y = Settings.RESOLUTION.getY()/2;
    }
    
    public void paint(){
        g.setColor(Color.red);
        g.fillOval(x, y, 100, 100);
        x++;
    }
    
}
