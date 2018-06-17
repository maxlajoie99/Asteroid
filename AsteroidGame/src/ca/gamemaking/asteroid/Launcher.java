/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.graphics.ResolutionDialog;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.lang.LangDialog;
import ca.gamemaking.asteroid.game.GameFrame;
import ca.gamemaking.asteroid.settings.Settings;
import ca.gamemaking.asteroid.settings.SettingsReader;
import ca.gamemaking.asteroid.settings.SettingsWriter;
import ca.gamemaking.asteroid.settings.controls.Controls;

/**
 *
 * @author mlajoie
 */
public class Launcher {
    
    static GameFrame gameFrame;
    static Lang lang;
    static Resolution res;
    
    public static GameFrame getGameFrame(){
        return gameFrame;
    }
    
    public static void main(String[] args){
        
        boolean exists = SettingsReader.Read(Settings.SETTINGSPATH);
        
        if (!exists){
            ResolutionDialog resDialog = new ResolutionDialog(null, "Resolution");
            res = resDialog.getValue();
            res.Adjust();
            Settings.RESOLUTION = res;
        
            LangDialog lcDialog = new LangDialog(null, "Lang");
            lang = new Lang(lcDialog.getValue());
            Settings.LANGUAGE = lang;
            
            Controls ctrls = new Controls();
            Settings.CONTROLS = ctrls;
            
            SettingsWriter.Write(Settings.SETTINGSPATH);
        }
        else {
            Settings.RESOLUTION.Adjust();
        }
        
        System.out.println(Settings.LANGUAGE.getText("launch"));
        
        gameFrame = new GameFrame();
        gameFrame.run();
    }
    
}
