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
    public static final String SHOOT = "Sound_Shooting.wav";
    public static final String THRUST = "Sound_Thrust.wav";
    public static final String ASTEROID_EXPLOSION = "Sound_AsteroidExplosion.wav";
    public static final String SPACESHIP_EXPLOSION = "Sound_SpaceShipExplosion.wav";
    public static final String EXTRA_LIFE = "Sound_ExtraLife.wav";
    
    private static Clip SHOOT_CLIP;
    static {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(SHOOT));
            SHOOT_CLIP = AudioSystem.getClip();
            SHOOT_CLIP.open(audioStream);
            SetVolume(SHOOT_CLIP, 0.85f);
        } catch (Exception e) {
            System.out.println("There was an error loading " + SHOOT);
        }
    }
    
    private static Clip THRUSTER_CLIP;
    static {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(THRUST));
            THRUSTER_CLIP = AudioSystem.getClip();
            THRUSTER_CLIP.open(audioStream);
            SetVolume(THRUSTER_CLIP, 1.00f);
        } catch (Exception e) {
            System.out.println("There was an error loading " + THRUST);
        }
    }
    
    private static Clip EXPLOSION_ASTEROID_CLIP;
    static {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(ASTEROID_EXPLOSION));
            EXPLOSION_ASTEROID_CLIP = AudioSystem.getClip();
            EXPLOSION_ASTEROID_CLIP.open(audioStream);
            SetVolume(EXPLOSION_ASTEROID_CLIP, 0.9f);
        } catch (Exception e) {
            System.out.println("There was an error loading " + ASTEROID_EXPLOSION);
        }
    }
    
    private static Clip EXPLOSION_SHIP_CLIP;
    static {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(SPACESHIP_EXPLOSION));
            EXPLOSION_SHIP_CLIP = AudioSystem.getClip();
            EXPLOSION_SHIP_CLIP.open(audioStream);
            SetVolume(EXPLOSION_SHIP_CLIP, 0.9f);
        } catch (Exception e) {
            System.out.println("There was an error loading " + SPACESHIP_EXPLOSION);
        }
    }
    
    private static Clip EXTRA_LIFE_CLIP;
    static {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(EXTRA_LIFE));
            EXTRA_LIFE_CLIP = AudioSystem.getClip();
            EXTRA_LIFE_CLIP.open(audioStream);
            SetVolume(EXTRA_LIFE_CLIP, 1.0f);
        } catch (Exception e) {
            System.out.println("There was an error loading " + EXTRA_LIFE);
        }
    }
    
    public static void play(String soundName) {
        switch(soundName) {
            case SHOOT:
                SHOOT_CLIP.setFramePosition(0);
                SHOOT_CLIP.start();
                break;
            case THRUST:
                if (THRUSTER_CLIP.isRunning()) {
                    break;
                }

                THRUSTER_CLIP.setFramePosition(0);
                THRUSTER_CLIP.start();
                break;
            case ASTEROID_EXPLOSION:
                EXPLOSION_ASTEROID_CLIP.stop();
                EXPLOSION_ASTEROID_CLIP.flush();
                EXPLOSION_ASTEROID_CLIP.setFramePosition(0);
                EXPLOSION_ASTEROID_CLIP.start();
                break;
            case SPACESHIP_EXPLOSION:
                EXPLOSION_SHIP_CLIP.stop();
                EXPLOSION_SHIP_CLIP.flush();
                EXPLOSION_SHIP_CLIP.setFramePosition(0);
                EXPLOSION_SHIP_CLIP.start();
                break;
            case EXTRA_LIFE:
                EXTRA_LIFE_CLIP.setFramePosition(0);
                EXTRA_LIFE_CLIP.start();
                break;
        }
    }
    
    private static void SetVolume(Clip audio, float scale){
        FloatControl volume = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * scale) + volume.getMinimum();
        volume.setValue(gain);
    }
}
