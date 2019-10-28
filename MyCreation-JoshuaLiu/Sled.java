/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
import java.awt.image.BufferedImage;
public class Sled extends Thread
{
    Console c;
    BufferedImage backgroundBuf;
    int x = 320;
    Sled (Console c)    //Constructor
    {
        this.c = c;
    }


    int arcFunc (int x)     //Gets the y values of ellipse based on x
    {
        double ans = (x - 95) * (x - 95);
        ans *= 100 * 100;
        ans /= 120 * 120;
        ans = 100 * 100 - ans;
        ans = -1 * Math.sqrt (ans) + 280;
        return (int) ans;
    }


    double arcDerivativeFunc (int x)    //Calculates the derivative of the ellipse based on x
    {
        double numer = 5 * (x - 95);
        double denom = -x * x + 190 * x + 5375;
        denom = Math.sqrt (denom);
        denom *= 6;
        return numer / denom;
    }


    void moveSled ()
    {
        int length = 50;
        int width = 10;
        while (true)
        {
            //c.fillArc(-25, 180, 240, 200, 90, 180); //Sled hill
            for (int i = 0 ; i < 100 ; i++) //Moves sled along arc
            {
                int x = DrawUtil.lerp (-50, 95, i / 99.0);
                int y = arcFunc (x);
                double slope = arcDerivativeFunc (x);
                double heading = Math.atan (slope);     //Get angle of the tangent of the arc
                c.setColor (new Color (193, 154, 107)); //Wood colour
                c.fillPolygon (     //Draws sled
                        new int[]
                {
                    x,
                        (int) (x + length * Math.cos (heading)),
                        (int) (x + length * Math.cos (heading) + width * Math.sin (heading)),
                        (int) (x + width * Math.sin (heading))
                }
                ,
                    new int[]
                {
                    y,
                        (int) (y + length * Math.sin (heading)),
                        (int) (y + length * Math.sin (heading) - width * Math.cos (heading)),
                        (int) (y - width * Math.cos (heading))
                }
                , 4);
                try
                {
                    Thread.sleep (10);
                }
                catch (Exception e)
                {
                }
                c.setColor (new Color (236, 247, 255)); //Mountian colour
                c.fillPolygon (     //Erases sled
                        new int[]
                {
                    x,
                        (int) (x + length * Math.cos (heading)),
                        (int) (x + length * Math.cos (heading) + width * Math.sin (heading)),
                        (int) (x + width * Math.sin (heading))
                }
                ,
                    new int[]
                {
                    y,
                        (int) (y + length * Math.sin (heading)),
                        (int) (y + length * Math.sin (heading) - width * Math.cos (heading)),
                        (int) (y - width * Math.cos (heading))
                }
                , 4);
            }
            for (int i = 100 ; i < 300 ; i++)   //Moves sled along bezier
            {
                double t = DrawUtil.lerp (0.0, 1.0, (i - 100) / 200.0); //Linear Interpolation
                int x = DrawUtil.bezierPoint (95, 190, 190, 350, t);    //Get x value of bezier
                int y = DrawUtil.bezierPoint (180, 170, 270, 370, t);   //Get y value of bezier
                double xTan = DrawUtil.bezierTangent (95, 190, 190, 350, t);    //Get x tangent
                double yTan = DrawUtil.bezierTangent (180, 170, 270, 370, t);   //Get y tangent
                double heading = Math.atan2 (yTan, xTan);   //Get angle of tangent of bezier
                c.setColor (new Color (193, 154, 107));     //Sled colour
                c.fillPolygon (     //Draw sled
                        new int[]
                {
                    x,
                        Math.min ((int) (x + length * Math.cos (heading)), 334),
                        Math.min ((int) (x + length * Math.cos (heading) + width * Math.sin (heading)), 351),
                        (int) (x + width * Math.sin (heading))
                }
                ,
                    new int[]
                {
                    Math.min (y, 361),
                        Math.min ((int) (y + length * Math.sin (heading)), 361),
                        Math.min ((int) (y + length * Math.sin (heading) - width * Math.cos (heading)), 361),
                        (int) (y - width * Math.cos (heading))
                }
                , 4);
                try
                {
                    Thread.sleep (10);
                }
                catch (Exception e)
                {
                }
                c.setColor (new Color (236, 247, 255));     //Background mountian
                c.fillPolygon (     //Erases sled
                        new int[]
                {
                    x,
                        Math.min ((int) (x + length * Math.cos (heading)), 334),
                        Math.min ((int) (x + length * Math.cos (heading) + width * Math.sin (heading)), 351),
                        (int) (x + width * Math.sin (heading))
                }
                ,
                    new int[]
                {
                    Math.min (y, 361),
                        Math.min ((int) (y + length * Math.sin (heading)), 361),
                        Math.min ((int) (y + length * Math.sin (heading) - width * Math.cos (heading)), 361),
                        (int) (y - width * Math.cos (heading))
                }
                , 4);
            }
        }
    }


    public void run ()
    {
        moveSled ();
    }
}
