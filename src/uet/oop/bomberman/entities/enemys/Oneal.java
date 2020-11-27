package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.BomBang;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Mob;
import uet.oop.bomberman.entities.enemys.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;


public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
        super(x, y, img, 2.0,150);
        this._ai = new AIMedium((Bomber) BombermanGame.player, this);
        this._speed = 2;
        this.MAX_STEPS = Sprite.DEFAULT_SIZE;
    }

    

    @Override
    public void kill() {
        this._alive = false;
        img = Sprite.oneal_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 1:
                img = Sprite.oneal_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.oneal_right1.getFxImage(), Sprite.oneal_right2.getFxImage(), _animate, 20);
                }
                break;
            case 3:
                img = Sprite.oneal_left1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.oneal_left1.getFxImage(), Sprite.oneal_left2.getFxImage(), _animate, 20);
                }
                break;
            default:
                img = Sprite.oneal_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.oneal_right1.getFxImage(), Sprite.oneal_right2.getFxImage(), _animate, 20);
                }
                break;
        }
    }
    
}
