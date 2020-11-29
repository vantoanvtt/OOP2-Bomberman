package uet.oop.bomberman.entities.enemys;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.enemys.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import javafx.scene.image.Image;

public abstract class Enemy extends Mob {

    protected int _points;

    protected double _speed;
    protected AI _ai;

    //necessary to correct move
    protected double MAX_STEPS;
    protected final double rest;
    protected double _steps;

    protected int timedead = 60;
    protected int _animate = 0;

    public Enemy(int x, int y , Image img, double speed, int points) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        _points = points;
        _speed = speed;

        MAX_STEPS = Sprite.DEFAULT_SIZE * 5 / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;
        this.rectangle = new Rectangle(this.x, this.y ,(int) img.getWidth(), (int) img.getHeight());
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Render & Update
    |--------------------------------------------------------------------------
     */
    @Override
    public void update() {
        //animate();
        checkDead();
        if(_alive == false) {
            kill();
            if (timedead < 0) {
                afterKill();
            }
        }


        if(_alive) {
            chooseSprite();
            calculateMove();
        }


    }

    public void animate() {
        if (_animate > 6000) _animate = 0;
        else _animate++;
    }


    @Override
    public void calculateMove() {
        int xa = 0, ya = 0;
        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = MAX_STEPS;
        }

        if(_direction == 0) ya--;
        if(_direction == 2) ya++;
        if(_direction == 3) xa--;
        if(_direction == 1) xa++;

        if(canMove(new Rectangle(x, (y + ya),(int) img.getWidth(),(int) img.getHeight()))
                && canMove(new Rectangle((x + xa), y, (int) img.getWidth(),(int) img.getHeight()))) {
            _steps -= 1 + rest;
            move(xa * _speed, ya * _speed);
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if(!_alive) return;

        y += ya;
        x += xa;
        this.rectangle = new Rectangle(x, y , 32, 32);
    }

    @Override
    public boolean canMove(Rectangle rec) {

        Entity a = BombermanGame.getEntity(rec);
        Entity t = BombermanGame.checkCollisionItem(rec);
        if (a != null && (a instanceof Wall || a instanceof Bomb)) {
            return false;
        }
        if (t != null && (t instanceof Portal || t instanceof Brick)) {
            return false;
        }
        return true;
    }

    @Override
    public void kill() {

    }


    @Override
    protected void afterKill() {

    }

    protected abstract void chooseSprite();

    public void checkDead() {
        Entity c = BombermanGame.getEntity(rectangle);

        if (c instanceof BomBang) {
            kill();
        }
    }
}
