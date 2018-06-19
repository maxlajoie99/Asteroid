/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maxime Lajoie
 */
public class DefaultResolutions {
    
    public static List<Resolution> getResolutions(){
        
        List<Resolution> resList = new ArrayList<>();
        
        resList.add(new Resolution(800, 600));
        resList.add(new Resolution(1024, 768));
        resList.add(new Resolution(1280, 720));
        resList.add(new Resolution(1360, 768));
        resList.add(new Resolution(1440, 900));
        resList.add(new Resolution(1600, 900));
        resList.add(new Resolution(1680, 1050));
        resList.add(new Resolution(1920, 1080));
        
        return resList;
    }
    
}
