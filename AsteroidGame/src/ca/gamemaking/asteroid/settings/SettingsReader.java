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
 * @author mlajoie
 */
public class SettingsReader {
    
    public static boolean Read(String path){
        
        List<String> tempList = new ArrayList<>();
        String tempLang;
        String tempRes;
        String tempControls;
        
        try {
            File settingsFile = new File(path + Settings.FILENAME);
            
            ObjectMapper objMap = new ObjectMapper();
            
            tempList = objMap.readValue(settingsFile, new TypeReference<List<String>>(){});
            
            tempLang = (tempList.get(0) instanceof String) ? (String) tempList.get(0) : null;
            tempRes = (tempList.get(1) instanceof String) ? (String) tempList.get(1) : null;
            tempControls = (tempList.get(2) instanceof String) ? (String) tempList.get(2) : null;
            
            System.out.println(tempLang);
            System.out.println(tempRes);
            System.out.println(tempControls);
            
            if (tempLang == null || tempRes == null || tempControls == null)
                return false;
            
            Settings.LANGUAGE = new Lang(tempLang);
            Settings.RESOLUTION = new Resolution(tempRes);
            Settings.CONTROLS = new Controls(tempControls);
            
            return true;
            
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        
        return false;
    }
    
}
