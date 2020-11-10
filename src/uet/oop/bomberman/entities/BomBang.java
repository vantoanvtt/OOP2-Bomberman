package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class BomBang extends Entity {
    protected int time;
    protected Image left, right, up, down, center, leftl, rightl, downl, upl;
    protected Rectangle rectanglel, rectangler, rectanglec, rectangleu, rectangled,
                        rectanglell, rectanglerl, rectanglecl, rectangleul, rectangledl;
    public BomBang(int x, int y, Image img) {
        super(x,y,img);
    }

    //tạo bom cùi
    public BomBang(int x, int y, int time) {
        this.x = x;
        this.y = y;
        //this.img = img;
        this.time = time;
        center = Sprite.bomb_exploded.getFxImage();
        left = Sprite.explosion_horizontal_left_last.getFxImage();
        right = Sprite.explosion_horizontal_right_last.getFxImage();
        up = Sprite.explosion_vertical_top_last.getFxImage();
        down = Sprite.explosion_vertical_down_last.getFxImage();

        rectanglel = new Rectangle((int) (x - left.getWidth()), y, (int) left.getWidth(),
                (int) left.getHeight());
        rectangler = new Rectangle((int) (x + right.getWidth()), y, (int) right.getWidth(),
                (int) right.getHeight());
        rectangleu = new Rectangle(x, (int) (y - up.getHeight()), (int) up.getWidth(),
                (int) up.getHeight());
        rectangled = new Rectangle(x, (int) (y + up.getHeight()), (int) up.getWidth(),
                (int) up.getHeight());
        rectangle = new Rectangle(x, y, (int) center.getWidth(), (int) center.getHeight());


        if (handleCollision(rectanglel) == null) {
            rectanglel = null;
            left = null;
        }

        if (handleCollision(rectangler) == null) {
            rectangler = null;
            right = null;
        }

        if (handleCollision(rectangleu) == null) {
            rectangleu = null;
            up = null;
        }

        if (handleCollision(rectangled) == null) {
            rectangled = null;
            down = null;
        }


    }

    //tạo bom xịn, t truyền vào để phân biệt
    public BomBang(int x, int y, int time, int t) {
        this.x = x;
        this.y = y;
        //this.img = img;
        this.time = time;
        leftl = Sprite.explosion_horizontal_left_last2.getFxImage();
        rightl = Sprite.explosion_horizontal_right_last2.getFxImage();
        upl = Sprite.explosion_vertical_top_last2.getFxImage();
        downl = Sprite.explosion_vertical_down_last2.getFxImage();

        center = Sprite.bomb_exploded2.getFxImage();
        left = Sprite.explosion_horizontal2.getFxImage();
        right = Sprite.explosion_horizontal2.getFxImage();
        up = Sprite.explosion_vertical2.getFxImage();
        down = Sprite.explosion_vertical2.getFxImage();

        rectanglel = new Rectangle((int) (x - left.getWidth()), y, (int) left.getWidth(),
                (int) left.getHeight());
        rectangler = new Rectangle((int) (x + right.getWidth()), y, (int) right.getWidth(),
                (int) right.getHeight());
        rectangleu = new Rectangle(x, (int) (y - up.getHeight()), (int) up.getWidth(),
                (int) up.getHeight());
        rectangled = new Rectangle(x, (int) (y + up.getHeight()), (int) up.getWidth(),
                (int) up.getHeight());

        rectangle = new Rectangle(x, y, (int) center.getWidth(), (int) center.getHeight());

        rectanglell = new Rectangle((int) (x - left.getWidth() - leftl.getWidth()), y, (int) leftl.getWidth(),
                (int) leftl.getHeight());
        rectanglerl = new Rectangle((int) (x + right.getWidth() + rightl.getWidth()), y, (int) rightl.getWidth(),
                (int) rightl.getHeight());
        rectangleul = new Rectangle(x, (int) (y - up.getHeight() - upl.getHeight()), (int) upl.getWidth(),
                (int) upl.getHeight());
        rectangledl = new Rectangle(x, (int) (y + up.getHeight() + upl.getHeight()), (int) upl.getWidth(),
                (int) upl.getHeight());


        if (handleCollision(rectanglel) == null) {
            rectanglel = null;
            rectanglell = null;
            leftl = null;
            left = null;
        }

        if (handleCollision(rectangler) == null) {
            rectangler = null;
            rectanglerl = null;
            rightl = null;
            right = null;
        }

        if (handleCollision(rectangleu) == null) {
            rectangleu = null;
            rectangleul = null;
            upl = null;
            up = null;
        }

        if (handleCollision(rectangled) == null) {
            rectangled = null;
            rectangledl = null;
            downl = null;
            down = null;
        }

        if (rectanglell != null && handleCollision(rectanglell) == null) {
            rectanglell = null;
            leftl = null;
        }

        if (rectanglerl != null && handleCollision(rectanglerl) == null) {
            rectanglerl = null;
            rightl = null;
        }

        if (rectangleul != null && handleCollision(rectangleul) == null) {
            rectangleul = null;
            upl = null;
        }

        if (rectangledl != null && handleCollision(rectangledl) == null) {
            rectangledl = null;
            downl = null;
        }


    }

    //vẽ bom nổ ra màn hình
    public void render(GraphicsContext gc) {
        gc.drawImage(center, x, y);
        gc.drawImage(left, x - center.getWidth(),y);
        gc.drawImage(right, x + center.getWidth(), y);
        gc.drawImage(up, x, y - center.getHeight());
        gc.drawImage(down, x, y + center.getHeight());
        gc.drawImage(leftl, x - 2*center.getWidth(),y);
        gc.drawImage(rightl, x + 2*center.getWidth(), y);
        gc.drawImage(upl, x, y - 2*center.getHeight());
        gc.drawImage(downl, x, y + 2*center.getHeight());

    }

    //thời gin tồn tại của vụ nổ
    public void deadLineBomBang() {
        time--;
    }

    //xử lí khi va chạm với vật thể
    public Rectangle handleCollision(Rectangle t) {
        if (BombermanGame.getEntity(t) != null) {
            if (BombermanGame.getEntity(t) instanceof Wall) {
                return null;
            }
        }
        return t;
    }

    //xử lí va chạm trước khi vẽ bom và tạo vùng
    public boolean handleCollisionBomBang(Rectangle e) {
        if (rectangle.intersects(e)) {
            return true;
        }
        if (rectanglel != null && rectanglel.intersects(e)) {
            return true;
        }

        if (rectangler != null && rectangler.intersects(e)) {
            return true;
        }

        if (rectangleu != null && rectangleu.intersects(e)) {
            return true;
        }

        if (rectangled != null && rectangled.intersects(e)) {
            return true;
        }

        if (rectanglell != null && rectanglell.intersects(e)) {
            return true;
        }

        if (rectanglerl != null && rectanglerl.intersects(e)) {
            return true;
        }

        if (rectangleul != null && rectangleul.intersects(e)) {
            return true;
        }

        if (rectangledl != null && rectangledl.intersects(e)) {
            return true;
        }

        return false;
    }

    @Override
    public void update() {

    }

}
