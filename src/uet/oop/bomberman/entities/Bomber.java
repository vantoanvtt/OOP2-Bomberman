package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;

public class Bomber extends Mob {

    protected BombermanGame game = new BombermanGame();
    protected Keyboard _input = new Keyboard();
    protected int _animate = 0;
    protected Rectangle prvRec;
    public Bomber(int x, int y, Image img, Keyboard _input) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this._input = _input;
        this.rectangle = new Rectangle(this.x, this.y , (int) img.getWidth(), (int) img.getHeight());
        //System.out.println(this.rectangle.getBounds2D());
        //System.out.println(BombermanGame.testS(this.rectangle));
    }


    @Override
    public void update() {
        if(_alive == false) {
            afterKill();
            return;
        }
        animate();

        calculateMove();

        chooseSprite();
    }

    public void animate() {
        _animate++;
    }

    private void chooseSprite() {
        switch(_direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage(), _animate, 20);
                }
                break;
            case 1:
                img = Sprite.player_right.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage(), _animate, 20);
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage(), _animate, 20);
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage(), _animate, 20);
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage(), _animate, 20);
                }
                break;
        }
    }

    @Override
    protected void calculateMove() {
        int xa = 0, ya = 0;
        if(_input.up) ya--;
        if(_input.down) ya++;
        if(_input.left) xa--;
        if(_input.right) xa++;

        if(xa != 0 || ya != 0)  {
                move(xa * Sprite.PLAYERSPEED, ya * Sprite.PLAYERSPEED);

            _moving = true;
        } else {
            _moving = false;
        }

    }

    @Override
    protected void move(double xa, double ya) {
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;

        if(canMove(this.rectangle)) { //separate the moves for the player can slide when is colliding
            if (canMove(new Rectangle(x, (int) (y + ya), (int) img.getWidth(), (int) img.getHeight())) ) {
                y += ya;
                this.rectangle = new Rectangle(x, y , (int) img.getWidth(), (int) img.getHeight());
            }

        }

        if(canMove(this.rectangle)) {
            if (canMove(new Rectangle((int) (x + xa), y, (int) img.getWidth(), (int) img.getHeight())) ) {
                x += xa;
                this.rectangle = new Rectangle(x, y , (int) img.getWidth(), (int) img.getHeight());
            }

        }
    }

    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected boolean canMove(Rectangle rec) {
        Entity a = game.getEntity(rec);
        if (a != null) {
            return false;
        }
        else return true;
    }
}
