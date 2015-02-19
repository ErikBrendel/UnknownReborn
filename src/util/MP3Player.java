package util;

import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import unknownreborn.UnknownReborn;

public class MP3Player {

    private AudioInputStream in;

    public void load(String file) {

        try {
            URL u = UnknownReborn.class.getClass().getResource(file);
            in = AudioSystem.getAudioInputStream(u);
            din = null;
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(targetFormat, in);
            line = getLine(targetFormat);
            if (line != null) {
                nBytesRead = 0;
                while (nBytesRead != -1) {
                    nBytesRead = din.read(data, 0, data.length);
                    if (nBytesRead != -1) {
                        line.write(data, 0, nBytesRead);
                    }

                }
            }
        } catch (Exception ex) {
            System.out.println("Error while loading sound file: " + ex.getMessage());
            System.out.println("Please check for \"" + file + "\".");
        }
    }

private AudioFormat targetFormat;
    private AudioInputStream din;
    private SourceDataLine line;
    private byte[] data = new byte[4096]; //4096
    private int nBytesRead = 0;
    public void playAudio() {
        try {
            if (line != null) {
                line.start();
                line.drain();
                line.stop();
                line.close();
                din.close(); /* */
            }
            in.close();
        } catch (Exception ex) {
            System.out.println("Error during playback: " + ex.getMessage());
        }
    }

    private synchronized SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info
                = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);

        return res;
    }
}
