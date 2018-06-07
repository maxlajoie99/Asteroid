/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang;

import ca.gamemaking.asteroid.lang.json.LangReaderJSON;
import java.util.Map;

/**
 *
 * @author mlajoie
 */
public class Lang {
    
    Map<String, String> fields;
    
    public Lang(String name){
        fields = LangReaderJSON.ReadFile(name);
    }
    
    public String getText(String text){
        return null;
    }
    
}
