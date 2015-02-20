package util;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javazoom.jl.player.Player;

public class MP3Player {

    private AudioInputStream in;

    public void play(final String file) {
        new Thread() {
            public void run() {
                try {
                    URL u = MP3Player.class.getResource(file);
                    System.out.println("u = " + u);
                    Player p = new Player(u.openStream());
                    p.play();
                } catch (Exception ex) {
                    System.out.println("Error while loading sound file: " + ex.getMessage());
                    System.out.println("Please check for \"" + file + "\".");
                }
            }
        }.start();
    }
}
