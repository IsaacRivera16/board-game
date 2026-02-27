import javax.swing.*;


class BreakOutRunner extends JFrame
{
    private static final int WIDTH = 750;
    private static final int HEIGHT = 750;


    public BreakOutRunner()
    {
        super("Brick Breaker BreakOut");


        setSize(WIDTH,HEIGHT);


        //use the ClassTester to test your classes
        //before you start to write the full game


//     getContentPane().add( new ClassTester() );


        //uncomment this when ready to start
        //building the game
        getContentPane().add( new BreakOut() );

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main( String args[] )
    {
        BreakOutRunner run = new BreakOutRunner();
    }
}
