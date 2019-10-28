/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;

public class AnimateCabinSmoke extends Thread
{
    Console c;
    AnimateCabinSmoke (Console c)   //Constructor
    {
        this.c = c;
    }


    void runAnimateCabinSmoke ()
    {
        while (true)        //Continuously runs
        {
            CabinSmoke s = new CabinSmoke (c);
            s.start ();     //Makes new instance of Cabin Smoke
            try
            {
                Thread.sleep (3000);
            }
            catch (Exception e)
            {
            }
        }
    }


    public void run ()
    {
        runAnimateCabinSmoke ();
    }
}