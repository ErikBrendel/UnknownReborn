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
    private boolean looping = false;

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

    public void play(final boolean isLooping) {
        looping = isLooping;
        new Thread() {
            public void run() {
                try {
                    if (looping) {
                        do {
                            p.play();
                            loadData();
                        } while (looping);
                    } else {
                        while (true) {
                            p.play();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error while playing sound file: " + ex.getMessage());
                }
            }
        }.start();
    }

    public void stop() {
        looping = false;
        p.stop();
    }
}
