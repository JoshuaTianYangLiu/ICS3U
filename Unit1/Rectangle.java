// The "Rectangle" class.
import java.awt.*;
import hsa.Console;

public class Rectangle
{
    static Console c;           // The output console
    
    public static void main (String[] args)
    {
	c = new Console ();
	c.println("The Rectangle Problem\n");
	c.print("Length (cm): ");
	int a=c.readInt();
	c.print("Width (cm): ");
	int b=c.readInt();
	c.println("Dimensions\tArea\tPerimeter");
	c.println("----------\t----\t---------");
	c.println("   "+a+"x"+b+"   \t "+(a*b)+"\t   "+(2*(a+b)));
	// Place your program here.  'c' is the output console
    } // main method
} // Rectangle class
