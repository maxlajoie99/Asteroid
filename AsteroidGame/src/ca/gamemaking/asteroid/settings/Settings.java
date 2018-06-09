/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.lang.Lang;
import java.io.File;

/**
 *
 * @author mlajoie
 */
public class Settings {
    
    public static String GAMEMAKINGDIR = System.getProperty("user.home") + File.separator + ".gamemaking";
    public static String SETTINGSDIR = GAMEMAKINGDIR + File.separator + "asteroid";
    public static String SETTINGSPATH = SETTINGSDIR + File.separator;
    public static String FILENAME = "Settings.json";
    
    public static Lang LANGUAGE;
    public static Resolution RESOLUTION;
    
}
