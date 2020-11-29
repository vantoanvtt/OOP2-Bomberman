package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {
    // Sound effect
    public MediaPlayer startSound = SoundController.makeSound("Level_Start.mp3");
    public MediaPlayer walking = SoundController.makeSound("Walk_cut1.mp3", 0.1);
    public MediaPlayer makeBomb = SoundController.makeSound("Make_Bomb.mp3", 8);
    public MediaPlayer bombExplodes = SoundController.makeSound("Bomb_Explodes.mp3", 3);
    public MediaPlayer getItem = SoundController.makeSound("Get_Item.mp3");
    public MediaPlayer getDamage = SoundController.makeSound("Get_Damage.mp3");
    public MediaPlayer kill_Enemy = SoundController.makeSound("Kill_Enemy.mp3");
    public MediaPlayer death = SoundController.makeSound("Death.mp3");

    // Soundtrack
    public MediaPlayer findTheExit = SoundController.makeSound("Find_The_Exit.mp3");
    public MediaPlayer currentThemeSound;

    public void repeat(MediaPlayer mediaPlayer) {
        SoundController.repeatMedia(mediaPlayer);
    }
    public void replay(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    public void playSound(String mp3File) {
        makeSound(mp3File).play();
//        SoundController soundController = new SoundController();
//        soundController.makeSound(mp3File).play();
    }

    public MediaPlayer makeSound(String mp3File) {
        URL resource = SoundController.class.getResource("/sound/");
        System.out.println(resource + mp3File);
        Media sound = new Media(resource + mp3File);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        return mediaPlayer;
    }

    public void stopAll() {
        SoundController.stopAllSounds(this);
    }
}
