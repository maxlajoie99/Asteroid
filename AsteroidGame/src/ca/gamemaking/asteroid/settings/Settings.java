/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import java.io.File;

/**
 *
 * @author mlajoie
 */
public class Settings {
    
    public static String GAMEMAKINGDIR = System.getProperty("user.home") + File.separator + ".gamemaking";
    public static String SETTINGSDIR = GAMEMAKINGDIR + File.separator + "asteroid";
    public static String SETTINGSPATH = SETTINGSDIR + File.separator;
    
}
