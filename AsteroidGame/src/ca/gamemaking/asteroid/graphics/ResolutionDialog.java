/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import ca.gamemaking.asteroid.graphics.json.ResolutionReaderJSON;
import ca.gamemaking.asteroid.lang.LangDialog;
import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mlajoie
 */
public class ResolutionDialog extends JDialog{
    
    private List<Resolution> resList;
    private JComboBox cbResolutions;
    
    private Resolution res;
    
    public ResolutionDialog(Frame f, String title){
        
        super(f,title,true);
        this.toFront();
        this.setSize(300,100);
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        resList = ResolutionReaderJSON.Read(Settings.SETTINGSPATH);
        
        initUI();
        
        this.setVisible(true);
    }
    
    private void initUI(){
        JPanel pane = new JPanel();
        
        JLabel text = new JLabel("Game resolution : ");
        pane.add(text);
        
        cbResolutions = new JComboBox(resList.toArray());
        pane.add(cbResolutions);
        
        JButton btn = new JButton("Confirm");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                res = (Resolution)cbResolutions.getSelectedItem();
                
                ResolutionDialog.this.setVisible(false);
            }
        });
        pane.add(btn);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
    
    public Resolution getValue(){
        return res;
    }
    
}
