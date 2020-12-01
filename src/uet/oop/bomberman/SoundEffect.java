package uet.oop.bomberman;


import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SoundEffect extends Application{
    //backsoundsrc\Sound\backSound.mp3
    public static Media mediaBackSound = new Media(new File("src\\Sound\\backSound.mp3")
            .toURI().toString());
    public static MediaPlayer mediaPlayerbacksound = new MediaPlayer(mediaBackSound);

    //EatItem
    public static Media mediaEatItem = new Media(new File("src\\Sound\\19.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerEatItem = new MediaPlayer(mediaEatItem);

    //Die
    public static Media mediaCollisionEnemy = new Media(new File("src\\Sound\\collision.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerCollisionEnemy = new MediaPlayer(mediaCollisionEnemy);

    //BombExploded
    public static Media mediaBombExploded = new Media(new File("src\\Sound\\bombExplode.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerBombExploded = new MediaPlayer(mediaBombExploded);

    public static Media mediaPlaceBomb = new Media(new File("src\\Sound\\placeBomb.mp3")
            .toURI().toString());
    public static MediaPlayer mediaPlayerPlaceBomb = new MediaPlayer(mediaPlaceBomb);


    public static void sound(MediaPlayer mp) {
        mp.play();
        System.out.println("play");
    }

    @Override
    public void start(Stage arg0) throws Exception {
        //sound();
    }
}
