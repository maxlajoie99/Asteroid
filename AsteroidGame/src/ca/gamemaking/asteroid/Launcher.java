/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid;

import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.lang.LangChooserDialog;
import ca.gamemaking.asteroid.menu.MenuFrame;

/**
 *
 * @author mlajoie
 */
public class Launcher {
    
    static MenuFrame mainMenu;
    static Lang lang;
    
    public static MenuFrame getMainMenu(){
        return mainMenu;
    }
    
    public static Lang getLanguage(){
        return lang;
    }
    
    public static void main(String[] args){
        
        LangChooserDialog lcDialog = new LangChooserDialog(null, "Lang");
        
        lang = new Lang(lcDialog.getValue());
        
        System.out.println(lang.getText("launch"));
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
