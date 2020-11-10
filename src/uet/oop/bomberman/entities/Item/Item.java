package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

import java.awt.*;

public  class Item extends Entity {
    protected int time = -1; // -1 is infinite, duration in lifes
    protected boolean _active = false;
    protected int _level;

    public boolean collision(Rectangle rec) {
        if (rectangle.intersects(rec)) {
            return true;
        } else {
            return false;
        }
    }

    public void afterCollision() {
        BombermanGame.changeObjects.remove(this);
    }

    @Override
    public void update() {

    }
}
