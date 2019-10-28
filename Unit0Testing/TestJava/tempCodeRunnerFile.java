// The "Joshua_Application6b" class.
import java.awt.*;
import hsa.Console;

public class Joshua_Application6b
{
    static Console c;           // The output console
    Joshua_Application6b(){
        c = new Console ();
    }
    void displaySlash(){
        for(int i=0; i<20; i++){
            c.println("* * * * *",70-i);
        }
        c.print("\n");
    }
    void displayName(){
        c.println("\t\t\t\tJoshua Liu");
    }
    public static void main (String[] args)
    {
        Joshua_Application6b j=new Joshua_Application6b();
        j.displaySlash();
        j.displayName();
        // Place your program here.  'c' is the output console
    } // main method
} // Joshua_Application6b class
