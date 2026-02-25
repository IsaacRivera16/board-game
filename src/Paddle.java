import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Complete the Paddle class.
 *  Use ClassTester to test your code.
 *
 */


public class Paddle extends Brick
{
    private int speed; // a paddle has to have a speed to make it move


    public Paddle( int ex, int wy, int wd, int ht, int sp)
    {
        super(ex,wy,wd,ht);
        speed=sp;
    }


    public void moveLeft()
    {
        setX( getX() - speed ); // because a paddle is a block it has the setX and getX methods


        // add the code to keep the paddle from going off the screen to the left.
        if(this.getX()<=-30){
            this.moveRight();
        }


    }
    public void moveRight()
    {
        //add code to move to the right
        setX( getX() + speed );
        if(this.getX()>=733){
            this.moveLeft();
        }
        // keep the paddle from going off the screen to the right.
    }


    //overidde paint to draw your Paddle
    public void paint( Graphics window )
    {
//     window.drawString("Paddle Class ", 50, 150 );
        //Draw the Paddle with graphics methods
        //or use an Image
    /*
    window.setColor(Color.RED);
    window.fillRect(getX(), getY(), getW(), getH());
    window.setColor(Color.BLACK);
    window.drawRect(getX(), getY(), getW(), getH());
    */
        //Find an Image for your Paddle.
    /*
    Graphics2D g2 = (Graphics2D) window;
     Image img1 = Toolkit.getDefaultToolkit().getImage("paddle.png"); //use .gif or .png, you can choose the image
     g2.drawImage(img1, getX(), getY(), getW(), getH(), this);
     */
        Graphics2D g2 = (Graphics2D) window;
        Image img1 = Toolkit.getDefaultToolkit().getImage("paddle.png"); //use .gif or .png, you can choose the image
        g2.drawImage(img1, getX(), getY(), getW(), getH(), this);
    }
}
