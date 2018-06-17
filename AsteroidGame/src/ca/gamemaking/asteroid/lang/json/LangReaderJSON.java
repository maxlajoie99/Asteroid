/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author mlajoie
 */
public class LangReaderJSON {
    
    public static HashMap<String,String> ReadFile(String langName){
        HashMap<String,String> langMap = null;
        try {
            
            langMap = new ObjectMapper().readValue(LangReaderJSON.class.getResourceAsStream(langName + ".json"), new TypeReference<HashMap<String,String>>() {});
            
        } catch (Exception e) {
            if (!"English".equals(langName)){
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
