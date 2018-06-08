/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.graphics.ResolutionDialog;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.lang.LangDialog;
import ca.gamemaking.asteroid.menu.MenuFrame;

/**
 *
 * @author mlajoie
 */
public class Launcher {
    
    static MenuFrame mainMenu;
    static Lang lang;
    static Resolution res;
    
    public static MenuFrame getMainMenu(){
        return mainMenu;
    }
    
    public static Lang getLanguage(){
        return lang;
    }
    
    public static Resolution getResolution(){
        return res;
    }
    
    public static void main(String[] args){
        
        ResolutionDialog resDialog = new ResolutionDialog(null, "Resolution");
        res = resDialog.getValue();
        
        LangDialog lcDialog = new LangDialog(null, "Lang");
        lang = new Lang(lcDialog.getValue());
        
        System.out.println(lang.getText("launch"));
        
        mainMenu = new MenuFrame();
        mainMenu.run();
    }
    
}
