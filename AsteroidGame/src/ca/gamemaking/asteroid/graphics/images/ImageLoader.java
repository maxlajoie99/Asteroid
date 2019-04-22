/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics.images;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Maxime Lajoie
 */
public class ImageLoader {
    public static final BufferedImage TITLE_IMG = LoadImage("title.png");

    private static BufferedImage LoadImage(String name) {
        try {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(name));
        } catch (Exception e) {
            return null;
        }
    }
}
