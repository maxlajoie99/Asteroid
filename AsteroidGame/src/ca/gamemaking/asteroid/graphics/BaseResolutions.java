/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mlajoie
 */
public class BaseResolutions {
    
    public static List<Resolution> getResolutions(){
        
        List<Resolution> resList = new ArrayList();
        
        resList.add(new Resolution(1280, 720));
        resList.add(new Resolution(1366, 768));
        resList.add(new Resolution(1600, 900));
        resList.add(new Resolution(1920, 1080));
        
        return resList;
    }
    
}
