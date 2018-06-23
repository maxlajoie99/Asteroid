/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game;

import ca.gamemaking.asteroid.game.asteroid.Asteroid;
import ca.gamemaking.asteroid.game.asteroid.Explosion;
import ca.gamemaking.asteroid.game.rocket.Rocket;
import ca.gamemaking.asteroid.game.player.Spaceship;
import ca.gamemaking.asteroid.graphics.images.ImageLoader;
import ca.gamemaking.asteroid.music.MusicLoader;
import ca.gamemaking.asteroid.settings.Settings;
import javax.swing.JFrame;
import ca.gamemaking.asteroid.settings.SettingsFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
 * @author Maxime Lajoie
 */
public class GameFrame extends JFrame {

    Container contentPane;
    JPanel background;
    Point[] stars;
    
    public Insets is;
    
    JButton btnStart;
    JButton btnSettings;
    JButton btnExit;
    JButton btnCredits;
    
    Random rnd;
    
    boolean drawTitle = true;
    public boolean gameStarted = false;
    public boolean pause = false;
    
    GameThread gamethread;
    public Spaceship player;
    public List<Rocket> rockets;
    public List<Asteroid> asteroids;
    public List<Explosion> explosions;
    private HashSet<Integer> keyPressed;
    
    int playerLives = 0;
    long points = 0;
    int pointsSinceLastLife = 0;
    
    public GameFrame() {
        initComponents();
        
        is = this.getInsets();
        
        rnd = new Random();
        Settings.SCALE = Settings.RESOLUTION.getX()/1280f;
        
        CreateStars();
        
        gamethread = new GameThread(Settings.TARGET_FPS);
        
        initUI();
        
        initFrameListeners();
    }
    
    private void CreateStars(){
        stars = new Point[(int)(120 * Settings.SCALE)];
        
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point(rnd.nextInt(Settings.RESOLUTION.getX()), rnd.nextInt(Settings.RESOLUTION.getY()));
        }
    }
    
    private void initUI(){
        contentPane = this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(null);
        
        background = new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                
                g.setColor(Color.WHITE);
                for (int i = 0; i < stars.length; i++) {
                    g.fillOval(stars[i].x, stars[i].y, 5, 5);
                }
                
                int sizeX = (int)(ImageLoader.TITLE_IMG.getWidth() * Settings.SCALE);
                int sizeY = (int)(ImageLoader.TITLE_IMG.getHeight() * Settings.SCALE);
                int offsetY = Settings.RESOLUTION.getY()/4;
                
                if (drawTitle)
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
        
        int offsetY = (int)(100 * Settings.SCALE);
        int btnWidth = (int)(250 * Settings.SCALE);
        int btnHeight = (int)(75 * Settings.SCALE);
        int btnX = Settings.RESOLUTION.getX()/2 - btnWidth/2;
        int btnY = Settings.RESOLUTION.getY()/2 - btnHeight/2;
        
        btnStart = new JButton(Settings.LANGUAGE.getText("start"));
        btnStart.setBounds(btnX,btnY,btnWidth,btnHeight);
        btnStart.setFont(new Font(btnStart.getFont().getFamily(),
                         Font.BOLD, 
                         Settings.SCALE > 1.0f ? (int)(btnStart.getFont().getSize() * Settings.SCALE) : btnStart.getFont().getSize()));
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawTitle = false;
                background.remove(btnStart);
                background.remove(btnSettings);
                background.remove(btnExit);
                background.remove(btnCredits);
                background.repaint();
        
                StartGame();
            }
        });
        background.add(btnStart);
        
        btnSettings = new JButton(Settings.LANGUAGE.getText("settings"));
        btnSettings.setBounds(btnX,btnY + offsetY,btnWidth,btnHeight);
        btnSettings.setFont(new Font(btnSettings.getFont().getFamily(),
                            Font.BOLD, 
                            Settings.SCALE > 1.0f ? (int)(btnSettings.getFont().getSize() * Settings.SCALE) : btnSettings.getFont().getSize()));
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowSettings();
            }
        });
        background.add(btnSettings);
        
        btnExit = new JButton(Settings.LANGUAGE.getText("exit"));
        btnExit.setBounds(btnX,btnY + offsetY*2,btnWidth,btnHeight);
        btnExit.setFont(new Font(btnExit.getFont().getFamily(),
                        Font.BOLD, 
                        Settings.SCALE > 1.0f ? (int)(btnExit.getFont().getSize() * Settings.SCALE) : btnExit.getFont().getSize()));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        background.add(btnExit);
        
        int creditsWidth = (int)(100*Settings.SCALE);
        int creditsHeight = (int)(25*Settings.SCALE);
        int offsetCredits = (int)(5*Settings.SCALE);
        btnCredits = new JButton(Settings.LANGUAGE.getText("credits"));
        btnCredits.setBounds(Settings.RESOLUTION.getX() - creditsWidth - offsetCredits - is.right - is.left, 
                             Settings.RESOLUTION.getY() - creditsHeight - offsetCredits - is.top - is.bottom, 
                             creditsWidth, 
                             creditsHeight);
        btnCredits.setFont(new Font(btnCredits.getFont().getFamily(),
                           Font.BOLD,
                           (int)(btnCredits.getFont().getSize() * Settings.SCALE)));
        btnCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowCredits();
            }
        });
        background.add(btnCredits);
        
        contentPane.add(background);
    }
    
    private void initFrameListeners(){
        
        keyPressed = new HashSet<>();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed.remove(e.getKeyCode());
            }
        });
        
        //this.removeKeyListener(this.getKeyListeners()[0]);
    }

    @Override
    public void paint(Graphics g){
        BufferedImage buffer = new BufferedImage(Settings.RESOLUTION.getX(), Settings.RESOLUTION.getY(), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = buffer.createGraphics();
        
        super.paint(bg);
        
        if (player != null)
            player.paint((Graphics2D)bg);
        
        if (rockets != null){
            //Clone the list and iterate over
            List<Rocket> rocketsCopy = new ArrayList<>(rockets);
            rocketsCopy.forEach((m) -> {
                m.paint((Graphics2D)bg);
            });
        }
            
        if (asteroids != null){
            List<Asteroid> asteroidsCopy = new ArrayList<>(asteroids);
            asteroidsCopy.forEach((a) -> {
                a.paint((Graphics2D)bg);
            });
        }
        
        if (explosions != null){
            List<Explosion> explosionsCopy = new ArrayList<>(explosions);
            explosionsCopy.forEach((e) -> {
                e.paint((Graphics2D)bg);
            });
        }
        
        if (gameStarted){
            bg.setFont(new Font(bg.getFont().getFamily(), Font.BOLD, (int)(35 * Settings.SCALE)));
            int x = Settings.RESOLUTION.getX() - (int)(100 * Settings.SCALE);
            int y = (int)(100 * Settings.SCALE);
            bg.drawString(String.format("%02d", playerLives), x, y);
            
            bg.setFont(new Font(bg.getFont().getFamily(), Font.BOLD, (int)(25 * Settings.SCALE)));
            x = Settings.RESOLUTION.getX()/2 - (int)(115*Settings.SCALE);
            y = (int)(75 * Settings.SCALE);
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(' ');
            DecimalFormat df = new DecimalFormat("000,000,000,000", dfs);
            bg.drawString(df.format(points), x, y);
        }

        g.drawImage(buffer, 0, 0, null);
        bg.dispose();
    }
    
    public void Update(double deltaTime){
        player.Update(deltaTime, keyPressed);
        
        //Clone the list and iterate over it
        List<Rocket> rocketsCopy = new ArrayList<>(rockets);
        rocketsCopy.forEach((m) -> {
            m.Update(deltaTime);
        });
        
        //Temporary way to create asteroids
        if (keyPressed.contains(KeyEvent.VK_0)){
            Asteroid t = new Asteroid(Settings.RESOLUTION.getX()/2, Settings.RESOLUTION.getY()/2);
            asteroids.add(t);
        }
        
        List<Asteroid> asteroidsCopy = new ArrayList<>(asteroids);
        asteroidsCopy.forEach((a) -> {
            a.Update(deltaTime);
            //Detect collision with missiles
            rocketsCopy.forEach((m) -> {
                a.RocketCollision(m);
            });
        });
        
        List<Explosion> explosionsCopy = new ArrayList<>(explosions);
        explosionsCopy.forEach((e) -> {
            e.Update(deltaTime);
        });
        
        player.AsteroidCollision(asteroidsCopy);
        
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
        this.setTitle(Settings.LANGUAGE.getText("title"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Settings.RESOLUTION.getX(), Settings.RESOLUTION.getY());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();
        MusicLoader.StartPlaying();
    }
        
    private void StartGame(){
        playerLives = Settings.DEFAULT_NB_LIVES;
        player = new Spaceship();
        rockets = new LinkedList<>();
        asteroids = new LinkedList<>();
        explosions = new LinkedList<>();
        
        points = 0;
        pointsSinceLastLife = 0;
        
        //TODO Create asteroids on start
        
        gameStarted = true;
        gamethread.start();
        this.requestFocus();
    }
    
    public void RemoveLife(){ 
        playerLives--; 
        if (playerLives < 0)
            System.out.println("End game now"); //TODO End game here
    }
    
    /*private void EndGame(){
        initUI();
    }*/
    
    public void AddPoints(int nb){
        points += nb;
        pointsSinceLastLife += nb;
        if (pointsSinceLastLife >= Settings.DEFAULT_NB_POINTS_FOR_NEW_LIFE){
            playerLives++;
            pointsSinceLastLife -= Settings.DEFAULT_NB_POINTS_FOR_NEW_LIFE;
        }
    }
    
    private void ShowSettings(){
        SettingsFrame sd = new SettingsFrame(this, Settings.LANGUAGE.getText("settings"));
    }
    
    private void ShowCredits(){
        JEditorPane creditsPane = new JEditorPane("text/html","<html>"
                        +"<body style=\""+ "font-family:"+ new JLabel().getFont().getFamily() +"\">"
                            +"<h1>"
                                + Settings.LANGUAGE.getText("credits").toUpperCase()
                            +"</h1>"
                            +"<p>" + Settings.LANGUAGE.getText("copyright") + "</p>"
                            +"<h2>"
                                + Settings.LANGUAGE.getText("music")
                            +"</h2>"
                            +"<p style=\"margin-top:0px\">"
                                + Settings.LANGUAGE.getText("artist1") + "<br>"
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
        this.requestFocus();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
