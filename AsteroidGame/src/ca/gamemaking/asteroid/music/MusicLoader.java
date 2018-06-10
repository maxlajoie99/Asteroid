/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.music;

import javax.sound.sampled.*;

/**
 *
 * @author maxla
 */
public class MusicLoader {
    
    public static void Serenity(){
        
        Clip audioClip;
        
        try {
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(MusicLoader.class.getResourceAsStream("AhrixSerenity.wav"));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            
            FloatControl volume = (FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (range * 0.85f) + volume.getMinimum();
            volume.setValue(gain);
            
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
