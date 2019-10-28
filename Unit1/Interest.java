// The "Interest" class.
import java.awt.*;
import hsa.Console;

public class Interest
{
    static Console c;           // The output console
    
    public static void main (String[] args)
    {
	c = new Console ();
	c.println("Let's save some money!\n");
	c.print("Principal ($): ");
	float a=c.readFloat();
	c.print("Interest Rate (%): ");
	float b=c.readFloat();
	c.println("\nYear 1 Interest = $"+(a*b/100));
	a+=a*b/100;
	c.println("New balance = $"+a);
	c.println("\nYear 2 Interest = $"+(a*b/100));
	a+=a*b/100;
	c.println("New balance = $"+(a*(1+b/100)));
	// Place your program here.  'c' is the output console
    } // main method
} // Interest class
