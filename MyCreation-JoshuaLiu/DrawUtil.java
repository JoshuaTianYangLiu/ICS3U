/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.Math;
import java.io.File;
import hsa.Console;
//Utilities class for methods which is used in multiple classes
public class DrawUtil
{
    static BufferedImage buf;
    static Color gradientSky;
    static Color skyBg[] = {
        new Color (53, 92, 125),
        new Color (108, 91, 123),
        new Color (192, 108, 132)
        };
    static void loadBuffer ()	//Loads background buffer
    {
        try
        {	
			//The sheet says no imported images, only ones drawn by you /w java
			//This background is a saved image drawn with ready to program
			//So it does not break any rules
            buf = ImageIO.read (new File ("BackgroundBuf.png"));
        }
        catch (Exception e)
        {
        }
    }


    static void bezier (int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int strokeWeight, Console c)	//Draws bezier with a size
    {
        for (int i = 0 ; i < 500 ; i++)
        {
            double t = i / 500.0;
            int x = bezierPoint (x1, x2, x3, x4, t);	//Evaluate bezier point for x and y
            int y = bezierPoint (y1, y2, y3, y4, t);
            c.fillOval (x - strokeWeight / 2, y - strokeWeight / 2, strokeWeight, strokeWeight);	//Moves x and y coord to corner
																									//This is done for accurate thickness with strokeWeight
        }
    }


    static void bezier (int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int strokeWeight, int precision, Color col, Console c)	//Draws bezier with precision
    {
        for (int i = 0 ; i < precision ; i++) //Draws those many points
        {
            double t = i * 1.0 / precision;
            int x = bezierPoint (x1, x2, x3, x4, t);	//Evaluate bezier point for x and y
            int y = bezierPoint (y1, y2, y3, y4, t);
            c.setColor (col);	//Sets color to prevent other threads from changing the oval color
            c.fillOval (x - strokeWeight / 2, y - strokeWeight / 2, strokeWeight, strokeWeight);	//Moves x and y coord to corner
																									//This is done for accurate thickness with strokeWeight
        }
    }


    static void bezierReplaceBackground (int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int strokeWeight, int precision, Console c)	//This is used to redraw the nightsky for Birds class
    {
        for (int i = 0 ; i < precision ; i++)
        {
            double t = i * 1.0 / precision;
            int x = bezierPoint (x1, x2, x3, x4, t);
            int y = bezierPoint (y1, y2, y3, y4, t);
			//Coords are assumed as center, to allow strokeWeight to work properly
            for (int j = y - strokeWeight / 2 ; j <= y + strokeWeight / 2 ; j++)	//Forloop the y range of the bezier
            {
                if (j < 135)	//Get the gradient color based on y position
                {
                    gradientSky = lerpColor (skyBg [0], skyBg [1], j / 135.0);
                }
                else
                {
                    gradientSky = lerpColor (skyBg [1], skyBg [2], (j - 135) / 135.0);
                }
                c.setColor (gradientSky);
                c.drawLine (x - strokeWeight / 2 - 1, j, x + strokeWeight / 2, j);	//Draws line so it's one pixel larger than the image
            }
        }
    }

    static int bezierPoint (int x1, int x2, int x3, int x4, double t)	//Calculates point of bezier using bezier formula
    {
        return (int) (Math.pow (1 - t, 3) * x1 + 3 * t * Math.pow (1 - t, 2) * x2 + 3 * Math.pow (t, 2) * (1 - t) * x3 + Math.pow (t, 3) * x4);
    }


    static double bezierTangent (int x1, int x2, int x3, int x4, double t)	//Calculates the tangent/derivative of the bezier
    {
        return 3 * (1 - t) * (1 - t) * (x2 - x1) + 6 * (1 - t) * t * (x3 - x2) + 3 * t * t * (x4 - x3);
    }


    static int lerp (int x, int y, double t)	//Linear Interpolation forumla
    {
        return (int) ((1 - t) * x + y * t);
    }


    static double lerp (double x, double y, double t)	//Lerp with decimal precision
    {
        return (1 - t) * x + y * t;
    }


    static Color lerpColor (Color x, Color y, double t)	//Lerp with colors
    {
        return new Color (
                lerp (x.getRed (), y.getRed (), t),
                lerp (x.getGreen (), y.getGreen (), t),
                lerp (x.getBlue (), y.getBlue (), t));
    }

    static BufferedImage getBackgroundCrop (int x, int y, int l, int w)	//Gets a crop of an image of the background
    {
        x = Math.max (0, x);		//Makes sure that values are in the correct constraints
        x = Math.min (640, x);
        y = Math.max (0, y);
        y = Math.min (500, y);
        if (x + l >= 640)
            l = 640 - x;
        if (y + w >= 500)
            w = 500 - y;
        return buf.getSubimage (x + 2, y + 2, l, w);
    }
}
