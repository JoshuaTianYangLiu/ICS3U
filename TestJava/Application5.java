// The "Joshua_Application5" class.
import java.awt.*;
import hsa.Console;

public class Joshua_Application5
{
    static java.io.Console c;           // The output console

    //Declaration statements
    String name;
    String teacherName;
    int age;
    Joshua_Application5(){
        c = new Console ("Who are you?");
    }
    void askData(){
        //Input statements
        c.print("What is your name? ");
        name=c.readLine();
        c.print("Who is your teacher? ");
        teacherName=c.readLine();
        c.print("How old are you? ");
        age=c.readInt();
    }
    void displayData(){
        //Output statement
        c.println("So, "+name+", your teacher is "+teacherName+" and are you really "+age+" years old?");
    }
    public static void main (String[] args)
    {
        Joshua_Application5 j;
        j=new Joshua_Application5();
        j.askData();
        j.displayData();
    } // main method
} // Joshua_Application5 class
