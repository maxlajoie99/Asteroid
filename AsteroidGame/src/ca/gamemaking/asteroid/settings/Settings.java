/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.settings.controls.Controls;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

/**
 *
 * @author Maxime Lajoie
 */
public class Settings {
    public static final String GAMEMAKING_DIR = System.getProperty("user.home") + File.separator + ".gamemaking";
    public static final String SETTINGS_DIR = GAMEMAKING_DIR + File.separator + "asteroid";
    public static final String SETTINGS_PATH = SETTINGS_DIR + File.separator;
    public static final String FILENAME = "Settings.json";
    
    public static Lang LANGUAGE;
    public static Resolution RESOLUTION;
    public static Controls CONTROLS;

    public static int TARGET_FPS = 60;
    public static float SCALE;

    public static final int DEFAULT_NB_LIVES = 5;
    public static final int DEFAULT_NB_POINTS_FOR_NEW_LIFE = 10000;

    public static final Random RANDOM = new Random();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final DecimalFormat POINT_FORMAT;
    static {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(' ');
        POINT_FORMAT = new DecimalFormat("000,000,000,000", dfs);
    }
}
