/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics.json;

import ca.gamemaking.asteroid.graphics.DefaultResolutions;
import ca.gamemaking.asteroid.settings.Settings;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Maxime Lajoie
 */
public class ResolutionWriterJSON {
    public static final String FILENAME = "Resolutions.json";
    
    public static void write(String path) {
        try {
            File gameDir = new File(Settings.GAMEMAKING_DIR);
            if(!gameDir.exists()) {
                gameDir.mkdir();
            }

            File settingsDir = new File(Settings.SETTINGS_DIR);
            if(!settingsDir.exists()) {
                settingsDir.mkdir();
            }

            Settings.OBJECT_MAPPER.writeValue(new File(path + FILENAME), DefaultResolutions.DEFAULT_RESOLUTIONS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error writing the resolutions file. Exiting...");
            System.exit(0);
        }
    }
}
