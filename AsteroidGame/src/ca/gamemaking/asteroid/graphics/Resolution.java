/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

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
    
    @Override
    public String toString(){
        return x + "x" + y;
    }
    
}
