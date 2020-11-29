package uet.oop.bomberman.entities.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.enemys.ai.AILow;
import uet.oop.bomberman.entities.enemys.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Condoria extends Enemy {

    protected int _animate = 0;

    public Condoria(int x, int y, Image img) {
        super(x, y, img, 1.0,100);
        this._ai = new AIMedium((Bomber) BombermanGame.player, this);
        this.MAX_STEPS = Sprite.DEFAULT_SIZE * 4;
    }


    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 1:
                img = Sprite.kondoria_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.kondoria_right1.getFxImage(), Sprite.kondoria_right2.getFxImage(), _animate, 20);
                }
                break;

            case 3:
                img = Sprite.kondoria_left1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.kondoria_left1.getFxImage(), Sprite.kondoria_left2.getFxImage(), _animate, 20);
                }
                break;
            default:
                img = Sprite.kondoria_right1.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.kondoria_right1.getFxImage(), Sprite.kondoria_right2.getFxImage(), _animate, 20);
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
        img = Sprite.kondoria_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

    @Override
    public boolean canMove(Rectangle rec) {
        Entity a = BombermanGame.getEntity(rec);
        if (a != null && (a instanceof Wall)) {
            if (a.getXTile() == 0 || a.getXTile() == 12 || a.getYTile() == 0 || a.getYTile() == 30) {
                return false;
            }
        }
        return true;
    }
}
