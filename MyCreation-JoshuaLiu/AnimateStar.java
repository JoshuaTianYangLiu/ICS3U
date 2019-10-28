/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class AnimateStar extends Thread
{
    Console c;
    AnimateStar (Console c)             //Constructor
    {
        this.c = c;
    }


    void drawStars ()
    {
        for (int i = 20 ; i < 640 ; i += 100)    //Draws a star at 100 pixel intervals
        {
            int y = (int) (100 * Math.random () + 50);          //Randomly generates the y position of the star 50 - 149
            int twinkleSpeed = (int) (500 * Math.random () + 500);      //Randomly generates the speed at which the star flashes 500 - 999
            int starSize = (int) (30 * Math.random () + 5);             //Randomly generates the size of the star 5 - 34
            int flickerSize = (int) (3 * Math.random () + 2);           //Randomly generates how large the star flash should be 2 - 4
            Star s = new Star (c, i, y, twinkleSpeed, starSize, flickerSize);   //Creates instance of star
            s.start ();
        }
    }


    public void run ()
    {
        drawStars ();
    }
}