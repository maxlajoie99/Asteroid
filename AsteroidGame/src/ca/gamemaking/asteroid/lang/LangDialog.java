/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Maxime Lajoie
 */
public class LangDialog extends JDialog {
    private JComboBox<String> cboxLangs;
    private String selectedLanguage;
    
    public LangDialog(Frame f, String title) {
        super(f,title,true);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.toFront();
        this.setSize(500,100);
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        initUI();
        
        this.setVisible(true);
    }
    
    private void initUI() {
        JPanel pane = new JPanel();
        
        JLabel text = new JLabel("Please choose a language from the list before continuing...");
        pane.add(text);
        
        cboxLangs = new JComboBox<>(new Vector<>(getLangs()));
        pane.add(cboxLangs);
        
        JButton btn = new JButton("Confirm");
        btn.addActionListener(e -> {
            selectedLanguage = (String) cboxLangs.getSelectedItem();
            LangDialog.this.setVisible(false);
        });
        pane.add(btn);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
    
    public static List<String> getLangs() {
        List<String> list = new ArrayList<>();
        
        list.add("English");
        list.add("Français");
        
        return list;
    }
    
    public String getValue() {
        return selectedLanguage != null ? selectedLanguage : (String) cboxLangs.getSelectedItem();
    }
}
