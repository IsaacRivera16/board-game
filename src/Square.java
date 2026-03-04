import java.awt.*;

//MAKE FINISH BOOLEAN AND METHODS


public class Square extends Canvas {
    int x, y, w, h;
    private int[] center;
    private Color color;
    boolean safe;
    boolean upTurn;
    boolean downTurn;
    boolean rightTurn;
    boolean leftTurn;
    boolean blueTurn;
    boolean redTurn;
    boolean yellowTurn;
    boolean greenTurn;




    public Square(int xx, int yy, int ww, int hh, Color co){
        x=xx;
        y=yy;
        w=ww;
        h=hh;
        color=co;
        safe=false;
        upTurn=false;
        downTurn=false;
        rightTurn=false;
        leftTurn=false;
        blueTurn=false;
        redTurn=false;
        yellowTurn=false;
        greenTurn=false;
    }




    public int getCenter() {




        return x+w/2;
    }


    public void setSafe() {
        safe = true;
    }
    public void setUpTurn(){
        upTurn= true;
    }
    public void setDownTurn(){
        downTurn= true;
    }
    public void setRightTurn(){
        rightTurn= true;
    }
    public void setLeftTurn(){
        leftTurn= true;
    }
    public void setBlueTurn(){
        blueTurn =true;
    }
    public void setRedTurn(){
        redTurn =true;
    }
    public void setGreenTurn(){
        greenTurn =true;
    }
    public void setYellowTurn(){
        yellowTurn =true;
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




