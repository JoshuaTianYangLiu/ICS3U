// The "Arithmetic" class.
import java.awt.*;
import hsa.Console;

public class Arithmetic
{
    static Console c;           // The output console
    
    public static void main (String[] args)
    {
	c = new Console ();
	c.println("The Calculator\n");
	c.print("Operand #1: ");
	int a=c.readInt();
	c.print("Operand #2: ");
	int b=c.readInt();
	c.println(a+" + "+b+" = "+(a+b));
	c.println(a+" - "+b+" = "+(a-b));
	c.println(a+" x "+b+" = "+(a*b));
	c.println(a+" / "+b+" = "+(a/(float)b));
	// Place your program here.  'c' is the output console
    } // main method
} // Arithmetic class
