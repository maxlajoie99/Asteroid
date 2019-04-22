/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.graphics;

import ca.gamemaking.asteroid.graphics.json.ResolutionReaderJSON;
import ca.gamemaking.asteroid.settings.Settings;
import java.awt.BorderLayout;
import java.awt.Frame;
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
public class ResolutionDialog extends JDialog {
    private List<Resolution> resolutions;
    private JComboBox<Resolution> cboxResolutions;
    
    private Resolution selectedResolution;
    
    public ResolutionDialog(Frame f, String title) {
        super(f, title,true);

        this.toFront();
        this.setSize(300,125);
        this.setLocationRelativeTo(f);
        this.setResizable(false);
        
        resolutions = ResolutionReaderJSON.read(Settings.SETTINGS_PATH);
        
        InitUI();
        
        this.setVisible(true);
    }
    
    private void InitUI() {
        JPanel pane = new JPanel();
        
        JLabel text = new JLabel("Game resolution : ");
        pane.add(text);
        
        cboxResolutions = new JComboBox<>(new Vector<>(resolutions));
        pane.add(cboxResolutions);
        
        JLabel text2 = new JLabel("(Actual window may be smaller)");
        pane.add(text2);
        
        JButton btn = new JButton("Confirm");
        btn.addActionListener(e -> {
            selectedResolution = (Resolution) cboxResolutions.getSelectedItem();
            ResolutionDialog.this.setVisible(false);
        });
        pane.add(btn);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.CENTER);
    }
    
    public Resolution getValue() {
        return selectedResolution != null ? selectedResolution : (Resolution) cboxResolutions.getSelectedItem();
    }
    
}
