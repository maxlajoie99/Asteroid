/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.main;

import ca.gamemaking.asteroid.lang.LangChooserDialog;
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
    
    public static MenuFrame getMainMenu(){
        return mainMenu;
    }
    
    public static Lang_BaseClass getLanguage(){
        return lang;
    }
    
    public static void main(String[] args){
        
        LangChooserDialog lcDialog = new LangChooserDialog(null, "Lang");
        
        lang = lcDialog.getValue();
        if (lang == null)
            lang = new Lang_EN();
        
        System.out.println(lang.getLaunch());
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
