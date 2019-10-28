// The "TestMarks" class.
import java.awt.*;
import hsa.Console;

public class TestMarks
{
    static Console c;           // The output console
    
    void TestMarks(){
        c=new Console();
    }
    void drawTitle(){
        c.println("\tStudent\t\tTest 1\t\t Test 2\t\t\tTest Average\n");
    }
    void displayMarks(){
        c.print("Goofy\t\t79\t\t60\t\t\t");
        c.println((79+60)/2,0,2);
        c.print("Sneezy\t\t50\t\t40\t\t\t");
        c.println((50+40)/2,0,2);
        c.print("Sleepy\t\t92\t\t93\t\t\t");
        c.println((92+93)/2,0,2);
    }
    public static void main (String[] args)
    {
        TestMarks t;
        t=new TestMarks();
        t.drawTitle();
        t.displayMarks();
        // Place your program here.  'c' is the output console
    } // main method
} // TestMarks   class
