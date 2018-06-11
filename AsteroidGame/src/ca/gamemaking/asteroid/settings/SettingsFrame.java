/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.graphics.json.ResolutionReaderJSON;
import ca.gamemaking.asteroid.lang.LangDialog;
import ca.gamemaking.asteroid.settings.controls.Controls;
import ca.gamemaking.asteroid.settings.controls.InputDialog;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author maxla
 */
public class SettingsFrame extends JFrame{
    
    float scale;
    Container contentPane;
    
    JButton btnApply;
    JButton btnCancel;
    
    JComboBox cbRes;
    JComboBox cbLang;
    
    JButton btnForward;
    JButton btnRight;
    JButton btnLeft;
    JButton btnShoot;
    
    int sizeX;
    int sizeY;
    
    public SettingsFrame(JFrame parent, String title, float scale){
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
        int posX = sizeX/2 + (int)(25 * scale);
        
        JLabel langText = new JLabel(Settings.LANGUAGE.getText("lang") + ":");
        int textSize = langText.getFont().getSize()*2;
        langText.setFont(new Font(langText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        langText.setBounds(posX, heightSixth, labelWidth, textSize);
        
        cbLang = new JComboBox(LangDialog.getLangs().toArray());
        cbLang.setBounds(langText.getLocation().x + labelWidth, heightSixth, labelWidth, langText.getSize().height);
        cbLang.setSelectedItem(Settings.LANGUAGE.toString());
        
        cbLang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ApplyEnabled();
            }
        });
        
        contentPane.add(langText);
        contentPane.add(cbLang);
    }
    
    private void initLang(){
        int heightSixth = sizeY/6  - this.getInsets().top - this.getInsets().bottom;
        int labelWidth = (int)(150 * scale);
        int posX = (int)(25 * scale);
        
        JLabel resText = new JLabel(Settings.LANGUAGE.getText("resolution") + ":");
        int textSize = resText.getFont().getSize()*2;
        resText.setFont(new Font(resText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        resText.setBounds(posX, heightSixth, labelWidth, textSize);
        
        cbRes = new JComboBox(ResolutionReaderJSON.Read(Settings.SETTINGSDIR).toArray());
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
        
        cbRes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ApplyEnabled();
            }
        });
        
        contentPane.add(resText);
        contentPane.add(cbRes);
    }
    
    private void initControls(){
        int heightSixth = sizeY/6;
        int labelWidth = (int)(150 * scale);
        int posX1 = (int)(25 * scale);
        int posX2 = sizeX/2 + (int)(25 * scale);
        
        JLabel rightText = new JLabel(Settings.LANGUAGE.getText("right") + ":");
        int textSize = rightText.getFont().getSize()*2;
        rightText.setFont(new Font(rightText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        rightText.setBounds(posX1, heightSixth*3 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnRight = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getTURN_RIGHT()));
        btnRight.setBounds(posX1 + labelWidth, heightSixth*3 - textSize/2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize*2);
        btnRight.addActionListener((e) -> actionPerformed(e));
        contentPane.add(rightText);
        contentPane.add(btnRight);
        
        JLabel leftText = new JLabel(Settings.LANGUAGE.getText("left") + ":");
        leftText.setFont(new Font(leftText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        leftText.setBounds(posX1, heightSixth*4 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnLeft = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getTURN_LEFT()));
        btnLeft.setBounds(posX1 + labelWidth, heightSixth*4 - textSize/2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize*2);
        btnLeft.addActionListener((e) -> actionPerformed(e));
        contentPane.add(leftText);
        contentPane.add(btnLeft);
        
        JLabel forwardText = new JLabel(Settings.LANGUAGE.getText("forward") + ":");
        forwardText.setFont(new Font(forwardText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        forwardText.setBounds(posX2, heightSixth*3 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnForward = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getFORWARD()));
        btnForward.setBounds(posX2 + labelWidth, heightSixth*3 - textSize/2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize*2);
        btnForward.addActionListener((e) -> actionPerformed(e));
        contentPane.add(forwardText);
        contentPane.add(btnForward);
        
        JLabel shootText = new JLabel(Settings.LANGUAGE.getText("shoot") + ":");
        shootText.setFont(new Font(shootText.getFont().getFamily(), Font.BOLD, (int)(textSize * scale)));
        shootText.setBounds(posX2, heightSixth*4 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnShoot = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getSHOOT()));
        btnShoot.setBounds(posX2 + labelWidth, heightSixth*4 - textSize/2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize*2);
        btnShoot.addActionListener((e) -> actionPerformed(e));
        contentPane.add(shootText);
        contentPane.add(btnShoot);
    }
    
    public void actionPerformed(ActionEvent e){
        InputDialog id = new InputDialog(null, Settings.LANGUAGE.getText("confKey"));
    }
    
    private void ApplyEnabled(){
        if ((cbRes.getSelectedItem().toString() == null ? Settings.RESOLUTION.toString() != null : !cbRes.getSelectedItem().toString().equals(Settings.RESOLUTION.toString())) 
            || (cbLang.getSelectedItem().toString() == null ? Settings.LANGUAGE.toString() != null : !cbLang.getSelectedItem().toString().equals(Settings.LANGUAGE.toString())))
            btnApply.setEnabled(true);
        else
            btnApply.setEnabled(false);
    }
}
