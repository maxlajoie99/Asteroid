/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings.controls;

import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author maxla
 */
public class InputDialog extends JDialog{
    public InputDialog(Frame f, String title){
        super(f,title,true);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(null);
        this.toFront();
        this.setSize(400,80);
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        initUI();
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
            }
        }
        );
        
        this.setVisible(true);
    }
    
    private void initUI(){
        
        JPanel pane = new JPanel();
        
        JLabel text = new JLabel(Settings.LANGUAGE.getText("input"), JLabel.CENTER);
        text.setFont(new Font(text.getFont().getFamily(),Font.BOLD,20));
        pane.add(text);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
}
