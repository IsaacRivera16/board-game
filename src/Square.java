import java.awt.*;

public class Square extends Canvas {
    int x, y, w, h;
    private int[] center;
    private Color color;
    private boolean safe;

    public Square(int xx, int yy, int ww, int hh, Color co){
        x=xx;
        y=yy;
        w=ww;
        h=hh;
        color=co;
    }

    public int[] getCenter() {
        center = new int[2];
        center[0]=x+w/2;
        center[1]=y+h/2;
        return center;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color c){
        color=c;
    }

    public void paint(Graphics window){
        window.setColor(color);
        window.
        window.fillRect(x,y,w,h);
    }
}
