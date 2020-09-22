import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {
    private int score;
    private int WIDTH = 1280, HEIGHT = 800;

    public EndScreen(int score, Thread thread){
        System.out.println("End!");
        this.score = score;
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        JLabel msg = new JLabel("Game Over!");
        JLabel high_score = new JLabel("Highest Score: " + Integer.toString(score));
        add(msg);
        add(high_score);
        /*
        try {+
            System.out.println("sss");
            thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */


    }
}
