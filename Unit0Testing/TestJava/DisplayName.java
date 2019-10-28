// The "JoshuaLiu4" class.
import java.awt.*;
import hsa.Console;

public class JoshuaLiu4
{
    static Console c;           // The output console
    JoshuaLiu4(){
        c =new Console();
    }
    void drawTitle(){
        c.println("\t\tDisplay My Name\n");
    }
    void displayData(){
        String firstName="Joshua";
        String lastName="Liu";
        c.println("\t"+firstName.toUpperCase()+" "+lastName.toUpperCase());
        c.println("\t"+firstName.toLowerCase()+" "+lastName.toLowerCase()));
    }
    public static void main (String[] args)
    {
        JoshuaLiu4 = j;
        j=new JoshuaLiu4();
        j.drawTitle();
        j.displayData();
        
        // Place your program here.  'c' is the output console
    } // main method
} // JoshuaLiu4 class
