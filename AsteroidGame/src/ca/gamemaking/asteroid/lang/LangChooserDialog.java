/*
 * Copyright © Maxime Lajoie - 2018 - All right reserved
 */
package ca.gamemaking.asteroid.lang;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author mlajoie
 */
public class LangChooserDialog extends JDialog{
    
    public LangChooserDialog(Frame f, String title){
        super(f,title,true);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.toFront();
        this.setSize(400,300);
        this.setLocationRelativeTo(f);
        this.setVisible(true);
        
        
    }
    
    public Lang_BaseClass getValue(){
        return new Lang_EN();
    }
    
}
