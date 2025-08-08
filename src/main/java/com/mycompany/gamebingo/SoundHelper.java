package com.mycompany.gamebingo;
import java.io.IOException; 
import java.net.URL;
import javax.sound.sampled.*;
/**
 *
 * @author nguye
 */
public final class SoundHelper {
    Clip clip; 
    public void newCreate(String soundFile) {
        try {
            URL soundURL = getClass().getResource(soundFile);
            if (soundURL != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                this.clip = clip;
            } else {
                System.err.println("Sound file not found: " + soundFile);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        }
    }
    public SoundHelper(String filePath) 
        throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException  
    { 
        newCreate(filePath);
    }
    
    public void play()  
    { 
        //start the clip 
        clip.start(); 
    }
    public void stop(){
        clip.stop();
    }
    
    
}
