/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;

/**
 *
 * @author Maxime Lajoie
 */
public class Resolution {
    private int x;
    private int y;
    
    private Resolution() {}
    
    public Resolution(int p_x, int p_y) {
        x = p_x;
        y = p_y;
    }
    
    public Resolution(String resolution) {
        String[] pixels = resolution.split("x");

        if (pixels.length != 2) {
            System.out.println(Resolution.class.getName() + ": Invalid string format");
        } else {
            x = Integer.parseInt(pixels[0]);
            y = Integer.parseInt(pixels[1]);
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Resolution adjust() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        
        for (GraphicsDevice gd : gds){
            int screenX = gd.getDisplayMode().getWidth();
            int screenY = gd.getDisplayMode().getHeight();
            Insets i = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
            
            if (y > screenY - (i.top + i.bottom) && y <= screenY) {
                y -= (i.top + i.bottom);
            }

            if (x > screenX - (i.right + i.left) && x <= screenX) {
                x -= (i.right + i.left);
            }
        }

        return this;
    }
    
    @Override
    public String toString(){
        return x + "x" + y;
    }
}
