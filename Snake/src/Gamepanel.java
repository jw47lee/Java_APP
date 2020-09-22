import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Gamepanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 1280, HEIGHT = 800;
    private Thread thread;
    private boolean running;
    private int x = 10, y = 10, size = 5;
    private int ticks = 0;
    private int fps = 200000;
    private boolean rightD = false;
    private boolean leftD = false;
    private boolean upD = false;
    private boolean downD = false;
    private SnakeBody b;
    private ArrayList<SnakeBody> snake;
    private Apple apple;
    private ArrayList<Apple> apples;
    private Random r;
    private JPanel title_scene;
    private JPanel end_scene;
    private int level;
    private Timer timer;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private int score = 0;
    private int current_level_apple = 0;

    public Gamepanel() {
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake = new ArrayList<SnakeBody>();
        apples = new ArrayList<Apple>();
        r = new Random();
        game_start();
    }
    public void init_lvl_one(){
        fps = 300000;
        current_level_apple = 5;
        for(int i = 0; i < Math.abs(apples.size() - current_level_apple); i++){
            //System.out.println(i);
            apple = new Apple((5 * i) + 20, (5 * i) + 20, 10);
            apples.add(apple);
        }
    }
    public void init_lvl_two(){
        fps = 200000;
        current_level_apple = 10;
        for(int i = 0; i < Math.abs(apples.size() - current_level_apple); i++){
            //System.out.println(i);
            apple = new Apple((5 * i) + 20, (5 * i) + 20, 10);
            apples.add(apple);
        }
    }
    public void init_lvl_three(){
        fps = 100000;
        current_level_apple = 15;
        for(int i = 0; i < Math.abs(apples.size() - current_level_apple); i++){
            //System.out.println(i);
            apple = new Apple((5 * i) + 20, (5 * i) + 20, 10);
            apples.add(apple);
        }
    }
    public void game_start(){
        rightD = true;
        init_lvl_one();
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public int create_random_x(){
        return r.nextInt(127);
    }
    public int create_random_y(){
        return r.nextInt(79);
    }
    public void checkCollision(){
        if(x < 0 || x > 127 || y < 0 || y > 79){
            System.out.println("Game over");
            game_over();
        }
    }
    public void game_over(){
        System.out.println(score);
        //getGraphics().drawString("SUP", 10, 10);
        running = false;
    }

    public void tick(){
        if(snake.size() == 0){
            b = new SnakeBody(x, y, 10);
            snake.add(b);
        }
        ticks++;
        if(ticks > fps){
            if(rightD) x++;
            if(leftD) x--;
            if(upD) y--;
            if(downD) y++;
            ticks = 0;
            b = new SnakeBody(x, y, 10);
            snake.add(b);

            if(snake.size() > size){
                snake.remove(0);
            }
        }
        //create apple
        if(apples.size() < current_level_apple){
            int x = create_random_x();
            int y = create_random_y();
            apple = new Apple(x, y, 10);
            apples.add(apple);
        }
        // eat apple
        for(int i = 0; i < apples.size(); i++){
            if(x == apples.get(i).getx() && y == apples.get(i).gety()){
                size++;
                apples.remove(i);
                score++;
                i++;
            }
        }

        for(int i = 0; i < snake.size(); i++){
            if(x == snake.get(i).getx() && y == snake.get(i).gety()){
                if(i != snake.size() - 1){
                    game_over();
                }
            }
        }
        checkCollision();

    }
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < snake.size(); i++){
            snake.get(i).draw(g);
        }
        for(int i = 0; i < apples.size(); i++){
            apples.get(i).draw(g);
        }
    }

    public void run(){
        while(running){
            synchronized(pauseLock){
                if(!running){
                    break;
                }
                if(paused){
                    try{
                        synchronized (pauseLock){
                            pauseLock.wait();
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                    if(!running){
                        break;
                    }
                }
            }
            tick();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_1){
            if(current_level_apple < 6){
                init_lvl_one();
            }
        }
        if(key == KeyEvent.VK_2){
            if(current_level_apple < 11){
                init_lvl_two();
            }
        }
        if(key == KeyEvent.VK_3){
            if(current_level_apple < 16){
                init_lvl_three();
            }
        }
        if(key == KeyEvent.VK_RIGHT){
            if(leftD){
                game_over();
            }
            rightD = true;
            upD = false;
            downD = false;
        }
        if(key == KeyEvent.VK_LEFT){
            if(rightD){
                game_over();
            }
            leftD = true;
            upD = false;
            downD = false;
        }
        if(key == KeyEvent.VK_UP){
            if(downD){
                game_over();
            }
            upD = true;
            leftD = false;
            rightD = false;
        }
        if(key == KeyEvent.VK_DOWN) {
            if(upD){
                game_over();
            }
            downD = true;
            leftD = false;
            rightD = false;
        }
        if(key == KeyEvent.VK_Q){
            System.out.println("Press Q, stop the game!");
            game_over();
        }
        if(key == KeyEvent.VK_P){
            // trying to pause
            if(!paused) {
                pause();
            }
            else{ // trying to resume
                resume();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void pause(){
        paused = true;
    }
    public void resume(){
        synchronized (pauseLock){
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
