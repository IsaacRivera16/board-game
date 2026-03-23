import java.awt.*;
import javax.swing.*;
//BBoolean finish
//




public class Ball extends Brick {
    private String color;
    boolean safe, finish;
    private int row, col;
    private String direction;
    boolean onBoard;
    int ballNum;
    int steps;
    int sx;
    int sy;




    public Ball(int ex, int wy, int wd, int ht, String co,int b){
        super(ex, wy, wd, ht);
        color = co;
        safe = false;
        row = -1;
        col = -1;
        ballNum=b;
        sx=ex;
        sy=wy;
        steps=60;
        onBoard=false;
        finish=false;




        if(co.equals("blue")){
            direction = "right";
        }
        else if(co.equals("red")){
            direction = "up";
        }
        else if(co.equals("green")){
            direction = "down";
        }
        else{
            direction="left";
        }
        onBoard = false;
    }


    public String getColor(){
        return color;
    }



    public void sendHome() {
        setX(sx);
        setY(sy);
        row = -1;
        col = -1;
        onBoard = false;
        steps=60;


        if(color.equals("blue")){
            direction = "right";
        }
        else if(color.equals("red")){
            direction = "up";
        }
        else if(color.equals("green")){
            direction = "down";
        }
        else{
            direction="left";
        }
    }


    public void setGridPosition(Square[][] grid, int r, int c) {
        row = r;
        col = c;
        onBoard = true;
        setX(grid[row][col].x + grid[row][col].w / 2 - getW() / 2);
        setY(grid[row][col].y + grid[row][col].h / 2 - getH() / 2);
    }








    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getDirection() { return direction; }
    public void setDirection(String dir) { direction = dir; }
    public void lowerSteps(int num){
        steps = steps-num;
    }








    public void moveOneSquare(Square[][] grid) {
        if (!onBoard) return;





        if (grid[row][col].upTurn) direction = "up";
        else if (grid[row][col].downTurn) direction = "down";
        else if (grid[row][col].leftTurn) direction = "left";
        else if (grid[row][col].rightTurn) direction = "right";
        else if (grid[row][col].blueTurn && color.equals("blue")) direction = "right";
        else if (grid[row][col].greenTurn && color.equals("green")) direction = "down";
        else if (grid[row][col].redTurn && color.equals("red")) direction = "up";
        else if (grid[row][col].yellowTurn && color.equals("yellow")) direction = "left";




        if (direction.equals("up")) row--;
        else if (direction.equals("down")) row++;
        else if (direction.equals("left")) col--;
        else if (direction.equals("right")) col++;





        setX(grid[row][col].x + grid[row][col].w / 2 - getW() / 2);
        setY(grid[row][col].y + grid[row][col].h / 2 - getH() / 2);
    }








    // Draw the ball
    public void paint(Graphics window) {
        if (color.equals("red")) window.setColor(new Color(255, 75, 75));
        else if (color.equals("blue")) window.setColor(new Color(48, 111, 255));
        else if (color.equals("green")) window.setColor(new Color(0, 159, 11));
        else window.setColor(new Color(230, 230, 100));








        window.fillOval(getX(), getY(), getW(), getH());
        window.setColor(Color.BLACK);
        window.drawOval(getX(), getY(), getW(), getH());
    }
















    public void setSafe(boolean s) { safe = s; }
}


