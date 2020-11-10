package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    public static boolean sp = true;

    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> changeObjects = new ArrayList<>();
    private Keyboard input = new Keyboard();


    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    input.setUp(true); break;
                    case DOWN:  input.setDown(true); break;
                    case LEFT:  input.setLeft(true); break;
                    case RIGHT: input.setRight(true); break;
                    case SPACE: input.setSpace(true); break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //input.keyRelease();
                switch (event.getCode()) {
                    case UP:    input.setUp(false); break;
                    case DOWN:  input.setDown(false); break;
                    case LEFT:  input.setLeft(false); break;
                    case RIGHT: input.setRight(false); break;
                    case SPACE: input.setSpace(false); break;
                }
            }
        });
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), input);
        Entity testBalloom = new Balloom(6,6,Sprite.balloom_right1.getFxImage());
        entities.add(bomberman);
        entities.add(testBalloom);
    }

    // tạo bản đò
    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }

        // them de test
        changeObjects.add(new Brick(8,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(4,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(5,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(6,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(7,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(8,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(9,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(10,8,Sprite.brick.getFxImage()));
        changeObjects.add(new Brick(6,10,Sprite.brick.getFxImage()));

    }
    public void update() {
        for (int i = 0; i < changeObjects.size(); i++) {
            changeObjects.get(i).update();
        }
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Bomber.deadLineAllBom();
        stillObjects.forEach(g -> g.render(gc));
        changeObjects.forEach(g -> g.render(gc));
        //Bomber.listBom.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));

    }

    //kiểm tra chạm wall va brick
    public static Entity getEntity(Rectangle rec) {
        //System.out.println(stillObjects.size());
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity t = stillObjects.get(i);
            if (t instanceof BomBang) {
                if (((BomBang) t).handleCollisionBomBang(rec)) {
                    return t;
                }
            }
            if (t instanceof Bomb) {

            } else {
                if (t.getRectangle().getBounds().intersects(rec.getBounds())) {
                    if (t instanceof Wall ) {
                        return t;
                    }
                    //System.out.println("yes");

                }
            }

        }

        for (int i = 0; i < changeObjects.size(); i++) {
            Entity t = changeObjects.get(i);
            if (t != null ) {
                if (t.getRectangle().getBounds().intersects(rec.getBounds())) {
                    if (t instanceof Brick) {
                        return t;
                    }
                }

            }
        }

        return null;
    }

    //kiểm tra chạm quái
    public static boolean checkCollisionEnemy(Rectangle rec) {
        //System.out.println(stillObjects.size());
        for (int i = 0; i < entities.size(); i++) {
            Entity t = entities.get(i);
            if (!(t instanceof Bomber)) {
                if (t.getRectangle().intersects(rec)) {
                    return true;
                }
            }
        }
        return false;
    }

    //kiểm tra chạm item
    public static Entity checkCollisionItem(Rectangle rec) {
        for (int i = 0; i < changeObjects.size(); i++) {
            Entity t = changeObjects.get(i);
            if (! (t instanceof Brick)) {
                if (t.getRectangle().intersects(rec)) {
                    return t;
                }
            }
        }
        return null;
    }


}

