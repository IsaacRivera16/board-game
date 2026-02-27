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
        safe=false;
    }

    public int getCenter() {

        return x+w/2;
    }

    public void setSafe(boolean safe) {
        safe = true;
    }

    public Color getColor() {
        return color;

    }
    public void setColor(Color c){
        color=c;
    }

    public void paint(Graphics windo){
        Graphics2D window = (Graphics2D) windo;
        int re=color.getRed();
        int gr=color.getGreen();
        int bl=color.getBlue();



        Color my = new Color(Math.max(re-50,100),Math.max(gr-50,100),Math.max(bl-50,100));
        my=new Color(100,100,100);

        window.setColor(my);
        window.setStroke(new BasicStroke(5));
        window.drawRect(x,y,w,h);
        window.setColor(color);
        window.fillRect(x,y,w,h);
    }
}
