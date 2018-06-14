/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.game.object.Spaceship;
import java.awt.Graphics;

/**
 *
 * @author maxla
 */
public class GameThread extends Thread{
    
    long rate;
    Graphics g;
    
    Spaceship player;
    
    public GameThread(int fps, Graphics g){
        rate = 1000/fps;
        this.g = g;
    }
    
    public void run(){
        
        player = new Spaceship(g);
        
        try {
            while (Launcher.getGameFrame().gameStarted){
                
                player.paint();
                
                if(Launcher.getGameFrame().pause)
                    wait();
                
                Thread.sleep(rate);
            }
        } catch (Exception e) {
            
        }
    }
}
