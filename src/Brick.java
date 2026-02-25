import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Complete the Brick class.
 *  Use ClassTester to test your code.
 *
 */
public class Brick extends Canvas
{
    //these are instance variables
    private int x, y, w, h;
    public Brick()
    {
        x=0;
        y=0;
        w=100;
        h=100;
    }


    public Brick( int ex, int wy, int wd, int ht)
    {
        x=ex;
        y=wy;
        w=wd;
        h=ht;
    }


    // All Bricks will have all of these set and get methods
    //these methods are complete
    public int getX( ){ return x; }
    public void setX( int ex ){ x = ex; }
    public int getY( ){ return y; }
    public void setY( int wy ){ y = wy; }
    public int getW(){ return w; }
    public int getH(){ return h; }


    //code to see if this Brick intersects with the other Brick goes here
    public boolean intersects( Brick other )
    {
        /* Easy Way:
         *        Copy and Paste the following URL in Google Chrome
         *    https://docs.oracle.com/javase/8/docs/api/java/awt/Rectangle.html
         *    make new Rectangle, using this Brick's x,y,w,h
         *    make new Rectangle, using the other Bricks's x,y,w,h
         *    return if the two Rectangles intersect, use the Rectangle's intersects method.
         */
        Rectangle dog= new Rectangle(this.getX(),this.getY(),this.getW(),this.getH());
        Rectangle rec= new Rectangle(other.getX(), other.getY(),other.getW(),other.getH());
        if(dog.intersects(rec)){
            return true;
        }
        return false;
    }
    //code to draw the Brick will go here
    public void paint( Graphics window )
    {
//     window.drawString("Brick Class ", 50, 150 );
        //Draw the Brick with Graphics methods
        //or use an Image
    /*
    window.setColor(Color.RED);
    window.fillRect(getX(), getY(), getW(), getH());
    window.setColor(Color.BLACK);
    window.drawRect(getX(), getY(), getW(), getH());
    */
//     window.fillRect(getX(), getY(), getW(), getH());
//     window.setColor(Color.BLACK);
//     window.drawRect(getX(), getY(), getW(), getH());
        //Find an Image for the Brick.
    /*
    Graphics2D g2 = (Graphics2D) window;
    Image img1 = Toolkit.getDefaultToolkit().getImage("brick.png"); //use .gif or .png, you can choose the image
    g2.drawImage(img1, getX(), getY(), getW(), getH(), this);
    */
//        Color myColor = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
//        window.setColor(myColor);
//        window.fillRect(getX(), getY(), getW(), getH());
//        window.setColor(myColor);
//        window.drawRect(getX(), getY(), getW(), getH());
     Graphics2D g2 = (Graphics2D) window;
     Image img1 = Toolkit.getDefaultToolkit().getImage("LUDO.png"); //use .gif or .png, you can choose the image
     g2.drawImage(img1, getX(), getY(), getW(), getH(), this);
    }
}
