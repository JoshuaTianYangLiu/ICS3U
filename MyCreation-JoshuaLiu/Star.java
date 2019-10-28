/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class Star extends Thread
{
    Console c;
    int x = 320;        //Default values
    int y = 100;
    int flickerSpeed = 500;
    int starSize = 20;
    boolean isEnlarged = false;
    int flickerSize = 3;
    Star (Console c)    //Constructor
    {
        this.c = c;
    }


    Star (Console c, int x, int y)  //Constructor
    {
        this (c);
        this.x = x;
        this.y = y;
    }


    Star (Console c, int x, int y, int twinkleSpeed)    //Constructor
    {
        this (c, x, y);
        flickerSpeed = twinkleSpeed;
    }


    Star (Console c, int x, int y, int twinkleSpeed, int starSize)  //Constructor
    {
        this (c, x, y, twinkleSpeed);
        this.starSize = starSize;
    }


    Star (Console c, int x, int y, int twinkleSpeed, int starSize, int flickerSize) //Constructor
    {
        this (c, x, y, twinkleSpeed, starSize);
        this.flickerSize = flickerSize;
    }


    void drawCenteredStar (int x, int y, int l, int w)  //Allows for more consistant drawing of stars
    {
        c.setColor (new Color (241, 255, 255));
        c.fillStar (x - l / 2, y - w / 2, l, w);
    }


    void flickerStar ()
    {
        while (true)
        {
            drawCenteredStar (x, y, starSize, starSize);
            try
            {
                Thread.sleep (flickerSpeed);    //Waits based on flickerSpeed
            }
            catch (Exception e)
            {
            }
            c.drawImage (DrawUtil.getBackgroundCrop (x - starSize / 2, y - starSize / 2, starSize, starSize), x - starSize / 2, y - starSize / 2, null);
            //Redraws background
            if (!isEnlarged)    //If star is not in enlarged state
            {
                starSize += flickerSize;    //Enlarge/flicker the star
            }
            else
            {
                starSize -= flickerSize;    //Dwindle the star
            }
            isEnlarged = !isEnlarged;       //Flip state
        }
    }


    public void run ()
    {
        flickerStar ();
    }
}