/*
 * Copyright © Maxime Lajoie - 2018 - All right reserved
 */
package ca.gamemaking.asteroid.main;

import ca.gamemaking.asteroid.lang.Lang_BaseClass;
import ca.gamemaking.asteroid.lang.Lang_EN;
import ca.gamemaking.asteroid.menu.MenuFrame;

/**
 *
 * @author mlajoie
 */
public class Launcher {
    
    static MenuFrame mainMenu;
    static Lang_BaseClass lang;
    
    public MenuFrame getMainMenu(){
        return mainMenu;
    }
    
    public Lang_BaseClass getLanguage(){
        return lang;
    }
    
    public static void main(String[] args){
        lang = new Lang_EN();
        
        System.out.println(lang.getLaunch());
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
