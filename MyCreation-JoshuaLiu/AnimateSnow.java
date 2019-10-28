/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class AnimateSnow extends Thread
{
    Console c;
    AnimateSnow (Console c)         //Constructor
    {
        this.c = c;
    }


    void runAnimateSnow ()
    {
        while (true)
        {
            int x = (int) (635 * Math.random () + 2);       //Randomly generates x coord of snowflake 2 - 636
            int size = (int) (5 * Math.random () + 1);      //Randomly generates the size of the snowflake 1 - 5
            int fallingSpeed = (int) (3 * Math.random () + 2);  //Randomly generates how fast the snowflakes falls 2 - 4
            Snow s = new Snow (c, x, size, fallingSpeed);   //Animate snow
            s.start ();
            try
            {
                Thread.sleep (150);
            }
            catch (Exception e)
            {
            }
        }
    }


    public void run ()
    {
        runAnimateSnow ();
    }
}