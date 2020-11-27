package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashMap;
import java.io.*;

public class GameSound {
    public static GameSound instance;

    public static final String MENU = "menu.wav";
    public static final String PLAY_GAME = "playgame.mid";
    public static final String BOMB = "src/uet/oop/bomberman/sound/newbomb.wav";
    public static final String BOMBER_DIE = "bomber_die.wav";
    public static final String MONSTER_DIE = "monster_die.wav";
    public static final String BONG_BANG = "src/uet/oop/bomberman/sound/bomb_bang.wav";
    public static final String ITEM = "item.wav";
    public static final String WIN = "win.wav";
    public static final String LOSE = "lose.mid";
    private HashMap<String, SourceDataLine> audioMap = new HashMap<>();

    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;


    /**
     * @param filename the name of the file that is going to be played
     */
    public SourceDataLine playSound(String filename) {

        String strFilename = filename;

        try {
            soundFile = new File(strFilename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        //sourceLine.start();
        //sourceLine.drain();
        //sourceLine.close();
        return sourceLine;
    }


    public void loadAudio() {
        //audioMap.put("MENU", playSound(MENU));
        audioMap.put("BOMB", playSound(BOMB));
        audioMap.put("BONG_BANG", playSound(BONG_BANG));
    }
    public static GameSound getInstance() {
        if (instance == null) {
            instance = new GameSound();
        }

        return instance;
    }

    public GameSound() {
        loadAudio();
    }

    public void playAudio(String name) {
        audioMap.get(name).start();
        audioMap.get(name).drain();
        audioMap.get(name).close();
    }

}
