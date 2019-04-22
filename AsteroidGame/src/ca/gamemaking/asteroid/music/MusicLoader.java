/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.music;

import ca.gamemaking.asteroid.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

/**
 *
 * @author Maxime Lajoie
 */
public class MusicLoader {
    private static final List<String> MUSICS = new ArrayList<>();
    static {
        //Ahrix songs
        MUSICS.add("ANewStart");
        MUSICS.add("CarpeDiem");
        MUSICS.add("Courage");
        MUSICS.add("Dreams");
        MUSICS.add("Euphoria");
        MUSICS.add("Evolving");
        MUSICS.add("Forgiven");
        MUSICS.add("Hope");
        MUSICS.add("LeftBehind");
        MUSICS.add("Moments");
        MUSICS.add("NeverAlone");
        MUSICS.add("NewEra");
        MUSICS.add("Nova");
        MUSICS.add("Pure");
        MUSICS.add("Raising");
        MUSICS.add("Reborn");
        MUSICS.add("Relief");
        MUSICS.add("Serenity");
        MUSICS.add("Snowbound");
        MUSICS.add("TheDreamer");
        MUSICS.add("Unite");
        MUSICS.add("WeDontKnow");
    }

    public static void startPlaying() {
        Clip audioClip;
        int i = Settings.RANDOM.nextInt(MUSICS.size() - 1);
        
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(MusicLoader.class.getResourceAsStream(MUSICS.get(i) + ".wav"));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            
            FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (range * 0.85f) + volume.getMinimum();
            volume.setValue(gain);
            
            audioClip.addLineListener(event -> {
                if (Type.STOP.equals(event.getType())) {
                    startPlaying();
                }
            });
            
            //audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println(MUSICS.get(i) + " is playing");
            audioClip.start();
        } catch (Exception e) {
            System.out.println("Could not play : " + MUSICS.get(i));
        }
    }
}
