// The "ForLoop1" class.
import java.awt.*;
import hsa.Console;

public class ForLoop1
{
    static Console c;           // The output console
    int num;
    long powNum;
    ForLoop1(){
	c=new Console("Exponent Table");
    }
    void title(){
	c.print(" ",30);
	c.println("Exponent Tables of Base 3");
    }
    void intro(){
	c.println("This will display a twelve line table of exponents of base 3 based on user user input");
    }
    void userInput(){
	c.print(" ",20);
	c.print("Enter the first power of the exponent table: ");
	num=c.readInt();
	c.println();
    }
    void displayOutput(){
	for(int i=num; i<num+12; i++){
	    c.print(" ",30);
	    powNum=1;
	    for(int j=0; j<i; j++){
		powNum*=3;
	    }
	    c.println("3 ^ "+i+"\t= "+powNum);
	}
    }
    public static void main (String[] args)
    {
	ForLoop1 f=new ForLoop1();
	f.title();
	f.intro();
	f.userInput();
	f.displayOutput();
	// Place your program here.  'c' is the output console
    } // main method
} // ForLoop1   class
