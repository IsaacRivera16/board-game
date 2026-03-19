import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.prefs.Preferences;




class BreakOut extends JPanel implements Runnable, MouseListener
{
    private boolean[] whoPlays;
    private int[] activeBalls;
    private int playerNum;
    private Ball currentBall;
    private int diceNum,mx, my;;
    private ArrayList<ArrayList<Ball>> players;
    private Square[][] grid;
    private ArrayList< Brick > bricks;
    private Color newB,newG,newR,newY;
    private Square dice;
    private boolean click, choosingTime;




    public BreakOut() // create all instance in here
    {
// breakout
        click=false;
        setSize(750,750);
        grid=new Square[15][15];
        players = new ArrayList<>();
        dice= new Square(340,340, 70,70, Color.LIGHT_GRAY);

        activeBalls = new int[]{0,0,0,0};
        playerNum=0;
        choosingTime=false;



        setGrid();
        currentBall=players.get(playerNum).get(0);

        whoPlays = new boolean[4];
        whoPlays[0]=true;


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

        Color[] playerColors = {newB, newG, newR, newY};

        for(int x=0; x< whoPlays.length; x++){
            if(whoPlays[x]) {
                playerNum=x;
            }
        }

        for(int i = 0; i < whoPlays.length; i++){
            if(whoPlays[i]){
                dice.setColor(playerColors[i]);
            }
        }


        if(click && !choosingTime){
            click=!click;
            if (mx >= dice.x && mx <= dice.x + dice.w && my >= dice.y && my <= dice.y + dice.h){
                gameRoll();
            }
        }

        if(choosingTime && !onStart()){
            if(click && checkBallClick()!=-2){
                currentBall = players.get(playerNum).get(checkBallClick());
                choosingTime=!choosingTime;
                if(activeBalls[playerNum]>1){
                    //DONT MOVE TRAPPED BALLS
                }
                if(!currentBall.onBoard){
                    notOnBoard(currentBall);
                }
                else if(currentBall.onBoard){
                    moveBall(currentBall);
                }
                rotatePlayer();

            }
            click=!click;
        }




        dice.paint(window);
        window.setFont(new Font("Arial", Font.BOLD, 50));
        window.setColor(Color.black);
        window.drawString("" + diceNum, dice.getCenter()-15, dice.getCenter()+20);
    }
    // only edit if you would like to add more key functions

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

    public void gameRoll(){

          roll();
          currentBall=players.get(playerNum).get(0);
            if(diceNum==6){
                if(activeBalls[playerNum]==1 && !onStart()){
                    choosingTime=true;
                    return;
                }
                if(onStart()){
                    moveBall(currentBall);
                    System.out.println("Blocked");
                    rotatePlayer();
                }
                else{
                    notOnBoard(currentBall);
                    increaseActive(playerNum);
                }
            }
             else if(players.get(playerNum).get(0).onBoard){
               moveBall(currentBall);
               rotatePlayer();
             }
             else{
                rotatePlayer();
            }



    }

    public int checkBallClick(){
        ArrayList<Ball> play = players.get(playerNum);
        if(mx>=play.get(0).getX() && mx<= play.get(0).getX() + play.get(0).getW() && my>=play.get(0).getY() && my<= play.get(0).getY() + play.get(0).getH()){
            return 0;
        }
        if(mx>=play.get(1).getX() && mx<= play.get(1).getX() + play.get(1).getW() && my>=play.get(1).getY() && my<= play.get(1).getY() + play.get(1).getH()){
            return 1;
        }
        if(mx>=play.get(2).getX() && mx<= play.get(2).getX() + play.get(2).getW() && my>=play.get(2).getY() && my<= play.get(2).getY() + play.get(2).getH()){
            return 2;
        }
        if(mx>=play.get(3).getX() && mx<= play.get(3).getX() + play.get(3).getW() && my>=play.get(3).getY() && my<= play.get(3).getY() + play.get(3).getH()){
            return 3;
        }
        return -2;
    }

    public void rotatePlayer(){
        whoPlays[playerNum]=false;
        if(playerNum==3){
            whoPlays[0]=true;
            playerNum=0;
        }
        else{
            whoPlays[playerNum+1]=true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

        mx = e.getX();
        my = e.getY();
        click=true;
        repaint();

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


    public void notOnBoard(Ball b){
        if (playerNum == 0) b.setGridPosition(grid, 6, 1);   // blue
        if (playerNum == 1) b.setGridPosition(grid, 1, 8);   // green
        if (playerNum == 2) b.setGridPosition(grid, 13, 6);  // red
        if (playerNum == 3) b.setGridPosition(grid, 8, 13);  // yellow
    }



    public boolean onStart(){
        for(Ball cur:players.get(playerNum)){
            int X=cur.getX();
            int Y=cur.getY();
            if(playerNum==0){
                if(X>=grid[6][1].x && X <=grid[6][1].x + grid[6][1].w   && Y>=grid[6][1].y && Y <=grid[6][1].y + grid[6][1].h ){
                    return true;
                }
            }
            if(playerNum==1){
                if(X>=grid[1][8].x && X <=grid[1][8].x + grid[1][8].w  && Y>=grid[1][8].y && Y <=grid[1][8].y + grid[1][8].h ){
                    return true;
                }
            }
            if(playerNum==2){
                if(X>=grid[13][6].x && X <=grid[13][6].x + grid[13][6].w  && Y>=grid[13][6].y && Y <=grid[13][6].y + grid[13][6].h ){
                    return true;
                }
            }
            if(playerNum==3){
                if(X>=grid[8][13].x && X <=grid[8][13].x + grid[8][13].w  && Y>=grid[8][13].y && Y <=grid[8][13].y + grid[8][13].h ){
                    return true;
                }
            }
        }
        return false;
    }


    public void moveBall(Ball ball){
        for (int i = 0; i < diceNum; i++) {
            if(ball.steps>5){
                ball.moveOneSquare(grid);
            }
            else{
                if(ball.steps-diceNum>=0){
                    ball.moveOneSquare(grid);
                }
            }
        }
        if((ball.steps-diceNum)>=0){
            ball.lowerSteps(diceNum);
        }
        checkCollision(ball);
    }

    public void increaseActive(int who){
        activeBalls[who]+=1;
    }

    public void decreaseActive(int who){
        activeBalls[who]-=1;
    }


    public void checkCollision(Ball currentBall) {


        int r = currentBall.getRow();
        int c = currentBall.getCol();

        if(r<0 || c<0) return;

        if(grid[r][c].safe) return;


        for(ArrayList<Ball> player : players){
            for(Ball other : player){


                if(other == currentBall) continue;


                if(!other.onBoard) continue;


                if(other.getColor().equals(currentBall.getColor())) continue;


                if(other.getRow() == r && other.getCol() == c){
                    other.sendHome();
                    if(other.getColor().equals("blue")){
                        decreaseActive(0);
                    }
                    if(other.getColor().equals("green")){
                        decreaseActive(1);
                    }
                    if(other.getColor().equals("red")){
                        decreaseActive(2);
                    }
                    if(other.getColor().equals("yellow")){
                        decreaseActive(3);
                    }
                }
            }
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

//


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
