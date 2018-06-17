/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

/**
 *
 * @author maxla
 */
public class MusicLoader {
    
    private static Random rnd;
    
    public static void StartPlaying(){
        
        Clip audioClip;
        rnd = new Random();
        
        int i = rnd.nextInt(getMusicList().size() - 1);
        
        try {
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(MusicLoader.class.getResourceAsStream(getMusicList().get(i) + ".wav"));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            
            FloatControl volume = (FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (range * 0.85f) + volume.getMinimum();
            volume.setValue(gain);
            
            audioClip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() != Type.STOP)
                        return;
                    else
                        StartPlaying();
                }
            });
            
            //audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println(getMusicList().get(i) + " is playing");
            audioClip.start();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not play : " + getMusicList().get(i));
        }
        
    }
    
    private static List<String> getMusicList(){
        
        List<String> musics = new ArrayList<>();
        
        //Ahrix songs
        musics.add("ANewStart");
        musics.add("CarpeDiem");
        musics.add("Courage");
        musics.add("Dreams");
        musics.add("Euphoria");
        musics.add("Evolving");
        musics.add("Forgiven");
        musics.add("Hope");
        musics.add("LeftBehind");
        musics.add("Moments");
        musics.add("NeverAlone");
        musics.add("NewEra");
        musics.add("Nova");
        musics.add("Pure");
        musics.add("Raising");
        musics.add("Reborn");
        musics.add("Relief");
        musics.add("Serenity");
        musics.add("Snowbound");
        musics.add("TheDreamer");
        musics.add("Unite");
        musics.add("WeDontKnow");
        
        return musics;
    }
    
}
