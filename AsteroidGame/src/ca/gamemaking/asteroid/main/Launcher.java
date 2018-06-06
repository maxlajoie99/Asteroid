/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.gamemaking.asteroid.main;

import ca.gamemaking.asteroid.menu.MenuFrame;

/**
 *
 * @author mlajoie
 */
public class Launcher {
    
    static MenuFrame mainMenu;
    
    public MenuFrame getMainMenu(){
        return mainMenu;
    }
    
    public static void main(String[] args){
        
        System.out.println("Launching Asteroid");
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
