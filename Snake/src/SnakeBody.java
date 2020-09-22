import java.awt.*;

public class SnakeBody {
    private int x, y, width, height;
    public SnakeBody(int x, int y, int tileSize){
        this.x = x;
        this.y = y;
        width = tileSize;
        height = tileSize;

    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x * width, y * height, width, height);
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }
}
