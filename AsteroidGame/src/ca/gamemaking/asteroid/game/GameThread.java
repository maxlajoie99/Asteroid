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
    
    final long REFRESH_RATE;
    boolean running = true;
    
    public GameThread(int fps){
        REFRESH_RATE = fps;
    }
    
    //https://stackoverflow.com/questions/18283199/java-main-game-loop
    public void run(){
        long lastLoopTime = System.nanoTime();
        final long OPTIMAL_TIME = 1000000000 / REFRESH_RATE;
        
        while(running){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);
            
            Launcher.getGameFrame().Update(delta);
            
            //repaint
            Launcher.getGameFrame().repaint();
            
            try {
                long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(gameTime);
            } catch (Exception e) {
            }
        }   //End of loop
    }
}
