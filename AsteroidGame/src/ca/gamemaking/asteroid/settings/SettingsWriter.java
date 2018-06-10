/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mlajoie
 */
public class SettingsWriter {
    
    public static void Write(String path){
        
        List<String> settings = new ArrayList();
        
        try {
            File settingsFile = new File(path + Settings.FILENAME);
            
            ObjectMapper objMap = new ObjectMapper();
            
            settings.add(Settings.LANGUAGE.toString());
            settings.add(Settings.RESOLUTION.toString());
            settings.add(Settings.CONTROLS.toString());
            
            objMap.writeValue(settingsFile, settings);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error writing the settings file");
            e.printStackTrace();
        }
        
    }
    
}
