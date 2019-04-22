/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics.json;

import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.settings.Settings;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Maxime Lajoie
 */
public class ResolutionReaderJSON {
    public static List<Resolution> read(String path) {
        List<Resolution> resList = new ArrayList<>();
        
        try {
            File resFile = new File(path + ResolutionWriterJSON.FILENAME);
            
            if (resFile.exists()) {
                resList = Settings.OBJECT_MAPPER.readValue(resFile, new TypeReference<List<Resolution>>() {});
            } else {
                ResolutionWriterJSON.write(path);
                resList = read(path);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error reading the resolutions file, " + e.getMessage());
        }
        
        return resList;
    }
}
