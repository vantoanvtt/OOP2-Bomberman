package uet.oop.bomberman.entities;

import javafx.stage.Screen;

import java.awt.*;

public abstract class Mob extends Entity {
    //protected Board _board;
    protected int _direction = -1;
    protected boolean _alive = true;
    protected boolean _moving = false;

    public Mob(int x, int y) {
        this.x = x;
        this.y = y;

        //_board = board;
    }

    public Mob() {

    }



    @Override
    public abstract void update();

    //@Override
    //public abstract void render(Screen screen);

    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove(Rectangle rec);

    public boolean isAlive() {
        return _alive;
    }

    public boolean isMoving() {
        return _moving;
    }

    public int getDirection() {
        return _direction;
    }

    /*protected double getXMessage() {
        return (_x * Game.SCALE) + (_sprite.SIZE / 2 * Game.SCALE);
    }

    protected double getYMessage() {
        return (_y* Game.SCALE) - (_sprite.SIZE / 2 * Game.SCALE);
    }*/

}
