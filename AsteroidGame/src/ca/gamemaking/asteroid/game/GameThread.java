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
    private final int REFRESH_RATE;
    
    private boolean running = true;
    
    public GameThread(int fps) {
        REFRESH_RATE = fps;
    }
    
    //https://stackoverflow.com/questions/18283199/java-main-game-loop
    public void run() {
        long lastLoopTime;
        final long OPTIMAL_TIME = 1000000000 / REFRESH_RATE;
        long lastGameTime = OPTIMAL_TIME / 1000000;
        
        while(running) {
            lastLoopTime = System.nanoTime();
            
            Launcher.getGameFrame().update(lastGameTime / 1000.0);
            Launcher.getGameFrame().repaint();
            
            lastGameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;

            try {
                if (lastGameTime > 0) {
                    Thread.sleep(lastGameTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stopThread(){
        running = false;
    }
}
