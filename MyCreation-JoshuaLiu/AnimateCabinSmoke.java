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

    CabinSmoke s[] = new CabinSmoke[4]; //Limits number of smoke clouds to 4 incase of the computer slowing down.
    void runAnimateCabinSmoke ()
    {
        while (true)        //Continuously runs
        {
            for(int i=0; i<4; i++){
                if(s[i]==null||s[i].isAlive()){
                    s[i] = new CabinSmoke (c);
                    s[i].start ();     //Makes new instance of Cabin Smoke
                    break;
                }
            }
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