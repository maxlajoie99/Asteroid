/*
 * Copyright © Maxime Lajoie - 2018 - All right reserved
 */
package ca.gamemaking.asteroid.lang;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mlajoie
 */
public class LangChooserDialog extends JDialog{
    
    private JComboBox cbLangs;
    private String[] langs = {"English", "French"};
    
    private Lang_BaseClass value;
    
    public LangChooserDialog(Frame f, String title){
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
        
        cbLangs = new JComboBox(langs);
        pane.add(cbLangs);
        
        JButton btn = new JButton("Confirm");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(cbLangs.getSelectedItem().toString()){
                    case "French":
                        value = new Lang_FR();
                        break;
                    case "English":
                    default:
                        value = new Lang_EN();
                        break;
                }
                
                LangChooserDialog.this.setVisible(false);
            }
        });
        pane.add(btn);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
    
    public Lang_BaseClass getValue(){
        return value;
    }
    
}
