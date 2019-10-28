// The "Numbers" class.
import java.awt.*;
import hsa.Console;

public class Numbers
{
    static Console c;           // The output console
    
    public static void main (String[] args)
    {
	c = new Console ();
	c.print(' ',40);
	c.println("1");
	c.print(' ',38);
	c.println("1 2");
	c.print(' ',36);
	c.println("1 2 3");
	c.print(' ',34);
	c.println("1 2 3 4");
	c.print(' ',32);
	c.println("1 2 3 4 5");
	c.print(' ',30);
	c.println("1 2 3 4 5 6");
	c.print(' ',28);
	// Place your program here.  'c' is the output console
    } // main method
} // Numbers class
