/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class AnimateBird extends Thread
{
    Console c;
    Bird b;
    AnimateBird (Console c) //Constructor
    {
        this.c = c;
    }


    void moveAnimateBird ()
    {
        while (true)
        {
            int choice = (int) (2 * Math.random () + 1);            //Randomly choses which constructor runs    1 - 2
            int levelOfFlight = (int) (7 * Math.random ()) + 1;     //Randomly choses the one of seven y level the bird flies 1 - 7
            if (choice == 1)
            {
                b = new Bird (c, 12 * levelOfFlight);               //Draw the bird at that y level with default speed
                b.start ();
            }
            else if (choice == 2)
            {
                b = new Bird (c, 12 * levelOfFlight, (int) (10 * Math.random () + 10)); //Draws the bird at y level with random speed 10 - 19
                b.start ();
            }
            try
            {
                b.join ();                                          //Waits for thread to finish
            }
            catch (Exception e)
            {
            }
        }
    }


    public void run ()
    {
        moveAnimateBird ();
    }
}
