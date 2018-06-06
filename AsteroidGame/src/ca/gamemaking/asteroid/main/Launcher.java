/*
 * Copyright © Maxime Lajoie - 2018 - All right reserved
 */
package ca.gamemaking.asteroid.main;

import ca.gamemaking.asteroid.lang.Lang_EN;
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
        
        System.out.println();
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
