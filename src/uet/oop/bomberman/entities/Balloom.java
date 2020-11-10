package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;


public class Balloom extends Mob {

    public Balloom(int x, int y, Image img) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this.rectangle = new Rectangle(this.x, this.y , (int) img.getWidth(), (int) img.getHeight());
    }

    protected static int timedead = 60;
    @Override
    public void update() {
        checkDead();
        if(_alive == false) {
            kill();
            if (timedead < 1) {
                afterKill();
            }
        }

    }


    public void checkDead() {
        Entity c = BombermanGame.getEntity(rectangle);

        if (c instanceof BomBang) {
            kill();
        }
    }

    @Override
    protected void calculateMove() {

    }

    @Override
    protected void move(double xa, double ya) {

    }

    @Override
    public void kill() {
        this._alive = false;
        img = Sprite.balloom_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

    @Override
    protected boolean canMove(Rectangle rec) {
        return false;
    }

}
