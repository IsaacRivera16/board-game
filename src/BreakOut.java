import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;


class BreakOut extends JPanel implements Runnable, KeyListener
{
    private boolean[] keys;
    private Brick background;
    private Ball r1,r2,r3,r4,g1,g2,g3,g4,y1,y2,y3,y4,b1,b2,b3,b4;
    private ArrayList< Brick > bricks;
    private boolean gameOver=false;
    private boolean gameStart=false;
    private boolean alive=false;
    private Brick[][] grid;

    public BreakOut() // create all instance in here
    {


        keys = new boolean[5];
        background = new Brick(0,0,800,800);
        r1= new Ball(203,90,30,30,"red");
        r2= new Ball(98,90,30,30,"red");
        r3= new Ball(203,195,30,30,"red");
        r4= new Ball(98,195,30,30,"red");

        g1= new Ball(678,90,30,30,"green");
        g2= new Ball(573,90,30,30,"green");
        g3= new Ball(678,195,30,30,"green");
        g4= new Ball(573,195,30,30,"green");

        y1= new Ball(678,565,30,30,"yellow");
        y2= new Ball(573,565,30,30,"yellow");
        y3= new Ball(678,670,30,30,"yellow");
        y4= new Ball(573,670,30,30,"yellow");

        b1= new Ball(203,565,30,30,"blue");
        b2= new Ball(98,565,30,30,"blue");
        b3= new Ball(203,670,30,30,"blue");
        b4= new Ball(98,670,30,30,"blue");



        addKeyListener( this );    //
        setFocusable( true );     // Do NOT DELETE these three lines
        new Thread(this).start();
    }

    public void paint( Graphics window )// all other paint methods and game logic goes in here.
    {
        background.paint(window);
        r1.paint(window);
        r2.paint(window);
        r3.paint(window);
        r4.paint(window);

        g1.paint(window);
        g2.paint(window);
        g3.paint(window);
        g4.paint(window);

        b1.paint(window);
        b2.paint(window);
        b3.paint(window);
        b4.paint(window);

        y1.paint(window);
        y2.paint(window);
        y3.paint(window);
        y4.paint(window);
    }
    // only edit if you would like to add more key functions
    public void keyPressed(KeyEvent e)
    {
        if( e.getKeyCode()  == KeyEvent.VK_UP )
        {
            keys[0]=true;
        }
        if( e.getKeyCode()  == KeyEvent.VK_LEFT )
        {
            keys[1]=true;
        }
        if( e.getKeyCode()  == KeyEvent.VK_RIGHT )
        {
            keys[2]=true;

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[4] = true;
        }
    }
    // do not edit anything from this point on!!!
    public void keyTyped(KeyEvent e)
    {
        keyPressed( e );
    }
    public void keyReleased(KeyEvent e)    {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[4] = false;
        }
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
