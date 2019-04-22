/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.settings.controls.Controls;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Maxime Lajoie
 */
public class SettingsWriter {
    public static void write(String path) {
        List<String> settings = new ArrayList<>();
        settings.add(Settings.LANGUAGE.toString());
        settings.add(Settings.RESOLUTION.toString());
        settings.add(Settings.CONTROLS.toString());

        File settingsFile = new File(path + Settings.FILENAME);

        try {
            Settings.OBJECT_MAPPER.writeValue(settingsFile, settings);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error writing the settings file");
        }
    }
    
    public static void rewrite(String path, Lang lang, Resolution res, Controls ctrls){
        List<String> settings = new ArrayList<>();
        settings.add(lang.toString());
        settings.add(res.toString());
        settings.add(ctrls.toString());

        File settingsFile = new File(path + Settings.FILENAME);

        try {
            Settings.OBJECT_MAPPER.writeValue(settingsFile, settings);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error writing the settings file");
        }
    }
}