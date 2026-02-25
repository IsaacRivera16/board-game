import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Directions: extend the Brick class and use the Brick methods
 *
 *          Override the paint method to draw a Ball
 *
 *          Make an xSpeed and a ySpeed instance variable in order to make the ball bounce
 *
 *          Create a Ball constructor, using the Paddle constructor as an example, it can still take
 *          in just one speed, then set both instance variables to that one speed.
 *
 *          Create a bounce method that keeps the ball on the screen but bounces around the screen.
 *          Do NOT test if the ball is touching the bricks or the paddle in this method, you will do that
 *          in the game logic in BreakOut.java.
 *
 *          Go to BreakOut and do Step 2 and test it in the runner.
 *
 *
 */
public class Ball extends Brick
{
    private int speedX=5;
    private int speedY=5;
    private String color;
    public Ball( int ex, int wy, int wd, int ht, String co)
    {
        super(ex,wy,wd,ht);
        color=co;
    }
    public void paddleBounce(){
        speedY = -Math.abs(speedY);
        setY(getY() - 5);
    }
    public void brickBounce(){
        speedY=-speedY;
    }
    public void upSpeed()
    {
        a++;
        b--;
        if(speedX>0){
            speedX++;
        }
        else{
            speedX--;
        }
        if(speedY>0){
            speedY++;
        }
        else{
            speedY--;
        }
        System.out.println("Ball speed increased! New SpeedX: " + a + " SpeedY: " + b);


    }
    public void setSpeedX(int newSpeedX) {
        this.speedX = newSpeedX;
    }
    public int getSpeedX() {
        return this.speedX;
    }
    public void playAgain(){
        setY(475);
        setX(400);
    }
    public void restart(){
        a=5;
        b=-5;
        speedX=5;
        speedY=5;
    }
    // All Bricks will have all of these set and get methods
    //these methods are complete
    public void paint( Graphics window )
    {
        if(color.equals("red")){
            window.setColor(Color.RED);
        }
        else if(color.equals("blue")){
            window.setColor(Color.BLUE);
        }
        else if(color.equals("green")){
            window.setColor(Color.GREEN);
        }
        else{
            window.setColor(Color.YELLOW);
        }
        window.fillOval(getX(),getY(),getW(),getH());
        window.setColor(Color.BLACK);
        window.drawOval(getX(),getY(),getW(),getH());
    }
    //Look at Paddle and Brick
    int a=5;
    int b=-5;
    public void changePosBy10()
    {
        setX(getX()+speedX);
        setY(getY()+speedY);
    }
    public void keepInBounds()
    {
        if(getX()<0){
            speedX= a;
        }
        if ( getX() > 765)
            speedX= b;
        if(getY()<0){
            speedY= a;
        }
        if( getY() > 570 )
            setY(700);
    }
}
//use those classes as examples
//to create the Ball class


