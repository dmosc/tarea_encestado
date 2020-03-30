package videogame;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.net.URL;

/**
 * @author oscarrodriguez
 */
public final class SoundClip {

    private AudioInputStream sample;
    private Clip clip;
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";

    /**
     *
     */
    public SoundClip() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("Error en " + e.toString());
        }
    }

    /**
     * @param filename
     */
    public SoundClip(String filename) {
        this();
        load(filename);
    }

    /**
     * @param looping
     */
    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    /**
     * @param repeat
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * @return
     */
    public boolean getLooping() {
        return looping;
    }

    /**
     * @return
     */
    public int getRepeat() {
        return repeat;
    }

    /**
     * @return
     */
    public String getFilename() {
        return filename;
    }

    private URL getURL(String filename) {
        URL url = null;
        try {

            url = this.getClass().getResource(filename);
        } catch (Exception e) {

            System.out.println("Error en " + e.toString());
        }
        return url;
    }

    /**
     * @return
     */
    public boolean isLoaded() {
        return (boolean) (sample != null);
    }

    /**
     * @param audiofile
     * @return
     */
    public boolean load(String audiofile) {
        try {

            setFilename(audiofile);
            sample = AudioSystem.getAudioInputStream(getURL(filename));
            clip.open(sample);
            return true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {

            System.out.println("Error en " + e.toString());
            return false;
        }
    }

    /**
     *
     */
    public void play() {
        if (!isLoaded()) {
            return;
        }

        clip.setFramePosition(0);

        if (looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(repeat);
        }
    }

    /**
     *
     */
    public void stop() {
        clip.stop();
    }
}
