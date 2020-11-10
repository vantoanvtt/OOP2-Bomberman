package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Mob {

    protected BombermanGame game = new BombermanGame();
    protected Keyboard _input = new Keyboard();
    protected int _animate = 0;
    public static List<Bomb> listBom = new ArrayList<>();
    public static List<BomBang> listBomBang = new ArrayList<>();

    protected static double speed = 1.0;
    protected static int max_bomb = 1;
    protected static boolean flame = false;

    public Bomber(int x, int y, Image img, Keyboard _input) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this._input = _input;
        this.rectangle = new Rectangle(this.x, this.y , (int) (img.getWidth()), (int) img.getHeight());
    }


    @Override
    public void update() {
        checkDead();
        checkItem();
        if(_alive == false) {
            afterKill();
            return;
        }
        animate();

        calculateMove();

        chooseSprite();

        showBom();
    }

    //dùng để cho vào hàm chọn hình ảnh
    public void animate() {
        if (_animate > 6000) _animate = 0;
        else _animate++;
    }


    //chọn hình ảnh khi di chuyển
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
        double xa = 0, ya = 0;
        if(_input.up) ya-= speed;
        if(_input.down) ya+=speed*1.34;
        if(_input.left) xa-=speed;
        if(_input.right) xa+=speed*1.34;

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
        if (!_alive) return;
        this._alive = false;

        this.img = Sprite.player_dead1.getFxImage();

    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected boolean canMove(Rectangle rec) {
        Entity a = game.getEntity(rec);
        if (a != null && (a instanceof Wall || a instanceof Brick)) {
            return false;
        }
        else return true;
    }

    //thêm bom vào stillobjects
    public void showBom() {
        if (_input.space) {
            if (listBom.size() < max_bomb) {
                Bomb bom = new Bomb(rounding(x*1.0 / Sprite.SCALED_SIZE), rounding(y*1.0 / Sprite.SCALED_SIZE)
                        , Sprite.bomb.getFxImage(), 200);
                if (listBom.size() == 0 ) {
                    listBom.add(bom);
                    BombermanGame.stillObjects.add(bom);
                } else {
                    if (checkListBom(bom) == false) {
                        listBom.add(bom);
                        BombermanGame.stillObjects.add(bom);
                    }
                }

            }

        }
    }

    //kiểm tra những vị trí quả bom hiện tại
    public boolean checkListBom(Bomb b) {
        for (int i = 0; i < listBom.size(); i++) {
            if (b.getRectangle().intersects(listBom.get(i).rectangle)) {
                return true;
            }
        }

        return false;
    }

    //set đếm ngược cho bom và tạo bom nổ
    public static void deadLineAllBom() {
        for (int i = 0; i < listBom.size(); i++) {
            Bomb t = listBom.get(i);
            if (t.time > 0) {
                listBom.get(i).deadLineBom();
            } else {
                listBom.remove(i);
                BombermanGame.stillObjects.remove(t);
                if (flame == false) {
                    listBomBang.add(new BomBang(t.x, t.y,25));
                } else {
                    listBomBang.add(new BomBang(t.x, t.y,25, 0));
                }

                BombermanGame.stillObjects.addAll(listBomBang);
            }

        }

        for (int i = 0; i < listBomBang.size(); i++) {
            BomBang t = listBomBang.get(i);

            if (t.time > 0) {
                listBomBang.get(i).deadLineBomBang();
            } else {
                listBomBang.remove(i);
                BombermanGame.stillObjects.remove(t);
            }
        }
    }

    //kiểm tra chết
    public void checkDead() {
        Entity c = BombermanGame.getEntity(rectangle);
        if (c instanceof BomBang) {
            //System.out.println("player die");
            kill();
        }

        if (BombermanGame.checkCollisionEnemy(rectangle)) {
            //System.out.println("player die");
            kill();
        }
    }

    //check va cham item
    public void checkItem() {
        Entity t = BombermanGame.checkCollisionItem(this.rectangle);
        if (t instanceof SpeedItem) {
            speed = 1.5;
            ((SpeedItem) t).afterCollision();
        }

        if (t instanceof FlameItem) {
            flame = true;
            ((FlameItem) t).afterCollision();
        }

        if (t instanceof BombItem) {
            max_bomb = 2;
            ((BombItem) t).afterCollision();
        }
    }

    //làm tròn số để đặt bom
    public int rounding(double s) {
        if (s - (int) s > 0.5) {
            return (int) (s + 1);
        } else {
            return (int) s;
        }
    }


}
