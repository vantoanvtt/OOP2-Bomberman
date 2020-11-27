package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.Random;

public class Brick extends Entity {
    protected boolean destroy = false;
    public Random generator = new Random();
    public Brick(int x, int y, Image img) {
        super(x, y, img);

    }

    private int animate = 49;


    @Override
    public void update() {
        checkDestroyed();
        if (destroy) {
            toang();
            if (animate < 2) {
                afterDestroy();
            }
        }

    }


    public void animate() {
        if (animate < 0) animate = 49;
        animate--;
    }

    public void toang() {
        this.img = Sprite.movingSprite(Sprite.brick_exploded.getFxImage(), Sprite.brick_exploded1.getFxImage(), Sprite.brick_exploded2.getFxImage(),animate, 16);
        animate();
    }

    public void checkDestroyed() {
        Entity t = BombermanGame.getEntity(this.rectangle);
        if (t instanceof BomBang) {
            this.destroy = true;
        }
    }

    public void afterDestroy() {
        int r = generator.nextInt(28);
        if (r == 4) {
            BombermanGame.changeObjects.add(new FlameItem(x, y, Sprite.powerup_flames.getFxImage()));
        }
        if (r == 8) {
            BombermanGame.changeObjects.add(new BombItem(x, y, Sprite.powerup_bombs.getFxImage()));
        }

        if (r == 12) {
            BombermanGame.changeObjects.add(new SpeedItem(x, y, Sprite.powerup_speed.getFxImage()));
        }

        BombermanGame.changeObjects.remove(this);
        //System.out.println(BombermanGame.changeObjects.size());
    }
}
