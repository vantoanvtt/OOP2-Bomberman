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
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
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


    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
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
                input.keyRelease();
                /*switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                    case SHIFT: running = false; break;
                }*/
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
        entities.add(bomberman);
    }

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
        //stillObjects.add(new )
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public static Entity getEntity(Rectangle rec) {
        //System.out.println(stillObjects.size());
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity t = stillObjects.get(i);

            if (t.getRectangle().getBounds().intersects(rec.getBounds()) && t instanceof Wall) {
                System.out.println("yes");
                return t;
            }
        }

        return null;
    }
    public static int testS(Rectangle r) {
        int result = 0;
        System.out.println(stillObjects.size());
        for (int i = 0; i < stillObjects.size(); i++) {

            if (check(r, stillObjects.get(i).getRectangle())) {
                result++;
            }
        }
        return result;
    }

    public static boolean check(Rectangle a, Rectangle b) {
        if ((a.x > b.x && a.x < b.x + b.height) && (a.y > b.y && a.y < b.y + b.width)) {
            return true;
        } else {
            return false;
        }
    }

}

