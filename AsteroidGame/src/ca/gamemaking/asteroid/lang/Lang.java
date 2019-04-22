/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang;

import ca.gamemaking.asteroid.lang.json.LangReaderJSON;
import java.util.HashMap;

/**
 *
 * @author Maxime Lajoie
 */
public class Lang {
    private HashMap<String, String> fields;
    private String name;
    
    public Lang(String name) {
        this.name = name;
        fields = LangReaderJSON.readFile(name);
    }
    
    public String getText(String text) {
        return fields.get(text);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
