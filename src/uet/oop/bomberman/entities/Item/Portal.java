package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BomBang;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;


public class Portal extends Entity {

    protected static int state = 0;

    public Portal(int x_, int y_, Image img) {
        this.x = x_ * Sprite.SCALED_SIZE;
        this.y = y_ * Sprite.SCALED_SIZE;
        this.img = img;
        this.rectangle = new Rectangle(x ,y , (int) img.getWidth(), (int) img.getHeight());
    }
    @Override
    public void update() {
        checkState();
    }

    public int getState() {
        return state;
    }

    public void checkState() {
        if (this.state == 0) {
             Entity t = BombermanGame.getEntity(this.rectangle);
             if (t instanceof BomBang) {
                 state = 1;
                 this.img = Sprite.portal.getFxImage();
             }
        }
        if (BombermanGame.entities.isEmpty()) {
            state = 2;
        }
    }

}
