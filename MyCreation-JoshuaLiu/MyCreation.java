// The "MyCreation" class.
import java.awt.*;
import java.lang.*;
import hsa.Console;

public class MyCreation
{
    Console c;           // The output console
    MyCreation ()
    {
	c = new Console ("My Creation: Winter");
    }


    void initUtil ()
    {
	DrawUtil.loadBuffer ();
    }


    void background ()
    {
	Background g = new Background (c);
    }


    void sled ()
    {
	Sled s = new Sled (c);
	s.start ();
    }


    void cabinSmoke ()
    {
	AnimateCabinSmoke cs = new AnimateCabinSmoke (c);
	cs.start ();
    }


    void bird ()
    {
	AnimateBird b = new AnimateBird (c);
	b.start ();
    }


    void snow ()
    {
	AnimateSnow sn = new AnimateSnow (c);
	sn.start ();
    }


    void star ()
    {
	AnimateStar st = new AnimateStar (c);
	st.start ();
    }


    void figureSkater ()
    {
	Thread t = new Thread (new Skater (c));
	t.start ();
    }


    public static void main (String[] args)
    {
	MyCreation c = new MyCreation ();
	//background    0
	//star                  1-10
	//cabinSmoke    11-13
	//bird                  14-20
	//sled                  21
	//figureSkater  22
	//snow                  22-32
	c.initUtil ();
	c.background ();
	c.sled ();
	c.cabinSmoke ();
	c.bird ();
	c.snow ();
	c.star ();
	c.figureSkater ();
	// Place your program here.  'c' is the output console
    } // main method
} // MyCreation class
