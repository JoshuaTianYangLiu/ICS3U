/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class Skater implements Runnable
{
    Console c;
    double timeElasped = 0;     //Default values
    int prevX = 0;
    boolean facingLeft = false;
    Skater (Console c)  //Constructor
    {
        this.c = c;
    }


    void drawSkater (int x, int y, boolean isFacingLeft)
    {
        if (isFacingLeft)
        {
            //Draws skater facing left
            c.setColor (new Color (0, 0, 0));
            c.fillArc (x - 3, y - 5, 10, 5, 180, 180);   //Skate on ice
            c.setColor (new Color (0, 0, 0));
            c.fillRect (x, y - 32, 2, 32);              //Leg on ice + body+neck
            c.setColor (new Color (0, 0, 0));
            c.drawLine (x, y - 20, x + 15, y - 12);     //Leg out
            c.setColor (new Color (0, 0, 0));
            c.drawLine (x, y - 21, x + 15, y - 13);     //Leg out
            c.setColor (new Color (0, 0, 0));
            c.fillArc (x + 12, y - 15, 8, 8, 225, 180); //Skate with leg out
            c.setColor (new Color (0, 0, 0));
            c.fillRect (x - 15, y - 28, 30, 2);         //Arms
            c.setColor (new Color (0, 0, 0));
            c.fillOval (x - 7, y - 47, 15, 15);         //Head
        }
        else
        {
            //Draws skater facing right
            c.setColor (new Color (0, 0, 0));
            c.fillArc (x - 3, y - 5, 10, 5, 180, 180);  //Skate on ice
            c.setColor (new Color (0, 0, 0));
            c.fillRect (x, y - 32, 2, 32);              //Leg on ice + body+neck
            c.setColor (new Color (0, 0, 0));
            c.drawLine (x, y - 20, x - 15, y - 12);     //Leg out
            c.setColor (new Color (0, 0, 0));
            c.drawLine (x, y - 21, x - 15, y - 13);     //Leg out
            c.setColor (new Color (0, 0, 0));
            c.fillArc (x - 18, y - 15, 8, 8, 135, 180); //Skate with leg out
            c.setColor (new Color (0, 0, 0));
            c.fillRect (x - 15, y - 28, 30, 2);         //Arms
            c.setColor (new Color (0, 0, 0));
            c.fillOval (x - 7, y - 47, 15, 15);         //Head
        }
    }


    void moveSkater ()
    {
        while (true)
        {
            int x = (int) (75 * Math.sin (timeElasped / 2) + 120);      //Creates figure 8 path
            int y = (int) (30 * Math.sin (timeElasped) + 425);
            drawSkater (x, y, facingLeft);
            timeElasped += Math.toRadians (2);  //Moves the skater forward
            try
            {
                Thread.sleep (10);
            }
            catch (Exception e)
            {
            }
            if (facingLeft) //Redraws image based which say the skater if facing
            {
                c.drawImage (DrawUtil.getBackgroundCrop (x - 15, y - 47, 35, 48), x - 15, y - 47, null);
            }
            else
            {
                c.drawImage (DrawUtil.getBackgroundCrop (x - 20, y - 47, 35, 48), x - 20, y - 47, null);
            }
            if (prevX < x)  //If its moving right
            {
                if (x - prevX > 0.001)
                { //Prevents the skater jittering left and right
                    facingLeft = false;
                }
            }
            else
            {
                if (prevX - x > 0.001)
                { //Prevents the skater jittering left and right
                    facingLeft = true;
                }
            }

            prevX = x;
        }
    }


    public void run ()
    {
        moveSkater ();
    }
}