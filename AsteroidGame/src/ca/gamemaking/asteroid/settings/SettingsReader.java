/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.settings.controls.Controls;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maxime Lajoie
 */
public class SettingsReader {
    public static boolean read(String path) {
        List<String> tempList;
        String tempLang;
        String tempResolution;
        String tempControls;
        
        try {
            File settingsFile = new File(path + Settings.FILENAME);

            tempList = Settings.OBJECT_MAPPER.readValue(settingsFile, new TypeReference<List<String>>() {});
            
            tempLang = tempList.get(0);
            tempResolution = tempList.get(1);
            tempControls = tempList.get(2);
            
            System.out.println(tempLang);
            System.out.println(tempResolution);
            System.out.println(tempControls);
            
            if (tempLang == null || tempResolution == null || tempControls == null) {
                return false;
            }

            Settings.LANGUAGE = new Lang(tempLang);
            Settings.RESOLUTION = new Resolution(tempResolution);
            Settings.CONTROLS = new Controls(tempControls);
            
            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        
        return false;
    }
}
