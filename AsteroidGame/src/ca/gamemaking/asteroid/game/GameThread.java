/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game;

import ca.gamemaking.asteroid.Launcher;

/**
 *
 * @author Maxime Lajoie
 */
public class GameThread extends Thread{
    
    final int REFRESH_RATE;
    
    boolean running = true;
    
    public GameThread(int fps){
        REFRESH_RATE = fps;
    }
    
    //https://stackoverflow.com/questions/18283199/java-main-game-loop
    public void run(){
        long lastLoopTime = System.nanoTime();
        final long OPTIMAL_TIME = 1000000000 / REFRESH_RATE;
        long lastGameTime = OPTIMAL_TIME/1000000;
        
        while(running){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            
            Launcher.getGameFrame().Update(lastGameTime/1000.0);
            
            //repaint
            Launcher.getGameFrame().repaint();
            
            try {
                long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                lastGameTime = gameTime;
                Thread.sleep(gameTime);
            } catch (Exception e) {
            }
        }   //End of loop
        
    }
}
