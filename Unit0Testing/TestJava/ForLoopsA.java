// The "Joshua_Application6a" class.
import java.awt.*;
import hsa.Console;

public class Joshua_Application6a
{
    static Console c;           // The output console
    
    Joshua_Application6a(){
        c = new Console ();
    }
    void drawTitle(){
        c.println("\t\t\t\tFor Loop Table\n");
    }
    void displayTable(){
        for(int i=1; i<=5; i++){
            c.print(i,11);
            c.print(12*i,11);
            c.print(-16+i,11);
            c.print(53-i,11);
            c.print(i+2,11);
            c.println(i+6,11);
        }
    }
    public static void main (String[] args)
    {
        Joshua_Application6a j = new Joshua_Application6a();
        j.drawTitle();
        j.displayTable();
        // Place your program here.  'c' is the output console
    } // main method
} // Joshua_Application6a class
