/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class Snow extends Thread
{
    Console c;
    int x = 320;    //Default values
    int y = -10;
    int size = 2;
    int fallingSpeed = 5;
    Snow (Console c)    //Constructor
    {
        this.c = c;
    }


    Snow (Console c, int x) //Constructor
    {
        this (c);
        this.x = x;
    }


    Snow (Console c, int x, int size)   //Constructor
    {
        this (c, x);
        this.size = size;
    }


    Snow (Console c, int x, int size, int fallingSpeed) //Constructor
    {
        this (c, x, size);
        this.fallingSpeed = fallingSpeed;
    }


    void moveSnow ()
    {
        while (y < 510)
        {
            c.setColor (new Color (255, 255, 255));
            c.drawLine (x + size / 2, y, x + size / 2, y + size);   //horizontal line of snowflake
            c.setColor (new Color (255, 255, 255));
            c.drawLine (x, y + size / 2, x + size, y + size / 2);   //vertical line of snowflake
            try
            {
                Thread.sleep (10);
            }
            catch (Exception e)
            {
            }
            if (y + size / 2 < 500)     //Error checking
            {
                c.drawImage (       //Redraws background of vertical line
                        DrawUtil.getBackgroundCrop (x, y + size / 2, size + 1, 1),
                        x, y + size / 2, null);
            }
            if (y < 500)        //Error checking
            {
                c.drawImage (       //Redraws background of horizontal line
                        DrawUtil.getBackgroundCrop (x + size / 2, y, 1, size + 1),
                        x + size / 2, y, null);
            }
            y += fallingSpeed;
            if (Math.random () > 0.5)   //50% chance to
            {
                x += size * (int) (2 * Math.random () - 1); //Let the snowflake "sway" left or right
            }
        }
    }


    public void run ()
    {
        moveSnow ();
    }
}