import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Ball extends Brick
{
    private String color;
    boolean safe;
    public Ball( int ex, int wy, int wd, int ht, String co)
    {
        super(ex,wy,wd,ht);
        color=co;
    }

    public void playAgain(){
        setY(475);
        setX(400);
    }

    public void setSafe(boolean s) {
        safe = s;
    }

    public void paint(Graphics window )
    {
        if(color.equals("red")){
            Color myColor = new Color(255, 75, 75);
            window.setColor(myColor);
        }
        else if(color.equals("blue")){
            Color myColor = new Color(48, 111, 255);
            window.setColor(myColor);
        }
        else if(color.equals("green")){
            Color myColor = new Color(0, 159, 11);
            window.setColor(myColor);
        }
        else{
            Color myColor = new Color(230, 230, 100);
            window.setColor(myColor);
        }
        window.fillOval(getX(),getY(),getW(),getH());
        window.setColor(Color.BLACK);
        window.drawOval(getX(),getY(),getW(),getH());
    }
    //Look at Paddle and Brick
    int a=5;
    int b=-5;
    public void up(){
        setY(getY()-50);
    }
    public void down(){
        setY(getY()+50);
    }
    public void left(){
        setX(getX()-50);
    }
    public void right(){
        setX(getX()-50);
    }

}
//use those classes as examples
//to create the Ball class


