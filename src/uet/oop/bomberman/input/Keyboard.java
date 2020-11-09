package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard {

    public boolean up, down, left, right, space;

    public void setDown(boolean down) {
        this.down = down;
    }

    public Keyboard() {
        up = false;
        down = false;
        right = false;
        left = false;
        space = false;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void keyRelease() {
        up = false;
        down = false;
        left = false;
        right = false;
        space = false;
    }

}
