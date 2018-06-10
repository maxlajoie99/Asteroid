/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.json.ResolutionReaderJSON;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author maxla
 */
public class SettingsDialog extends JFrame{
    
    float scale;
    Container contentPane;
    
    JButton btnApply;
    JButton btnCancel;
    
    int sizeX;
    int sizeY;
    
    public SettingsDialog(JFrame parent, String title, float scale){
        this.pack();
        this.setTitle(title);
        
        this.scale = scale;
        contentPane = this.getContentPane();
        
        sizeX = Settings.RESOLUTION.getX() - (int)(350*scale);
        sizeY = Settings.RESOLUTION.getY() - (int)(200*scale);
        
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
        
        initOptions();
        initResolution();
        initLang();
        initControls();
        
        this.setVisible(true);
    }
    
    private void initOptions(){
        int btnWidth = (int)(125 * scale);
        int btnHeight = (int)(30 * scale);
        int offset = (int)(5 * scale);
        
        btnApply = new JButton(Settings.LANGUAGE.getText("apply"));
        btnApply.setBounds(sizeX - btnWidth*2 - offset*2 - this.getInsets().right - this.getInsets().left,
                           sizeY - btnHeight - offset - this.getInsets().top - this.getInsets().bottom,
                           btnWidth, btnHeight);
        btnApply.setEnabled(false);
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        btnCancel = new JButton(Settings.LANGUAGE.getText("cancel"));
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
    
    private void initResolution(){
        int heightSixth = sizeY/6  - this.getInsets().top - this.getInsets().bottom;
        int labelWidth = (int)(150 * scale);
        int posX = (int)(25 * scale);
        
        JLabel resText = new JLabel(Settings.LANGUAGE.getText("resolution") + ":");
        int textSize = resText.getFont().getSize()*2;
        resText.setFont(new Font(resText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        resText.setBounds(posX, heightSixth, labelWidth, textSize);
        
        JComboBox cbRes = new JComboBox(ResolutionReaderJSON.Read(Settings.SETTINGSDIR).toArray());
        cbRes.setBounds(resText.getLocation().x + labelWidth, heightSixth, labelWidth, resText.getSize().height);
        
        int index = -1;
        for(int i = 0; i < cbRes.getItemCount();i++)
        {
            if (cbRes.getItemAt(i).toString() == null ? Settings.RESOLUTION.toString() == null : cbRes.getItemAt(i).toString().equals(Settings.RESOLUTION.toString())){
                index = i;
                break;
            }
        }
        if (index != -1)
            cbRes.setSelectedIndex(index);
        else{
            cbRes.addItem(Settings.RESOLUTION);
            cbRes.setSelectedIndex(cbRes.getItemCount() - 1);
        }
        
        /*cbRes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cbRes.getSelectedItem().toString() == null ? Settings.RESOLUTION.toString() == null : cbRes.getSelectedItem().toString().equals(Settings.RESOLUTION.toString()))
                    btnApply.setEnabled(false);
                else
                    btnApply.setEnabled(true);
            }
        });*/
        
        contentPane.add(resText);
        contentPane.add(cbRes);
    }
    
    private void initLang(){
        
    }
    
    private void initControls(){
        
    }
}
