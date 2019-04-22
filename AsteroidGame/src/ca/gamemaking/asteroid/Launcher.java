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
 * @author Maxime Lajoie
 */
public class Launcher {
    private static GameFrame gameFrame;
    
    public static void main(String[] args){
        boolean exists = SettingsReader.read(Settings.SETTINGS_PATH);
        
        if (!exists) {
            ResolutionDialog resDialog = new ResolutionDialog(null, "Resolution");
            Settings.RESOLUTION = resDialog.getValue().adjust();

            LangDialog lcDialog = new LangDialog(null, "Lang");
            Settings.LANGUAGE = new Lang(lcDialog.getValue());

            Settings.CONTROLS = new Controls();
            
            SettingsWriter.write(Settings.SETTINGS_PATH);
        } else {
            Settings.RESOLUTION.adjust();
        }
        
        System.out.println(Settings.LANGUAGE.getText("launch"));
        
        gameFrame = new GameFrame();
        gameFrame.run();
    }

    public static GameFrame getGameFrame(){
        return gameFrame;
    }
}
