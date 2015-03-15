package util;

import java.net.URL;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

public class MP3Player {

    public static final boolean AUDIO_ENABLED = true;

    /**
     * alle aktiven Player (also eigentlich alle, die komplette players-liste)
     * werden angehalten.
     */
    public static void stopAllAudio() {
        new Thread() {
            public void run() {
                getSoundSystem().cleanup();
            }
        }.start();
    }

    private static SoundSystem myOne = null;

    private static SoundSystem getSoundSystem() {
        if (myOne == null) {
            try {
                SoundSystemConfig.setSoundFilesPackage("/resources/sound/");
                //SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
                SoundSystemConfig.addLibrary(LibraryJavaSound.class);
                SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
                //SoundSystemConfig.setCodec("ogg", CodecJOgg.class);
                myOne = new SoundSystem();
                myOne.setListenerPosition(0, 0, 100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return myOne;
    }

    public static void addSource(String name, String location, boolean looping) {
        URL u = MP3Player.class.getResource("/resources/sound/" + location);
        System.out.println("u = " + u);
        //getSoundSystem().backgroundMusic(name, u, location, looping);
        getSoundSystem().newSource(true, name, u, location, looping, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF, SoundSystemConfig.getDefaultRolloff());
        //getSoundSystem().loadSound(name); //not sure if actually neccessary :D
    }

    public static void play(String name) {
        if (AUDIO_ENABLED) {
            getSoundSystem().play(name);
        }
    }

    public static void setVolume(String name, int percent) {
        getSoundSystem().setVolume(name, ((float) percent) / 100f);
    }
    
    /**
     * 0 % = mittig, -100 prozent = links, 100% = rechts
     * @param name
     * @param percent
     */
    public static void setBalance(String name, int percent) {
        getSoundSystem().setPosition(name, percent, 0, 0);
    }

    public static void setMasterVolume(int percent) {
        getSoundSystem().setMasterVolume(((float) percent) / 100f);
    }

    public static void fade(String fromName, String toName, int fadeOutMS, int fadeInMS) {
        getSoundSystem().fadeOutIn(fromName, toName, fadeOutMS, fadeInMS);
    }

    public static void stop(String name) {
        getSoundSystem().stop(name);
    }

    public static void fadeOut(String name, int ms) {
        getSoundSystem().fadeOut(name, null, ms);
    }
}
