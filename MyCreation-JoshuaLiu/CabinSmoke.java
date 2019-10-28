/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class CabinSmoke extends Thread
{
    Console c;
    CabinSmoke (Console c)  //Constructor
    {
        this.c = c;
    }


    void moveCabinSmoke ()  
    {
        //These x values will create the path of the cloud based off of a bezier path
        int x2 = (int) (40 * Math.random () - 20 + 347);    //Creates the second point so its <20 pixels away from the starting point
        int x3 = (int) (150 * Math.random () - 75 + 347);   //Creates the third point so its <75 pixels away from the starting point
        int x4 = (int) (400 * Math.random () - 200 + 347);  //Creates the point point so its <200 pixels away from the starting point
        for (int i = 0 ; i < 500 ; i++)
        {
            double t = DrawUtil.lerp (0.0, 1.0, i / 500.0);     //Linear Interpolation from 0 - 500 to 0 - 1
            int x = DrawUtil.bezierPoint (347, x2, x3, x4, t);  //Gets x and y points based on bezier formula and how far into the animation is
            int y = DrawUtil.bezierPoint (210, 100, 50, 0, t);
            c.setColor (new Color (52, 73, 93));        //Cloud colour
            c.fillRoundRect (x, y, i / 5, i / 10, i / 20, i / 20);  //Cloud grows as it goes further away
            try
            {
                Thread.sleep (10);
            }
            catch (Exception e)
            {
            }
            if (i / 5 != 0 && i / 10 != 0)  //If the cloud exists/ if the cloud has a width and height
            {
                c.drawImage (DrawUtil.getBackgroundCrop (x, y, i / 5, i / 10), x, y, null); //Covers the image with the background
            }
        }
    }


    public void run ()
    {
        moveCabinSmoke ();
    }
}
