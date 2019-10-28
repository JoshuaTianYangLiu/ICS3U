/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class Bird extends Thread
{
    Console c;
    int y = 100;        //Default Values
    int x = -70;
    int movementSpeed = 10;
    double flapSpeed = Math.toRadians (30);
    double flapCycle = 0;
    Bird (Console c)        //Constructor
    {
        this.c = c;
    }


    Bird (Console c, int y) //Constructor
    {
        this (c);
        this.y = y;     //Init height of bird
    }


    Bird (Console c, int y, int speed)  //Constructor
    {
        this (c, y);
        movementSpeed = speed;          //Flying speed
        flapSpeed = Math.toRadians (speed); //Sets the flap speed in ratio of flying speed
    }


    void moveBird ()
    {
        while (x <= 700)
        {
            int wingY = (int) (10 * Math.sin (flapCycle) - 10);     //Creates height of wing tips
            DrawUtil.bezier (x, y + wingY, x + 10, y - 10, x + 20, y - 10, 30 + x, y, 2, 50, new Color (0, 0, 0), c);   //Draws left wing
            DrawUtil.bezier (30 + x, y, 40 + x, -10 + y, 50 + x, -10 + y, 60 + x, y + wingY, 2, 50, new Color (0, 0, 0), c);    //Draws right wing
            try
            {
                Thread.sleep (10);
            }
            catch (Exception e)
            {
            }
            DrawUtil.bezierReplaceBackground (x, y + wingY, x + 10, y - 10, x + 20, y - 10, 30 + x, y, 2, 50, c);   //Erases left wing
            DrawUtil.bezierReplaceBackground (30 + x, y, 40 + x, -10 + y, 50 + x, -10 + y, 60 + x, y + wingY, 50, 2, c);    //Erases right wing
            x += movementSpeed;
            flapCycle += flapSpeed;
        }
    }


    public void run ()
    {
        moveBird ();
    }
}