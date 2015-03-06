package util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MP3Player {

    public static final boolean AUDIO_ENABLED = true;

    /**
     * alle aktiven Player (also eigentlich alle, die komplette players-liste)
     * werden angehalten.
     */
    public static void stopAllAudio() {
        for (MP3Player p : players) {
            try {
                p.stop();
            } catch (Exception e) {
            }
        }
    }
    private static final ArrayList<MP3Player> players = new ArrayList<>();

    private AdvancedPlayer p;
    private final String path;
    private boolean looping = false;

    public MP3Player(final String path) {
        players.add(this);
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
        if (AUDIO_ENABLED) {
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
                            p.play();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error while playing sound file: " + ex.getMessage());
                    }
                }
            }.start();
        }
    }

    public void stop() {
        looping = false;
        p.close();
    }
}
