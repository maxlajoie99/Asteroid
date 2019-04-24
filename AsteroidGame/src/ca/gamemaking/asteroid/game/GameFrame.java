/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.game;

import ca.gamemaking.asteroid.game.asteroid.Asteroid;
import ca.gamemaking.asteroid.game.asteroid.Explosion;
import ca.gamemaking.asteroid.game.rocket.Rocket;
import ca.gamemaking.asteroid.game.player.Spaceship;
import ca.gamemaking.asteroid.graphics.images.ImageLoader;
import ca.gamemaking.asteroid.lang.Lang;
import ca.gamemaking.asteroid.music.MusicLoader;
import ca.gamemaking.asteroid.settings.Settings;
import ca.gamemaking.asteroid.settings.SettingsFrame;
import ca.gamemaking.asteroid.sound.SoundPlayer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;

/**
 *
 * @author Maxime Lajoie
 */
public class GameFrame extends JFrame {
    private static final int STAR_SIZE = 5;
    private static final long END_WAIT_TIME = 2000;
    private static final double MIN_SPAWN_TIME = 4.5;
    private static final double MAX_SPAWN_TIME = 10.25;
    private static final int MIN_SPAWN_AMOUNT = 3;
    private static final int MAX_SPAWN_AMOUNT = 6;
    private static final int MAX_ASTEROIDS_AT_TIME = 12;

    private JPanel background;
    private Point[] stars;

    private JButton btnStart;
    private JButton btnSettings;
    private JButton btnExit;
    private JButton btnCredits;

    private boolean drawTitle = true;
    private boolean gameStarted = false;
    private boolean pause = false;
    private boolean ending = false;
    private GameThread gamethread;
    private HashSet<Integer> keyPressed;

    public Spaceship player;
    public List<Rocket> rockets;
    public List<Asteroid> asteroids;
    public List<Explosion> explosions;
    public Insets insets;

    private int playerLives = 0;
    private long points = 0;
    private int pointsSinceLastLife = 0;
    private double minDistance;
    private double currentTime = 0.0;
    private double currentDelay = 0.0;

    private Font POINTS_FONT;
    private Font LIVES_FONT;
    private Font PAUSE_FONT;
    private final Point POINTS_POS = new Point();
    private final Point LIVES_POS = new Point();
    private final Point PAUSE_POS = new Point();
    private final Point END_TEXT_POS = new Point();

    public GameFrame() {
        initComponents();
        
        insets = this.getInsets();

        Settings.SCALE = Settings.RESOLUTION.getX() / 1280f;

        CreateStars();

        InitFonts();
        InitUI();
        InitFrameListeners();
    }

    private void InitFonts() {
        LIVES_FONT = new Font(this.getFont().getFamily(), Font.BOLD, (int)(35 * Settings.SCALE));
        LIVES_POS.x = Settings.RESOLUTION.getX() - (int)(100 * Settings.SCALE);
        LIVES_POS.y = (int)(100 * Settings.SCALE);

        POINTS_FONT = new Font(this.getFont().getFamily(), Font.BOLD, (int)(35 * Settings.SCALE));
        POINTS_POS.x = Settings.RESOLUTION.getX() / 2 - (int)(115 * Settings.SCALE);
        POINTS_POS.y = (int)(75 * Settings.SCALE);

        PAUSE_FONT = new Font(this.getFont().getFamily(), Font.BOLD, (int)(125 * Settings.SCALE));
        PAUSE_POS.x = Settings.RESOLUTION.getX() / 2 - (int)(425 * Settings.SCALE / 2);
        PAUSE_POS.y = Settings.RESOLUTION.getY() / 2 + PAUSE_FONT.getSize() / 2;

        END_TEXT_POS.x = Settings.RESOLUTION.getX() / 2 - (int)(800 * Settings.SCALE / 2);
        END_TEXT_POS.y = Settings.RESOLUTION.getY() / 2 + PAUSE_FONT.getSize() / 2;
    }

    private void CreateStars() {
        stars = new Point[(int)(120 * Settings.SCALE)];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Point(Settings.RANDOM.nextInt(Settings.RESOLUTION.getX()), Settings.RANDOM.nextInt(Settings.RESOLUTION.getY()));
        }
    }
    
    private void InitUI() {
        Container contentPane = this.getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(null);
        
        background = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                
                g.setColor(Color.WHITE);
                for (Point star: stars) {
                    g.fillOval(star.x, star.y, STAR_SIZE, STAR_SIZE);
                }
                
                int sizeX = (int)(ImageLoader.TITLE_IMG.getWidth() * Settings.SCALE);
                int sizeY = (int)(ImageLoader.TITLE_IMG.getHeight() * Settings.SCALE);
                int offsetY = Settings.RESOLUTION.getY() / 4;
                
                if (drawTitle) {
                    g.drawImage(ImageLoader.TITLE_IMG,
                            (Settings.RESOLUTION.getX() - sizeX) / 2,
                            (Settings.RESOLUTION.getY() - sizeY) / 2 - offsetY,
                                sizeX,
                                sizeY,
                                null);
                }
            }
        };
        background.setLayout(null);
        background.setBackground(Color.BLACK);
        background.setSize(Settings.RESOLUTION.getX(), Settings.RESOLUTION.getY());
        
        int offsetY = (int)(100 * Settings.SCALE);
        int btnWidth = (int)(250 * Settings.SCALE);
        int btnHeight = (int)(75 * Settings.SCALE);
        int btnX = Settings.RESOLUTION.getX() / 2 - btnWidth / 2;
        int btnY = Settings.RESOLUTION.getY() / 2 - btnHeight / 2;
        
        btnStart = new JButton(Settings.LANGUAGE.getText("start"));
        btnStart.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnStart.setFont(new Font(btnStart.getFont().getFamily(),
                                    Font.BOLD,
                                    (int)(btnStart.getFont().getSize() * Settings.SCALE)));
        btnStart.addActionListener(e -> {
            drawTitle = false;

            background.remove(btnStart);
            background.remove(btnSettings);
            background.remove(btnExit);
            background.remove(btnCredits);
            background.repaint();

            StartGame();
        });
        background.add(btnStart);
        
        btnSettings = new JButton(Settings.LANGUAGE.getText("settings"));
        btnSettings.setBounds(btnX,btnY + offsetY, btnWidth, btnHeight);
        btnSettings.setFont(new Font(btnSettings.getFont().getFamily(),
                                    Font.BOLD,
                                    (int)(btnSettings.getFont().getSize() * Settings.SCALE)));
        btnSettings.addActionListener(e -> ShowSettings());
        background.add(btnSettings);
        
        btnExit = new JButton(Settings.LANGUAGE.getText("exit"));
        btnExit.setBounds(btnX,btnY + offsetY * 2, btnWidth, btnHeight);
        btnExit.setFont(new Font(btnExit.getFont().getFamily(),
                                Font.BOLD,
                                (int)(btnExit.getFont().getSize() * Settings.SCALE)));
        btnExit.addActionListener(e -> System.exit(0));
        background.add(btnExit);
        
        int creditsWidth = (int)(100 * Settings.SCALE);
        int creditsHeight = (int)(25 * Settings.SCALE);
        int offsetCredits = (int)(5 * Settings.SCALE);
        btnCredits = new JButton(Settings.LANGUAGE.getText("credits"));
        btnCredits.setBounds(Settings.RESOLUTION.getX() - creditsWidth - offsetCredits - insets.right - insets.left,
                             Settings.RESOLUTION.getY() - creditsHeight - offsetCredits - insets.top - insets.bottom,
                             creditsWidth, 
                             creditsHeight);
        btnCredits.setFont(new Font(btnCredits.getFont().getFamily(),
                                    Font.BOLD,
                                    (int)(btnCredits.getFont().getSize() * Settings.SCALE)));
        btnCredits.addActionListener(e -> ShowCredits());
        background.add(btnCredits);
        
        contentPane.add(background);
    }
    
    private void InitFrameListeners() {
        keyPressed = new HashSet<>();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (gameStarted && e.getKeyCode() == KeyEvent.VK_P && !ending) {
                    pause = !pause;
                    repaint(); //To write the text
                }

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
    public void paint(Graphics g) {
        BufferedImage buffer = new BufferedImage(Settings.RESOLUTION.getX(), Settings.RESOLUTION.getY(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = buffer.createGraphics();
        
        super.paint(graphics2D);
        
        if (gameStarted) {
            if (player != null) {
                player.paint(graphics2D);
            }

            if (rockets != null) {
                new ArrayList<>(rockets).parallelStream().filter(Objects::nonNull).forEach((m) -> m.paint(graphics2D));
            }

            if (asteroids != null) {
                new ArrayList<>(asteroids).parallelStream().filter(Objects::nonNull).forEach((a) -> a.paint(graphics2D));
            }

            if (explosions != null) {
                new ArrayList<>(explosions).parallelStream().filter(Objects::nonNull).forEach((e) -> e.paint(graphics2D));
            }

            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(LIVES_FONT);
            graphics2D.drawString(String.format("%02d", playerLives), LIVES_POS.x, LIVES_POS.y);
            
            graphics2D.setFont(POINTS_FONT);
            graphics2D.drawString(Settings.POINT_FORMAT.format(points), POINTS_POS.x, POINTS_POS.y);

            if (pause) {
                graphics2D.setFont(PAUSE_FONT);
                graphics2D.drawString(Lang.PAUSE, PAUSE_POS.x, PAUSE_POS.y);
            } else if (ending) {
                graphics2D.setFont(PAUSE_FONT);
                graphics2D.drawString(Lang.GAME_OVER, END_TEXT_POS.x, END_TEXT_POS.y);
            }
        }

        g.drawImage(buffer, 0, 0, null);
        graphics2D.dispose();
    }
    
    public void update(double deltaTime) {
        player.update(deltaTime, keyPressed);

        currentTime += deltaTime;
        if (currentTime > currentDelay && asteroids.stream().filter(Objects::nonNull).count() < MAX_ASTEROIDS_AT_TIME) {
            currentTime = 0.0;
            currentDelay = Settings.RANDOM.nextDouble() * (MAX_SPAWN_TIME - MIN_SPAWN_TIME) + MIN_SPAWN_TIME;
            CreateAsteroids(Settings.RANDOM.nextInt(MAX_SPAWN_AMOUNT - MIN_SPAWN_AMOUNT) + MIN_SPAWN_AMOUNT);
        }

        List<Rocket> rocketsCopy = new ArrayList<>(rockets);
        rocketsCopy.parallelStream().filter(Objects::nonNull).forEach((m) -> m.update(deltaTime));
        
        List<Asteroid> asteroidsCopy = new ArrayList<>(asteroids);
        asteroidsCopy.parallelStream().filter(Objects::nonNull).forEach((a) -> {
            a.update(deltaTime);
            //Detect collision with missiles
            rocketsCopy.parallelStream().filter(Objects::nonNull).forEach(a::collideRocket);
        });

        new ArrayList<>(explosions).parallelStream().filter(Objects::nonNull).forEach(e -> e.update(deltaTime));
        
        player.collideAsteroids(asteroidsCopy);
    }

    /**
     * This method insets called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method insets always
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

        MusicLoader.startPlaying();
    }
        
    private void StartGame() {
        playerLives = Settings.DEFAULT_NB_LIVES;
        minDistance = Math.pow(300 * Settings.SCALE, 2);

        player = new Spaceship();
        rockets = new LinkedList<>();
        asteroids = new LinkedList<>();
        explosions = new LinkedList<>();
        
        points = 0;
        pointsSinceLastLife = 0;

        gameStarted = true;
        gamethread = new GameThread(Settings.TARGET_FPS);
        gamethread.start();

        CreateAsteroids(10);

        this.requestFocus();
    }

    public boolean isPaused() {
        return pause;
    }

    public void removeLife() {
        playerLives--; 
        if (playerLives < 0) {
            EndGame();
        }
    }

    private void CreateAsteroids(int number) {
        IntStream.range(0, number).forEach(i -> CreateAsteroid());
    }

    private void CreateAsteroid() {
        int x,y;

        do {
            x = Settings.RANDOM.nextInt(Settings.RESOLUTION.getX());
            y = Settings.RANDOM.nextInt(Settings.RESOLUTION.getY());
        } while (!ValidAsteroidPosition(x, y));

        asteroids.add(new Asteroid(x, y));
    }

    private boolean ValidAsteroidPosition(int x, int y) {
        return player.getPosition().distanceSq(x, y) > minDistance;
    }
    
    private void EndGame() {
        player = null;
        playerLives = 0;

        asteroids.clear();
        rockets.clear();
        explosions.clear();

        gamethread.stopThread();
        gamethread = null;
        pause = false;
        ending = true;

        try {
            this.repaint();
            Thread.sleep(END_WAIT_TIME);
        } catch (Exception e) {
            System.out.println("There was an error while ending the game");
        }

        ending = false;
        gameStarted = false;

        drawTitle = true;
        InitUI();
    }
    
    public void addPoints(int nb) {
        points += nb;
        pointsSinceLastLife += nb;
        if (pointsSinceLastLife >= Settings.DEFAULT_NB_POINTS_FOR_NEW_LIFE){
            playerLives++;
            pointsSinceLastLife -= Settings.DEFAULT_NB_POINTS_FOR_NEW_LIFE;

            SoundPlayer.play(SoundPlayer.EXTRA_LIFE);
        }
    }
    
    private void ShowSettings() {
        SettingsFrame sd = new SettingsFrame(this, Settings.LANGUAGE.getText("settings"));
    }
    
    private void ShowCredits() {
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
                
        creditsPane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                Desktop d = Desktop.getDesktop();
                try {
                    d.browse(e.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        creditsPane.setEditable(false);
        creditsPane.setBackground(new JLabel().getBackground());
                
        JOptionPane.showMessageDialog(null, creditsPane, Settings.LANGUAGE.getText("credits"), JOptionPane.PLAIN_MESSAGE);
        this.requestFocus();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
