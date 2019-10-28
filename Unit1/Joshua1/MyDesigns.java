/*
    Joshua Liu
    Afsari
    Sept 9,2019
    Prints the string output of a rectangle and trapezoid
*/
import java.awt.*;
import hsa.Console;

public class Joshua_Application1
{
    static Console c;           // The output console
    public void drawTitle(){
	c.println(' ');
	c.print(' ',36);
	c.println("Design\n");
    }
    public void drawRectangle(){
	c.print(' ',35);
	c.println("RECTANGLE");
	c.print(' ',35);
	c.print('E');
	c.print(' ',7);
	c.println('L');
	c.print(' ',35);
	c.print('C');
	c.print(' ',7);
	c.println('G');
	c.print(' ',35);
	c.print('T');
	c.print(' ',7);
	c.println('N');
	c.print(' ',35);
	c.print('A');
	c.print(' ',7);
	c.println('A');
	c.print(' ',35);
	c.print('N');
	c.print(' ',7);
	c.println('T');
	c.print(' ',35);
	c.print('G');
	c.print(' ',7);
	c.println('C');
	c.print(' ',35);
	c.print('L');
	c.print(' ',7);
	c.println('E');
	c.print(' ',35);
	c.println("ELGNATCER\n");
    }
    public void drawTrapezoid(){
	c.print(' ',37);
	c.println("APEZO");
	c.print(' ',36);
	c.print('R');
	c.print(' ',5);
	c.println('I');
	c.print(' ',35);
	c.println("TRAPEZOID");
    }
    public Joshua_Application1(){
	c=new Console();
    }
    public static void main (String[] args)
    {   
	Joshua_Application1 d;
	d=new Joshua_Application1();
	d.drawTitle();
	d.drawRectangle();
	d.drawTrapezoid();
	// Place your program here.  'c' is the output console
    } // main method
} // Joshua_Application1 class
