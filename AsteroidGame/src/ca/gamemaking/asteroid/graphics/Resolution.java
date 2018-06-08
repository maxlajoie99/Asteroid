/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;

/**
 *
 * @author mlajoie
 */
public class Resolution {
    
    private int x;
    private int y;
    
    private Resolution(){}
    
    public Resolution(int p_x, int p_y) {
        x = p_x;
        y = p_y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void Adjust(){
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        
        for (GraphicsDevice gd : gds){
            DisplayMode dm = gd.getDisplayMode();
            System.out.println(dm.getWidth() +"x"+dm.getHeight());
            Insets i = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
            System.out.println(i.top +"-"+i.right+"-"+i.bottom+"-"+i.left);
        }
        
    }
    
    @Override
    public String toString(){
        return x + "x" + y;
    }
    
}
