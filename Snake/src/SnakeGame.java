import javax.swing.*;

public class SnakeGame {
    public SnakeGame(){
        JFrame frame = new JFrame();
        Gamepanel gamepanel = new Gamepanel();
        frame.add(gamepanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Snake Game!");

    }

    public static void main(String[] args){
        new SnakeGame();
    }
<<<<<<< HEAD
=======
    public void stop(){
        game_over = true;
    }
    public void tick(){

    }
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < WIDTH/10; i++) g.drawLine(i * 10, 0, i * 10, HEIGHT);
        for(int j = 0; j < HEIGHT/10; j++) g.drawLine(0, j*10, WIDTH, j * 10);

    }

    public void run(){

    }

}

class Controller extends KeyAdapter{

>>>>>>> 0367f1d98fc3981b7f2e1c0ab91d2fc13ae1da1a
}
