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
    public static final List<Resolution> DEFAULT_RESOLUTIONS = new ArrayList<>();
    static {
        DEFAULT_RESOLUTIONS.add(new Resolution(800, 600));
        DEFAULT_RESOLUTIONS.add(new Resolution(1024, 768));
        DEFAULT_RESOLUTIONS.add(new Resolution(1280, 720));
        DEFAULT_RESOLUTIONS.add(new Resolution(1360, 768));
        DEFAULT_RESOLUTIONS.add(new Resolution(1440, 900));
        DEFAULT_RESOLUTIONS.add(new Resolution(1600, 900));
        DEFAULT_RESOLUTIONS.add(new Resolution(1680, 1050));
        DEFAULT_RESOLUTIONS.add(new Resolution(1920, 1080));
    }
}
