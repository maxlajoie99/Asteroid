/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mlajoie
 */
public class LangReaderJSON {
    
    public static Map<String,String> ReadFile(String name){
        Map<String,String> file = new HashMap<String, String>();
        try {
            
            file = new ObjectMapper().readValue(LangReaderJSON.class.getResourceAsStream(name + ".json"), HashMap.class);
            
        } catch (Exception e) {
            
        }
        
        return file;
    }
    
}
