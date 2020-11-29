package uet.oop.bomberman.map;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.enemys.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map {
    protected int LEVEL;
    protected int WIDTH;
    protected int HEIGHT;

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public Map(int level) {
        BombermanGame.stillObjects.clear();
        BombermanGame.changeObjects.clear();
        BombermanGame.entities.clear();
        this.LEVEL = level;
        ReadMapToFile();
    }

    public static void addObject(char c, int x, int y) {
        switch (c) {
            case '#': BombermanGame.stillObjects.add(new Wall(x,y, Sprite.wall.getFxImage())); break;
            case '*': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.changeObjects.add(new Brick(x,y,Sprite.brick.getFxImage()));
                break;
            }
            case '2': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.entities.add(new Oneal(x, y, Sprite.oneal_right1.getFxImage()));
                break;
            }
            case '3': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.entities.add(new Condoria(x, y, Sprite.kondoria_right1.getFxImage()));
                break;
            }

            case '4': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.entities.add(new Doll(x, y, Sprite.doll_right1.getFxImage()));
                break;
            }

            case '5': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.entities.add(new Minvo(x, y, Sprite.minvo_right1.getFxImage()));
                break;
            }
            case 'p': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.player = new Bomber(x, y, Sprite.player_right.getFxImage(), BombermanGame.input);
                break;
            }
            case '1': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.entities.add(new Balloom(x,y,Sprite.balloom_right1.getFxImage()));
                break;
            }
            case ' ': BombermanGame.stillObjects.add(new Grass(x, y, Sprite.grass.getFxImage())); break;
            //test
            case 'x': {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
                BombermanGame.changeObjects.add(new Portal(x, y, Sprite.brick.getFxImage()));
                break;
            }
            case 'f': BombermanGame.stillObjects.add(new Grass(x, y, Sprite.grass.getFxImage())); break;
            //case 'x': changeObjects.add(new Portal(x,y,Sprite.portal.getFxImage())); break;
            default: {
                BombermanGame.stillObjects.add(new Grass(x,y,Sprite.grass.getFxImage()));
            }
        }
    }

    public void ReadMapToFile() {
        String ip = "res/levels/Level" + getLEVEL() + ".txt";
        File file = new File(ip);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            int level = sc.nextInt();
            int h = sc.nextInt();
            int w = sc.nextInt();

            sc.nextLine();

            char[][] obj = new char[h][w];

            for (int i = 0; i < h; i++) {
                String s = sc.nextLine();
                for (int j = 0; j < w; j++) {
                    //System.out.print(s.charAt(j));
                    addObject(s.charAt(j), j, i);
                }
               // System.out.println();
            }

            LEVEL = level;
            HEIGHT = h;
            WIDTH = w;
        } catch ( IOException e) {
            System.out.println("lỗi đọc file map");
            System.out.println(e.fillInStackTrace());
        }
        sc.close();
    }
}
