/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.lang;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class LangDialog extends JDialog{
    
    private JComboBox cbLangs;
    
    private String value;
    
    public LangDialog(Frame f, String title){
        super(f,title,true);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.toFront();
        this.setSize(500,100);
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        initUI();
        
        this.setVisible(true);
    }
    
    private void initUI(){
        
        JPanel pane = new JPanel();
        
        JLabel text = new JLabel("Please choose a language from the list before continuing...");
        pane.add(text);
        
        cbLangs = new JComboBox(getLangs().toArray());
        pane.add(cbLangs);
        
        JButton btn = new JButton("Confirm");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                value = cbLangs.getSelectedItem().toString();
                
                LangDialog.this.setVisible(false);
            }
        });
        pane.add(btn);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
    
    public static List<String> getLangs(){
        
        List<String> list = new ArrayList();
        
        list.add("English");
        list.add("Français");
        
        return list;
    }
    
    public String getValue(){
        if (value != null)
            return value;
        else
            return cbLangs.getSelectedItem().toString();
    }
    
}
