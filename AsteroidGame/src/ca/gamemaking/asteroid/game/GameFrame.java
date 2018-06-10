/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game;

import ca.gamemaking.asteroid.graphics.images.ImageLoader;
import ca.gamemaking.asteroid.music.MusicLoader;
import ca.gamemaking.asteroid.settings.Settings;
import javax.swing.JFrame;
import static ca.gamemaking.asteroid.settings.Settings.*;
import ca.gamemaking.asteroid.settings.SettingsDialog;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author mlajoie
 */
public class GameFrame extends JFrame {

    Container contentPane;
    JPanel background;
    
    Random rnd;
    float scale = 1.0f;
    
    public GameFrame() {
        initComponents();
        
        scale = Settings.RESOLUTION.getX()/1280f;
        rnd = new Random();
        
        initUI();
    }
    
    private void initUI(){
        contentPane = this.getContentPane();
        contentPane.setLayout(null);
        
        background = new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                
                g.setColor(Color.WHITE);
                for (int i = 0; i < (int)(120 * scale); i++) {
                    g.fillOval(rnd.nextInt(Settings.RESOLUTION.getX()), rnd.nextInt(Settings.RESOLUTION.getY()), 5, 5);
                }
                
                int sizeX = (int)(ImageLoader.TITLE_IMG.getWidth()*scale);
                int sizeY = (int)(ImageLoader.TITLE_IMG.getHeight()*scale);
                int offsetY = Settings.RESOLUTION.getY()/4;
                
                g.drawImage(ImageLoader.TITLE_IMG, 
                            Settings.RESOLUTION.getX()/2 - sizeX/2, 
                            Settings.RESOLUTION.getY()/2 - sizeY/2 - offsetY, 
                            sizeX, 
                            sizeY, 
                            null);
            }
        };
        background.setLayout(null);
        background.setBackground(Color.BLACK);
        background.setSize(Settings.RESOLUTION.getX(),Settings.RESOLUTION.getY());
        
        int offsetY = (int)(100*scale);
        int btnWidth = (int)(250*scale);
        int btnHeight = (int)(75*scale);
        int btnX = Settings.RESOLUTION.getX()/2 - btnWidth/2;
        int btnY = Settings.RESOLUTION.getY()/2 - btnHeight/2;
        
        JButton btnStart = new JButton(Settings.LANGUAGE.getText("start"));
        btnStart.setBounds(btnX,btnY,btnWidth,btnHeight);
        btnStart.setFont(new Font(btnStart.getFont().getFamily(),
                         Font.BOLD, 
                         scale > 1.0f ? (int)(btnStart.getFont().getSize() * scale) : btnStart.getFont().getSize()));
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame();
            }
        });
        background.add(btnStart);
        
        JButton btnSettings = new JButton(Settings.LANGUAGE.getText("settings"));
        btnSettings.setBounds(btnX,btnY + offsetY,btnWidth,btnHeight);
        btnSettings.setFont(new Font(btnSettings.getFont().getFamily(),
                            Font.BOLD, 
                            scale > 1.0f ? (int)(btnSettings.getFont().getSize() * scale) : btnSettings.getFont().getSize()));
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowSettings();
            }
        });
        background.add(btnSettings);
        
        JButton btnExit = new JButton(Settings.LANGUAGE.getText("exit"));
        btnExit.setBounds(btnX,btnY + offsetY*2,btnWidth,btnHeight);
        btnExit.setFont(new Font(btnExit.getFont().getFamily(),
                        Font.BOLD, 
                        scale > 1.0f ? (int)(btnExit.getFont().getSize() * scale) : btnExit.getFont().getSize()));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        background.add(btnExit);
        
        int creditsWidth = (int)(100*scale);
        int creditsHeight = (int)(25*scale);
        int offsetCredits = (int)(5*scale);
        JButton btnCredits = new JButton(Settings.LANGUAGE.getText("credits"));
        btnCredits.setBounds(Settings.RESOLUTION.getX() - creditsWidth - offsetCredits - this.getInsets().right, 
                             Settings.RESOLUTION.getY() - creditsHeight - offsetCredits - this.getInsets().top, 
                             creditsWidth, 
                             creditsHeight);
        btnCredits.setFont(new Font(btnCredits.getFont().getFamily(),
                           Font.BOLD,
                           (int)(btnCredits.getFont().getSize() * scale)));
        btnCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowCredits();
            }
        });
        background.add(btnCredits);
        
        contentPane.add(background);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void run() {
        this.setName("Asteroid");
        this.setTitle(LANGUAGE.getText("title"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Settings.RESOLUTION.getX(), Settings.RESOLUTION.getY());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        MusicLoader.Serenity();
    }
        
    private void StartGame(){
        
    }
    
    private void ShowSettings(){
        SettingsDialog sd = new SettingsDialog(null, "Settings", scale);
        
    }
    
    private void ShowCredits(){
        JEditorPane creditsPane = new JEditorPane("text/html","<html>"
                        +"<body style=\""+ "font-family:"+ new JLabel().getFont().getFamily() +"\">"
                            +"<h1>"
                                + Settings.LANGUAGE.getText("credits").toUpperCase()
                            +"</h1>"
                            +"<h2>"
                                + Settings.LANGUAGE.getText("music")
                            +"</h2>"
                            +"<p style=\"margin-top:0px\">"
                                + "\"" + Settings.LANGUAGE.getText("song1") + "\" " + Settings.LANGUAGE.getText("artist1") + "<br>"
                                + "<a href="+Settings.LANGUAGE.getText("website1")+">" + Settings.LANGUAGE.getText("website1") + "</a>"
                            +"</p>"
                        + "</body>"
                        + "</html>");
                
                creditsPane.addHyperlinkListener(new HyperlinkListener() {
                    @Override
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
                            Desktop d = Desktop.getDesktop();
                            try {
                                d.browse(e.getURL().toURI());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }                            
                        }
                    }
                });
                creditsPane.setEditable(false);
                creditsPane.setBackground(new JLabel().getBackground());
                
                JOptionPane.showMessageDialog(null, creditsPane, Settings.LANGUAGE.getText("credits"),JOptionPane.PLAIN_MESSAGE);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
