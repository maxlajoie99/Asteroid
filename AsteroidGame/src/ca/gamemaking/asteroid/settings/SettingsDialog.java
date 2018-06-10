/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author maxla
 */
public class SettingsDialog extends JFrame{
    
    float scale;
    Container contentPane;
    
    int sizeX;
    int sizeY;
    
    public SettingsDialog(JFrame parent, String title, float scale){
        this.pack();
        this.setTitle(title);
        
        this.scale = scale;
        contentPane = this.getContentPane();
        
        sizeX = Settings.RESOLUTION.getX() - (int)(150*scale);
        sizeY = Settings.RESOLUTION.getY() - (int)(100*scale);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(sizeX,sizeY);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e){
                parent.setEnabled(false);
            }
            
            @Override
            public void windowClosed(WindowEvent e){
                parent.setEnabled(true);
                parent.toFront();
            }
        });
        
        initUI();
        
        this.setVisible(true);
    }
    
    private void initUI(){
        int btnWidth = (int)(125 * scale);
        int btnHeight = (int)(30 * scale);
        int offset = (int)(5 * scale);
        
        JButton btnApply = new JButton(Settings.LANGUAGE.getText("apply"));
        btnApply.setBounds(sizeX - btnWidth*2 - offset*2 - this.getInsets().right - this.getInsets().left,
                           sizeY - btnHeight - offset - this.getInsets().top - this.getInsets().bottom,
                           btnWidth, btnHeight);
        btnApply.setEnabled(false);
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        JButton btnCancel = new JButton(Settings.LANGUAGE.getText("cancel"));
        btnCancel.setBounds(sizeX - btnWidth - offset - this.getInsets().right - this.getInsets().left,
                            sizeY - btnHeight - offset - this.getInsets().top - this.getInsets().bottom,
                            btnWidth, btnHeight);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        contentPane.add(btnApply);
        contentPane.add(btnCancel);
    }
}
