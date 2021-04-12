package snakeGame;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.Keyboard;

/* CPCS - 391
*  GROUP PROJECT
*  1. Eyad Halwani (Leader) - 1847263
*  2. Khalid Alghamdi - 1846409
*  3. Mohammed Miaji - 1845953 
*/
public class SnakeRunner {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.setProperty("org.lwjgl.librarypath", "F:\\GitHub\\CPCS_391_FINAL_PROJECT\\native");
        final int X = 1000;
        final int Y = 600;
        Game snakeManager = new Game(X, Y);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src\\snakeGame\\The Godfather Theme Song.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        snakeManager.startGame();

    }
}
