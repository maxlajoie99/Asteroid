/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics.json;

import ca.gamemaking.asteroid.graphics.Resolution;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Maxime Lajoie
 */
public class ResolutionReaderJSON {
    
    public static List<Resolution> Read(String path){
        List<Resolution> resList = new ArrayList<>();
        
        try {
            File resFile = new File(path + ResolutionWriterJSON.FILENAME);
            
            if(resFile.exists()){
                ObjectMapper objmap = new ObjectMapper();
            
                resList = objmap.readValue(resFile, new TypeReference<List<Resolution>>() {});
            }
            else {
                ResolutionWriterJSON.Write(path);
                resList = Read(path);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error reading the resolutions file, " + e.getMessage());
        }
        
        return resList;
    }
    
}
