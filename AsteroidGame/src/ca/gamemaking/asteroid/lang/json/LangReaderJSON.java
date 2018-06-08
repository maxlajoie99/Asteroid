/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author mlajoie
 */
public class LangReaderJSON {
    
    public static Map<String,String> ReadFile(String langName){
        Map<String,String> langMap = new HashMap<String, String>();
        try {
            
            langMap = new ObjectMapper().readValue(LangReaderJSON.class.getResourceAsStream(langName + ".json"), HashMap.class);
            
        } catch (Exception e) {
            if (langName != "English"){
                JOptionPane.showMessageDialog(null, "This lang doesn't exist, reverting to English");
                langMap = ReadFile("English");
            }
            else {
                JOptionPane.showMessageDialog(null, "Default language not available, exiting");
                System.exit(0);
            }
        }
        
        return langMap;
    }
    
}
