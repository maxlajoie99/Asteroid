/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Maxime Lajoie
 */
public class SoundPlayer {
    
    public final static String SHOOT = "Sound_Shooting.wav";
    public final static String THRUST = "Sound_Thrust.wav";
    public final static String ASTEROID_EXPLOSION = "Sound_AsteroidExplosion.wav";
    public final static String SPACESHIP_EXPLOSION = "Sound_SpaceShipExplosion.wav";
    public final static String EXTRA_LIFE = "Sound_ExtraLife.wav";
    
    static Clip shoot;
    static{
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(SHOOT));
            shoot = AudioSystem.getClip();
            shoot.open(audioStream);
            setVolume(shoot, 0.85f);
        } catch (Exception e) {
        }
    }
    
    static Clip thrust;
    static{
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(THRUST));
            thrust = AudioSystem.getClip();
            thrust.open(audioStream);
            setVolume(thrust, 1.00f);
        } catch (Exception e) {
        }
    }
    
    static Clip explosionA;
    static{
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(ASTEROID_EXPLOSION));
            explosionA = AudioSystem.getClip();
            explosionA.open(audioStream);
            setVolume(explosionA, 0.9f);
        } catch (Exception e) {
        }
    }
    
    static Clip explosionS;
    static{
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(SPACESHIP_EXPLOSION));
            explosionS = AudioSystem.getClip();
            explosionS.open(audioStream);
            setVolume(explosionS, 0.9f);
        } catch (Exception e) {
        }
    }
    
    static Clip extraLife;
    static{
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(EXTRA_LIFE));
            extraLife = AudioSystem.getClip();
            extraLife.open(audioStream);
            setVolume(extraLife, 1.0f);
        } catch (Exception e) {
        }
    }
    
    public static void Play(String soundName){
        
        switch(soundName){
            case SHOOT:
                shoot.setFramePosition(0);
                shoot.start();
                break;
            case THRUST:
                if (thrust.isRunning())
                    break;
                thrust.setFramePosition(0);
                thrust.start();
                break;
            case ASTEROID_EXPLOSION:
                explosionA.stop();
                explosionA.flush();
                explosionA.setFramePosition(0);
                explosionA.start();
                break;
            case SPACESHIP_EXPLOSION:
                explosionS.stop();
                explosionS.flush();
                explosionS.setFramePosition(0);
                explosionS.start();
                break;
            case EXTRA_LIFE:
                extraLife.setFramePosition(0);
                extraLife.start();
                break;
        }
    }
    
    private static void setVolume(Clip audio, float scale){
        FloatControl volume = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * scale) + volume.getMinimum();
        volume.setValue(gain);
    }
}
