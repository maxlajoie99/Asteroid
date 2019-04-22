/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings;

import ca.gamemaking.asteroid.Launcher;
import ca.gamemaking.asteroid.graphics.Resolution;
import ca.gamemaking.asteroid.graphics.json.ResolutionReaderJSON;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.lang.LangDialog;
import ca.gamemaking.asteroid.settings.controls.Controls;
import ca.gamemaking.asteroid.settings.controls.InputDialog;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Maxime Lajoie
 */
public class SettingsFrame extends JFrame {
    private Container contentPane;

    private JButton btnApply;
    private JButton btnCancel;
    
    private JComboBox<Resolution> cboxResolutions;
    private JComboBox<String> cboxLangs;
    
    private JButton btnForward;
    private JButton btnRight;
    private JButton btnLeft;
    private JButton btnShoot;
    
    private int sizeX;
    private int sizeY;
    
    private int baseWidth = 200;
    
    private boolean requireRestart;
    
    public static Controls tempControls;
    
    public SettingsFrame(JFrame parent, String title){
        this.pack();
        this.setTitle(title);
        
        tempControls = new Controls(Settings.CONTROLS);
        requireRestart = false;
        
        contentPane = this.getContentPane();
        
        sizeX = Settings.RESOLUTION.getX() - (int)(350 * Settings.SCALE);
        sizeY = Settings.RESOLUTION.getY() - (int)(200 * Settings.SCALE);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(sizeX, sizeY);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                parent.setEnabled(false);
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
                parent.setEnabled(true);
                parent.toFront();
                parent.requestFocus();
            }
        });
        
        InitOptions();
        InitResolution();
        initLang();
        InitControls();
        
        this.setVisible(true);
    }
    
    private void InitOptions() {
        int btnWidth = (int)(130 * Settings.SCALE);
        int btnHeight = (int)(30 * Settings.SCALE);
        int offset = (int)(5 * Settings.SCALE);
        
        btnApply = new JButton(Settings.LANGUAGE.getText("apply"));
        btnApply.setBounds(sizeX - btnWidth * 2 - offset * 2 - this.getInsets().right - this.getInsets().left,
                           sizeY - btnHeight - offset - this.getInsets().top - this.getInsets().bottom,
                           btnWidth, btnHeight);
        btnApply.setEnabled(false);
        btnApply.addActionListener(e -> ApplyChange());
        
        btnCancel = new JButton(Settings.LANGUAGE.getText("cancel"));
        btnCancel.setBounds(sizeX - btnWidth - offset - this.getInsets().right - this.getInsets().left,
                            sizeY - btnHeight - offset - this.getInsets().top - this.getInsets().bottom,
                            btnWidth, btnHeight);
        btnCancel.addActionListener(e -> dispose());
        
        contentPane.add(btnApply);
        contentPane.add(btnCancel);
    }
    
    private void initLang() {
        int heightSixth = sizeY/6  - this.getInsets().top - this.getInsets().bottom;
        int labelWidth = (int)(baseWidth * Settings.SCALE);
        int posX = sizeX/2 + (int)(25 * Settings.SCALE);
        
        JLabel langText = new JLabel(Settings.LANGUAGE.getText("lang") + ":");
        int textSize = (int)(langText.getFont().getSize() * Settings.SCALE * 2);
        langText.setFont(new Font(langText.getFont().getFamily(), Font.BOLD, textSize));
        langText.setBounds(posX, heightSixth, labelWidth, textSize);
        
        cboxLangs = new JComboBox<String>(new Vector<String>(LangDialog.getLangs()));
        cboxLangs.setBounds(langText.getLocation().x + labelWidth, heightSixth - textSize/4, labelWidth, (int)(textSize * 1.5));
        cboxLangs.setSelectedItem(Settings.LANGUAGE.toString());
        
        cboxLangs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                BtnApplyEnabled();
            }
        });
        
        contentPane.add(langText);
        contentPane.add(cboxLangs);
    }
    
    private void InitResolution(){
        int heightSixth = sizeY / 6  - this.getInsets().top - this.getInsets().bottom;
        int labelWidth = (int)(baseWidth * Settings.SCALE);
        int posX = (int)(25 * Settings.SCALE);
        
        JLabel resText = new JLabel(Settings.LANGUAGE.getText("resolution") + ":");
        int textSize = (int)(resText.getFont().getSize() * Settings.SCALE * 2);
        resText.setFont(new Font(resText.getFont().getFamily(), Font.BOLD, textSize));
        resText.setBounds(posX, heightSixth, labelWidth, textSize);
        
        cboxResolutions = new JComboBox<>(new Vector<>(ResolutionReaderJSON.read(Settings.SETTINGS_DIR)));
        cboxResolutions.setBounds(resText.getLocation().x + labelWidth,
                                  heightSixth - textSize / 4, labelWidth, (int)(textSize * 1.5));
        
        int index = -1;
        for (int i = 0; i < cboxResolutions.getItemCount(); i++) {
            if (cboxResolutions.getItemAt(i).toString() == null ? Settings.RESOLUTION.toString() == null : cboxResolutions.getItemAt(i).toString().equals(Settings.RESOLUTION.toString())){
                index = i;
                break;
            }
        }

        if (index != -1) {
            cboxResolutions.setSelectedIndex(index);
        } else {
            cboxResolutions.addItem(Settings.RESOLUTION);
            cboxResolutions.setSelectedIndex(cboxResolutions.getItemCount() - 1);
        }
        
        cboxResolutions.addItemListener(e -> BtnApplyEnabled());
        
        contentPane.add(resText);
        contentPane.add(cboxResolutions);
    }
    
    private void InitControls() {
        int heightSixth = sizeY / 6;
        int labelWidth = (int)(baseWidth * Settings.SCALE);
        int posX1 = (int)(25 * Settings.SCALE);
        int posX2 = sizeX / 2 + (int)(25 * Settings.SCALE);
        
        JLabel rightText = new JLabel(Settings.LANGUAGE.getText("right") + ":");
        int textSize = (int)(rightText.getFont().getSize() * Settings.SCALE * 2);
        rightText.setFont(new Font(rightText.getFont().getFamily(), Font.BOLD, textSize));
        rightText.setBounds(posX1, heightSixth * 3 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnRight = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getTURN_RIGHT()));
        btnRight.setName("R");
        btnRight.setBounds(posX1 + labelWidth, heightSixth * 3 - textSize / 2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize * 2);
        btnRight.addActionListener(this::ActionPerformed);
        contentPane.add(rightText);
        contentPane.add(btnRight);
        
        JLabel leftText = new JLabel(Settings.LANGUAGE.getText("left") + ":");
        leftText.setFont(new Font(leftText.getFont().getFamily(), Font.BOLD, textSize));
        leftText.setBounds(posX1, heightSixth * 4 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnLeft = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getTURN_LEFT()));
        btnLeft.setName("L");
        btnLeft.setBounds(posX1 + labelWidth, heightSixth * 4 - textSize / 2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize * 2);
        btnLeft.addActionListener(this::ActionPerformed);
        contentPane.add(leftText);
        contentPane.add(btnLeft);
        
        JLabel forwardText = new JLabel(Settings.LANGUAGE.getText("forward") + ":");
        forwardText.setFont(new Font(forwardText.getFont().getFamily(), Font.BOLD, textSize));
        forwardText.setBounds(posX2, heightSixth * 3 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnForward = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getFORWARD()));
        btnForward.setName("F");
        btnForward.setBounds(posX2 + labelWidth, heightSixth * 3 - textSize / 2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize * 2);
        btnForward.addActionListener(this::ActionPerformed);
        contentPane.add(forwardText);
        contentPane.add(btnForward);
        
        JLabel shootText = new JLabel(Settings.LANGUAGE.getText("shoot") + ":");
        shootText.setFont(new Font(shootText.getFont().getFamily(), Font.BOLD, textSize));
        shootText.setBounds(posX2, heightSixth * 4 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize);
        btnShoot = new JButton(KeyEvent.getKeyText(Settings.CONTROLS.getSHOOT()));
        btnShoot.setName("S");
        btnShoot.setBounds(posX2 + labelWidth, heightSixth * 4 - textSize / 2 - this.getInsets().top - this.getInsets().bottom, labelWidth, textSize * 2);
        btnShoot.addActionListener(this::ActionPerformed);
        contentPane.add(shootText);
        contentPane.add(btnShoot);
    }
    
    private void ActionPerformed(ActionEvent e){
        JButton temp = (JButton) e.getSource();
        InputDialog id = new InputDialog(null, Settings.LANGUAGE.getText("confKey"), temp);

        BtnApplyEnabled();
    }
    
    private void BtnApplyEnabled(){
        if ((cboxResolutions.getSelectedItem() == null ? Settings.RESOLUTION.toString() != null : !cboxResolutions.getSelectedItem().toString().equals(Settings.RESOLUTION.toString()))
                || (cboxLangs.getSelectedItem() == null ? Settings.LANGUAGE.toString() != null : !cboxLangs.getSelectedItem().toString().equals(Settings.LANGUAGE.toString()))
                || tempControls.getFORWARD() != Settings.CONTROLS.getFORWARD()
                || tempControls.getTURN_RIGHT() != Settings.CONTROLS.getTURN_RIGHT()
                || tempControls.getTURN_LEFT() != Settings.CONTROLS.getTURN_LEFT()
                || tempControls.getSHOOT() != Settings.CONTROLS.getSHOOT()) {
            if((cboxResolutions.getSelectedItem() == null ? Settings.RESOLUTION.toString() != null : !cboxResolutions.getSelectedItem().toString().equals(Settings.RESOLUTION.toString()))
                    || (cboxLangs.getSelectedItem() == null ? Settings.LANGUAGE.toString() != null : !cboxLangs.getSelectedItem().toString().equals(Settings.LANGUAGE.toString()))) {
                requireRestart = true;
            }

            btnApply.setEnabled(true);
        } else {
            btnApply.setEnabled(false);
        }
    }
    
    private void ApplyChange(){
        SettingsWriter.rewrite(Settings.SETTINGS_PATH, new Lang((String) cboxLangs.getSelectedItem()), (Resolution) cboxResolutions.getSelectedItem(), tempControls);
        Settings.CONTROLS = tempControls;
        
        if(requireRestart) {
            if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, Settings.LANGUAGE.getText("restart") + Settings.LANGUAGE.getText("restart2"), Settings.LANGUAGE.getText("restartTitle"), JOptionPane.YES_NO_OPTION)) {
                Launcher.getGameFrame().dispose();
            }
        }

        this.dispose();
    }
}