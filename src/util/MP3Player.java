package util;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MP3Player {

    private AdvancedPlayer p;
    private final String path;

    public MP3Player(final String path) {
        this.path = path;
        loadData();
    }
    
    private void loadData() {
        try {
            URL u = MP3Player.class.getResource(path);
            System.out.println("u = " + u);
            p = new AdvancedPlayer(u.openStream());
        } catch (IOException | JavaLayerException ex) {
            Logger.getLogger(MP3Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play(final boolean looping) {
        new Thread() {
            public void run() {
                try {
                    if (looping) {
                        while (true) {
                            p.play();
                            loadData();
                        }
                    } else {
                        p.play();
                    }
                } catch (Exception ex) {
                    System.out.println("Error while playing sound file: " + ex.getMessage());
                }
            }
        }.start();
    }
    
    public void stop() {
        p.stop();
    }
}
