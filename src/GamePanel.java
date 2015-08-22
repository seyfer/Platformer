import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Created by seyfer on 22.08.15.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener
{
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private TileMap tileMap;
    private Player player;

    public GamePanel() {
        super();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    @Override
    public void run() {

        init();

        long startTime;
        long urdTime;
        long waitTime;

        while (running) {
            startTime = System.nanoTime();

            update();
            render();
            draw();

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        tileMap = new TileMap("testmap.txt", 32);
        player = new Player(tileMap);
        player.setX(50);
        player.setY(50);
    }

    private void update() {
        tileMap.update();
        player.update();
    }

    private void render() {
        tileMap.draw(g);
        player.draw(g);
    }

    private void draw() {
        Graphics g2 = getGraphics();

//        System.out.println(image.getHeight());

        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (code == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (code == KeyEvent.VK_SPACE) {
            player.setJumping(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (code == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
//        if (code == KeyEvent.VK_SPACE) {
//            player.setJumping(false);
//        }
    }
}
