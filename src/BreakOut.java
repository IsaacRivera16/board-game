import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;


class BreakOut extends JPanel implements Runnable, KeyListener
{
    private boolean[] keys;
    private ArrayList<ArrayList<Ball>> players;
    private Brick background;
    private Ball r1,r2,r3,r4,g1,g2,g3,g4,y1,y2,y3,y4,b1,b2,b3,b4;
    private Square[][] grid;
    private ArrayList< Brick > bricks;
    private boolean gameOver=false;
    private boolean gameStart=false;
    private boolean alive=false;

    public BreakOut() // create all instance in here
    {
// breakout
        setSize(750,750);
        grid=new Square[15][15];
        players = new ArrayList<>();

        ArrayList<Ball> red = new ArrayList<>();
        ArrayList<Ball> blue = new ArrayList<>();
        ArrayList<Ball> yellow = new ArrayList<>();
        ArrayList<Ball> green = new ArrayList<>();


        //Adding blue players
        blue.add(new Ball(85,85,30,30,"blue"));
        blue.add(new Ball(185,185,30,30,"blue"));
        blue.add(new Ball(185,85,30,30,"blue"));
        blue.add(new Ball(85,185,30,30,"blue"));

        //Adding green players
        green.add(new Ball(678,90,30,30,"green"));
        green.add(new Ball(573,90,30,30,"green"));
        green.add(new Ball(678,195,30,30,"green"));
        green.add(new Ball(573,195,30,30,"green"));

        //Adding red players
        red.add(new Ball(203,565,30,30,"red"));
        red.add(new Ball(98,565,30,30,"red"));
        red.add(new Ball(203,670,30,30,"red"));
        red.add( new Ball(98,670,30,30,"red"));

        //Adding yellow player
        yellow.add(new Ball(678,565,30,30,"yellow"));
        yellow.add(new Ball(573,565,30,30,"yellow"));
        yellow.add(new Ball(678,670,30,30,"yellow"));
        yellow.add(new Ball(573,670,30,30,"yellow"));

        players.add(blue);
        players.add(green);
        players.add(red);
        players.add(yellow);


        keys = new boolean[5];

        int currentx=0;
        int currenty=0;
        int howMuch=50;
        for(int x=0; x<grid.length; x++){
            for(int i=0; i<grid[0].length; i++){
                grid[x][i]=new Square(currentx,currenty,howMuch,howMuch,Color.white);
                currentx+=howMuch;
            }
            currenty+=howMuch;
            currentx=0;
        }



        currenty=0;
        for(int r=0; r<grid.length; r++){
            for(int c=0; c<grid[0].length; c++){
                //The prison things
                if(r<6){
                    if(c<6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.blue);
                    }
                    if(c>8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.green);
                    }
                }

                if(r>8){
                    if(c<6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.red);
                    }
                    if(c>8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.yellow);
                    }

                }

                //Entrance onto board and pathways
                if(r>0 && r<=6){
                    if(c==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.green);
                        grid[r][c].setSafe(true);
                    }
                    if(r==1 && c==8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.green);
                        grid[r][c].setSafe(true);
                    }
                }
                if(r>7&&r<grid.length-1){
                    if(c==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.red);
                        grid[r][c].setSafe(true);
                    }
                    if (r==grid.length-2 && c==6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.red);
                        grid[r][c].setSafe(true);}
                }
                if(c>0 && c<=6){
                    if(r==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.blue);
                        grid[r][c].setSafe(true);
                    }
                    if(c==1 && r==6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.blue);
                        grid[r][c].setSafe(true);
                    }
                }
                if(c>7&&c<grid.length-1){
                    if(r==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.yellow);
                        grid[r][c].setSafe(true);
                    }
                    if(c==grid.length-2 && r==8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.yellow);
                        grid[r][c].setSafe(true);
                    }
                }
                currentx+=howMuch;


            }
            currenty+=howMuch;
            currentx=0;

        }
        //Safe spots
        grid[2][6].setSafe(true); grid[2][6].setColor(Color.MAGENTA);
        grid[8][2].setSafe(true); grid[8][2].setColor(Color.MAGENTA);
        grid[6][12].setSafe(true);  grid[6][12].setColor(Color.MAGENTA);
        grid[12][8].setSafe(true);  grid[12][8].setColor(Color.MAGENTA);

        addKeyListener( this );    //
        setFocusable( true );     // Do NOT DELETE these three lines
        new Thread(this).start();
    }

    public void paint( Graphics window )// all other paint methods and game logic goes in here.
    {
        //background.paint(window);
        int middleBoard=grid[7][7].getCenter();

        int[] triCorner1X = {grid[6][6].x,middleBoard,grid[6][8].x+grid[6][8].w};
        int[] triCorner1Y = {grid[6][6].y,middleBoard,grid[6][8].y};
        int[] triCorner2X = {grid[6][6].x,middleBoard,grid[8][6].x};
        int[] triCorner2Y = {grid[6][6].y,middleBoard,grid[8][6].y+grid[8][6].h};
        int[] triCorner3X = {grid[8][6].x,middleBoard,grid[8][8].x+grid[8][8].w};
        int[] triCorner3Y = {grid[8][6].y+grid[8][6].h,middleBoard,grid[8][8].y+grid[8][8].h};
        int[] triCorner4X = {grid[6][8].x+grid[6][8].w,middleBoard,grid[8][8].x+grid[8][8].w};
        int[] triCorner4Y = {grid[6][8].y,middleBoard,grid[8][8].y+grid[8][8].h};

        Polygon tri1= new Polygon(triCorner1X, triCorner1Y,3);
        Polygon tri2= new Polygon(triCorner2X, triCorner2Y,3);
        Polygon tri3= new Polygon(triCorner3X, triCorner3Y,3);
        Polygon tri4= new Polygon(triCorner4X, triCorner4Y,3);



        for(Square[] x:grid){
            for(Square i:x){
                i.paint(window);
            }
        }
        window.setColor(Color.green);
        window.fillPolygon(tri1);
        window.setColor(Color.blue);
        window.fillPolygon(tri2);
        window.setColor(Color.red);
        window.fillPolygon(tri3);
        window.setColor(Color.yellow);
        window.fillPolygon(tri4);

        for(ArrayList<Ball> x:players){
            for(Ball i: x){
                i.paint(window);
            }
        }

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
