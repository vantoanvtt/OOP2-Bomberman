package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import java.awt.*;


public class Portal extends Entity {

    protected boolean active = false;

    public Portal(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.rectangle = new Rectangle(x ,y , (int) img.getWidth(), (int) img.getHeight());
    }
    @Override
    public void update() {

    }

    public void check() {

    }


}
