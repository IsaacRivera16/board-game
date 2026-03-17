import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;

class BreakOut extends JPanel implements Runnable, KeyListener, MouseListener
{
    private boolean[] keys;
    int te=0;
    private boolean[] whoPlays;
    private int diceNum;
    private ArrayList<ArrayList<Ball>> players;
    ArrayList<Ball> red,blue,yellow,green;
    private Brick background;
    private Ball r1,r2,r3,r4,g1,g2,g3,g4,y1,y2,y3,y4,b1,b2,b3,b4;
    private Square[][] grid;
    private ArrayList< Brick > bricks;
    private boolean gameOver=false;
    private boolean gameStart=false;
    private boolean alive=false;
    private Square dice;
    private Color newB,newG,newR,newY;
    private boolean choosingTime=false;


    public BreakOut() // create all instance in here
    {
// breakout
        setSize(750,750);
        grid=new Square[15][15];
        players = new ArrayList<>();
        dice= new Square(340,340, 70,70, Color.LIGHT_GRAY);

        setGrid();

        whoPlays = new boolean[4];
        whoPlays[0]=true;
        keys = new boolean[5];

        addKeyListener( this );    //
        addMouseListener(this);
        setFocusable( true );     // Do NOT DELETE these three lines
        new Thread(this).start();
    }

    public void roll(){
        diceNum=(int)(Math.random()*6)+1;
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

        if(whoPlays[0]){
            dice.setColor(newB);
        }
        if(whoPlays[1]){
            dice.setColor(newG);
        }
        if(whoPlays[2]){
            dice.setColor(newR);
        }
        if(whoPlays[3]){
            dice.setColor(newY);
        }


        dice.paint(window);
        window.setFont(new Font("Arial", Font.BOLD, 50));
        window.setColor(Color.black);
        window.drawString("" + diceNum, dice.getCenter()-15, dice.getCenter()+20);
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
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= dice.x && mx <= dice.x + dice.w && my >= dice.y && my <= dice.y + dice.h) {
            roll();
        }


        //SCRAP ALL THIS
        //MAKE METHODS THAT ICNLUDE MOUSEPRESSED IN THEM, DO NOT PUT THE METHODS IN MOUSEPRESSED

            int currentPlayer = -1;
            ArrayList<Ball> player = new ArrayList<>();
            Ball ball = new Ball(-1,-1,-1,-1,"white",-1);

            for (int i = 0; i < whoPlays.length; i++) {   //Checks to see who's up
                if (whoPlays[i]){
                    currentPlayer = i;
                    player = players.get(currentPlayer);
                }

            }
            if (currentPlayer == -1) return; //Error Checker

            if(nextAvail(player)==0){
                ball = player.get(0);
            }

            if(diceNum==6){
                if(hasPlayerActive(players.get(currentPlayer)) && !onStart(ball)){
                    chooseBall(mx,my,players.get(currentPlayer),currentPlayer);
                    System.out.println("yay");
                }

                else if (!ball.onBoard) {
                    notOnBoard(currentPlayer, ball);    //When there are no balls on board
                    ball.onBoard=true;
                }
            }


            else {
                for (int i = 0; i < diceNum; i++) {  //Moves ball diceNum times
                    ball.moveOneSquare(grid);
                }
                if(diceNum!=6){   //Moves to next player
                    if(whoPlays[3]){
                        whoPlays[0]=true;
                    }
                    else{
                        whoPlays[currentPlayer+1]=true;
                    }
                    whoPlays[currentPlayer]=false;
                }
            }

            repaint();

    }


public boolean onStart(Ball ba){

        if(whoPlays[0]){
            if(ba.getX()==grid[6][1].x){
                return true;
            }
        }
    if(whoPlays[1]){
        if(ba.getX()==grid[2][8].x){
            return true;
        }
    }
    if(whoPlays[2]){
        if(ba.getX()==grid[13][6].x){
            return true;
        }
    }
    if(whoPlays[3]){
        if(ba.getX()==grid[8][13].x){
            return true;
        }
    }
        return false;

}

    public void chooseBall(int x, int y, ArrayList<Ball> player, int curP){

        if(whoPlays[0]){ //b
            if(x>=grid[0][0].x && x<=(grid[0][5].x + grid[0][5].w) && y>=grid[0][0].y && y<=grid[5][5].y + grid[5][5].h){
                notOnBoard(curP,player.get(nextAvail(player)));
                System.out.println("Blue");
            }
        }
        if(whoPlays[1]){ //g
            if(x>=grid[0][0].x && x<=(grid[0][5].x + grid[0][5].w) && y>=grid[0][0].y && y<=grid[5][5].y + grid[5][5].h){
                notOnBoard(curP,player.get(nextAvail(player)));
                System.out.println("Green");
            }
        }
        if(whoPlays[2]){ //r
            if(x>=grid[0][0].x && x<=(grid[0][5].x + grid[0][5].w) && y>=grid[0][0].y && y<=grid[5][5].y + grid[5][5].h){
                notOnBoard(curP,player.get(nextAvail(player)));
                System.out.println("Red");
            }
        }
        if(whoPlays[3]){ //y
            if(x>=grid[0][0].x && x<=(grid[0][5].x + grid[0][5].w) && y>=grid[0][0].y && y<=grid[5][5].y + grid[5][5].h){
                notOnBoard(curP,player.get(nextAvail(player)));
                System.out.println("Yellow");
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean hasPlayerActive(ArrayList<Ball> player){
        for(Ball x:player){
            if(x.onBoard){
                return true;
            }
        }
        return false;
    }

    public int nextAvail(ArrayList<Ball> player){
        for(int x=0; x<player.size(); x++){
            if(!player.get(x).onBoard){
                return x;
            }
        }
        return -1;
    }

    public void notOnBoard(int curPlay, Ball b){
        if (diceNum == 6) {
            if (curPlay == 0) b.setGridPosition(grid, 6, 1);   // blue
            if (curPlay == 1) b.setGridPosition(grid, 2, 8);   // green
            if (curPlay == 2) b.setGridPosition(grid, 13, 6);  // red
            if (curPlay == 3) b.setGridPosition(grid, 8, 13);  // yellow
        }
        else{
            if(whoPlays[3]){
                whoPlays[0]=true;
            }
            else{
                whoPlays[curPlay+1]=true;
            }
            whoPlays[curPlay]=false;
        }
    }

    public void setGrid(){

        newB=new Color(73,73,153);
        newG=new Color(73,153,73);
        newR=new Color(153,73,73);
        newY=new Color(153,153,73);


        ArrayList<Ball> red = new ArrayList<>();
        ArrayList<Ball> blue = new ArrayList<>();
        ArrayList<Ball> yellow = new ArrayList<>();
        ArrayList<Ball> green = new ArrayList<>();

        blue.add(new Ball(85,85,30,30,"blue",0));
        blue.add(new Ball(185,185,30,30,"blue",1));
        blue.add(new Ball(185,85,30,30,"blue",2));
        blue.add(new Ball(85,185,30,30,"blue",3));

        //Adding green players
        green.add(new Ball(535,85,30,30,"green",0));
        green.add(new Ball(535,185,30,30,"green",1));
        green.add(new Ball(635,85,30,30,"green",2));
        green.add(new Ball(635,185,30,30,"green",3));

        //Adding red players
        red.add(new Ball(85,635,30,30,"red",0));
        red.add(new Ball(185,635,30,30,"red",1));
        red.add(new Ball(85,535,30,30,"red",2));
        red.add( new Ball(185,535,30,30,"red",3));

        //Adding yellow player
        yellow.add(new Ball(535,635,30,30,"yellow",0));
        yellow.add(new Ball(535,535,30,30,"yellow",1));
        yellow.add(new Ball(635,535,30,30,"yellow",2));
        yellow.add(new Ball(635,635,30,30,"yellow",3));

        players.add(blue);
        players.add(green);
        players.add(red);
        players.add(yellow);

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
                        grid[r][c].setSafe();
                    }
                    if(r==1 && c==8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.green);
                        grid[r][c].setSafe();
                    }
                }
                if(r>7&&r<grid.length-1){
                    if(c==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.red);
                        grid[r][c].setSafe();
                    }
                    if (r==grid.length-2 && c==6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.red);
                        grid[r][c].setSafe();}
                }
                if(c>0 && c<=6){
                    if(r==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.blue);
                        grid[r][c].setSafe();
                    }
                    if(c==1 && r==6){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.blue);
                        grid[r][c].setSafe();
                    }
                }
                if(c>7&&c<grid.length-1){
                    if(r==7){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.yellow);
                        grid[r][c].setSafe();
                    }
                    if(c==grid.length-2 && r==8){
                        grid[r][c]=new Square(currentx,currenty,howMuch,howMuch,Color.yellow);
                        grid[r][c].setSafe();
                    }
                }
                currentx+=howMuch;

            }
            currenty+=howMuch;
            currentx=0;

        }
        //Safe spots
        grid[2][6].setSafe(); grid[2][6].setColor(Color.MAGENTA);
        grid[8][2].setSafe(); grid[8][2].setColor(Color.MAGENTA);
        grid[6][12].setSafe();  grid[6][12].setColor(Color.MAGENTA);
        grid[12][8].setSafe();  grid[12][8].setColor(Color.MAGENTA);
        grid[0][6].setRightTurn();
        grid[0][7].setGreenTurn();
        grid[0][8].setDownTurn();
        grid[6][14].setDownTurn();
        grid[7][14].setYellowTurn();
        grid[8][14].setLeftTurn();
        grid[14][8].setLeftTurn();
        grid[14][7].setRedTurn();
        grid[14][6].setUpTurn();
        grid[8][0].setUpTurn();
        grid[7][0].setBlueTurn();
        grid[6][0].setRightTurn();
        grid[6][6].setUpTurn();
        grid[6][8].setRightTurn();
        grid[8][8].setDownTurn();
        grid[8][6].setLeftTurn();
    }
}