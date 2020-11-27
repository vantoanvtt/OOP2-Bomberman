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
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.enemys.Balloom;
import uet.oop.bomberman.entities.enemys.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.map.Map;
import uet.oop.bomberman.sound.GameSound;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class BombermanGame extends Application {

    public static int LEVEL = 1;

    
    private GraphicsContext gc;
    private Canvas canvas;


    public static Entity player;

    //lưu enemy
    public static List<Entity> entities = new ArrayList<>();
    //lưu tường và cỏ
    public static List<Entity> stillObjects = new ArrayList<>();
    //lưu brick portal, item
    public static List<Entity> changeObjects = new ArrayList<>();
    //map
    private static Map map;
    public static Keyboard input = new Keyboard();


    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        // read map truoc de co width va height
            map = new Map(LEVEL);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * map.getWIDTH(), Sprite.SCALED_SIZE * map.getHEIGHT());
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


    }


    public static void restartLevel() {
        map = new Map(LEVEL);
    }
    public static void nextLevel() {
        map = new Map(++LEVEL);
    }
    public void update() {
        for (int i = 0; i < changeObjects.size(); i++) {
            changeObjects.get(i).update();
        }
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        player.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Bomber.deadLineAllBom();
        stillObjects.forEach(g -> g.render(gc));
        changeObjects.forEach(g -> g.render(gc));
        //Bomber.listBom.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        player.render(gc);
       // GameSound.getInstance().getAudio(GameSound.PLAY_GAME).play();
    }

    //kiểm tra chạm wall va bom
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
                if (t.getRectangle().getBounds().intersects(rec.getBounds())) {
                    return t;
                }
            } else {
                if (t.getRectangle().getBounds().intersects(rec.getBounds())) {
                    if (t instanceof Wall ) {
                        return t;
                    }
                    //System.out.println("yes");

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

    //kiểm tra chạm item, brick, portal
    public static Entity checkCollisionItem(Rectangle rec) {
        for (int i = 0; i < changeObjects.size(); i++) {
            Entity t = changeObjects.get(i);
            if (t.getRectangle().intersects(rec)) return t;

        }
        return null;
    }


}

