package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Bomb extends Entity{
    protected int time;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomb(int x, int y, Image img, int time) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this.time = time;
        this.rectangle = new Rectangle(this.x, this.y, (int) img.getWidth(),(int) img.getHeight());
    }

    public void deadLineBom() {
        img = Sprite.movingSprite(Sprite.bomb.getFxImage(),Sprite.bomb_1.getFxImage(), Sprite.bomb_2.getFxImage(), time, 200/3);
        time--;
    }

    @Override
    public void update() {

    }


}
