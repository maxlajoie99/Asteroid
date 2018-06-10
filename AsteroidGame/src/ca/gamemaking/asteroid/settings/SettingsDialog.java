/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author maxla
 */
public class SettingsDialog extends JDialog{
    
    public SettingsDialog(Frame f, String title, float scale){
        super(f,title,true);
        
        this.toFront();
        this.setSize(Settings.RESOLUTION.getX() - (int)(150*scale),Settings.RESOLUTION.getY() - (int)(100*scale));
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        initUI();
        
        this.setVisible(true);
    }
    
    private void initUI(){
        
    }
}
