package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.enemys.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {

    protected int _animate = 0;

    public Doll(int x, int y, Image img) {
        super(x, y, img, 2.0,100);
        this._ai = new AILow();
        this.MAX_STEPS = Sprite.DEFAULT_SIZE * 2;
    }


    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 1:
                img = Sprite.doll_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.doll_right1.getFxImage(), Sprite.doll_right2.getFxImage(), _animate, 20);
                }
                break;
            case 3:
                img = Sprite.doll_left1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.doll_left1.getFxImage(), Sprite.doll_left2.getFxImage(), _animate, 20);
                }
                break;
            default:
                img = Sprite.doll_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.doll_right1.getFxImage(), Sprite.doll_right2.getFxImage(), _animate, 20);
                }
                break;
        }
    }





    //dùng để cho vào hàm chọn hình ảnh
    public void animate() {
        if (_animate > 6000) _animate = 0;
        else _animate++;
    }



    @Override
    public void kill() {
        this._alive = false;
        img = Sprite.doll_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

}
