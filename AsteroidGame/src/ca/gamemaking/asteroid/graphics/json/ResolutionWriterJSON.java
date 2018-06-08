/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics.json;

import ca.gamemaking.asteroid.graphics.BaseResolutions;
import ca.gamemaking.asteroid.settings.Settings;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author mlajoie
 */
public class ResolutionWriterJSON {
    
    public static String FILENAME = "Resolutions.json";
    
    public static void Write(String path){
        
        try {
            
            File gameDir = new File(Settings.GAMEMAKINGDIR);
            if(!gameDir.exists())
                gameDir.mkdir();
            
            File settingsDir = new File(Settings.SETTINGSDIR);
            if(!settingsDir.exists())
                settingsDir.mkdir();
            
            ObjectMapper objmap = new ObjectMapper();
            objmap.writeValue(new File(path + FILENAME), BaseResolutions.getResolutions());
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "There was an error writing the resolutions file. Exiting...");
            System.exit(0);
        }
        
    }
    
}
