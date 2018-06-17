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
    
    public static BufferedImage TITLE_IMG;
    static {
        try {
            
            TITLE_IMG = ImageIO.read(ImageLoader.class.getResourceAsStream("title.png"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
