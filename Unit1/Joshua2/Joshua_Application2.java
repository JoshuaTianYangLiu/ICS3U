/*
Joshua Liu
Afsari
Sept 12,2019
Writes test marks
*/
import java.awt.*;
import hsa.Console;

public class Joshua_Application2
{
    static Console c;           // The output console
    
    Joshua_Application2(){
	c=new Console();
    }
    void drawTitle(){
	c.println("\tStudent\t\tTest 1\t\t Test 2\t\t\tTest Average\n");
    }
    void displayMarks(){
	c.print("\tGoofy\t\t79\t\t60\t\t\t");
	c.println((79+60)/2.0,0,2);
	c.print("\tSneezy\t\t50\t\t40\t\t\t");
	c.println((50+40)/2.0,0,2);
	c.print("\tSleepy\t\t92\t\t93\t\t\t");
	c.println((92+93)/2.0,0,2);
    }
    public static void main (String[] args)
    {
	Joshua_Application2t;
	t=new Joshua_Application2();
	t.drawTitle();
	t.displayMarks();
	// Place your program here.  'c' is the output console
    } // main method
} // TestMarks   class
