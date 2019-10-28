// The "JoshuaLiu4" class.
import java.awt.*;
import hsa.Console;

public class JoshuaLiu4
{
    static Console c;       // The output console
    
    JoshuaLiu4(){
	c =new Console();   //Constructor
    }
    void drawTitle(){
	c.println("Display My Name\n");   //Draws title
    }
    void displayData(){
	String firstName="Joshua";      //Shows my name in uppercase and lowercase
	String lastName="Liu";
	c.println(firstName.toUpperCase()+" "+lastName.toUpperCase());
	c.println(firstName.toLowerCase()+" "+lastName.toLowerCase());
    }
    void displayName(){
	String firstName="Joshua";  //Variable declaration
	String lastName="Liu";
	c.println(firstName.charAt(0));
	c.println(lastName.charAt(0));
    }
    public static void main (String[] args)
    {
	JoshuaLiu4 j;
	j = new JoshuaLiu4();
	//Calls methods
	j.drawTitle();
	j.displayData();
	j.displayName();
	// Place your program here.  'c' is the output console
    } // main method
    
} // JoshuaLiu4 class
