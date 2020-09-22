import java.awt.*;

public class Apple {
    private int x, y, width, height;
    public Apple(int x, int y, int tileSize){
        this.x = x;
        this.y = y;
        width = tileSize;
        height = tileSize;
    }
    public void tick(){
    }
    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x * width, y * height, width, height);
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

}
