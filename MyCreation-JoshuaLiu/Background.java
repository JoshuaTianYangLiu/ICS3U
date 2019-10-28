/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import hsa.Console;
public class Background
{
    Console c;
    Background (Console c)      //Constructor
    {
        this.c = c;
        drawBackground ();
    }


    Color nightSky[] = {
        new Color (53, 92, 125),
        new Color (108, 91, 123),
        new Color (192, 108, 132)
        };
    Color gradientSky;
    void drawBackground ()
    {
        for (int i = 0 ; i < 135 ; i++)     //Draws a gradient of the night sky based on linear interpolation formula
        {
            gradientSky = DrawUtil.lerpColor (nightSky [0], nightSky [1], i / 135.0);
            c.setColor (gradientSky);
            c.drawLine (0, i, 640, i);
        }
        for (int i = 135 ; i < 270 ; i++)     //Draws a gradient of the night sky based on linear interpolation formula
        {
            gradientSky = DrawUtil.lerpColor (nightSky [1], nightSky [2], (i - 135) / 135.0);
            c.setColor (gradientSky);
            c.drawLine (0, i, 640, i);
        }

        //Back hill
        c.setColor (new Color (211, 207, 201));
        c.fillArc (120, 170, 400, 400, 0, 180); //Outer ring
        c.setColor (new Color (252, 252, 232));
        c.fillArc (145, 195, 350, 350, 0, 180); //Hill

        //Right hill
        c.setColor (new Color (235, 240, 247));
        c.fillArc (420, 165, 1000, 440, 120, 270);  //Outer ring
        c.setColor (new Color (216, 225, 234));
        c.fillArc (440, 185, 1000, 440, 120, 270);  //Hill

        //Left hill
        c.setColor (new Color (236, 247, 255));
        c.fillArc (-35, 150, 260, 220, 90, 180);    //Outer ring
        DrawUtil.bezier (100, 180, 200, 180, 200, 280, 370, 390, 60, c);    //Outer ring
        c.setColor (new Color (211, 229, 247));  //Sled hill
        c.fillArc (-25, 180, 240, 200, 90, 180); //Sled hill
        for (int i = 0 ; i < 200 ; i += 20)
        {
            DrawUtil.bezier (90, 190 + i, 190, 190 + i, 190, 290 + i, 340, 380 + i, 20, c); //Fills the hill with a solid color
        }

        //Bottom gray hill
        c.setColor (new Color (232, 232, 233));
        DrawUtil.bezier (-35, 365, 65, 290, 115, 290, 265, 365, 30, c); //Draws the curved hill
        DrawUtil.bezier (265, 365, 315, 390, 340, 390, 415, 410, 30, c);
        DrawUtil.bezier (415, 410, 465, 440, 465, 465, 425, 515, 30, c);
        DrawUtil.bezier (265, 365, 315, 390, 565, 365, 665, 390, 30, c);
        c.fillRect (440, 390, 300, 500);    //Fills in missing spots
        c.fillRect (410, 380, 30, 30);      //Fills in missing spots

        //Bottom white hill
        c.setColor (new Color (201, 210, 224));
        DrawUtil.bezier (-35, 395, 65, 320, 115, 320, 265, 395, 30, c); //Draws curved hill
        DrawUtil.bezier (265, 395, 315, 420, 340, 420, 415, 440, 30, c);
        DrawUtil.bezier (415, 440, 440, 460, 440, 465, 410, 515, 30, c);
        c.fillRect (0, 440, 420, 120);      //Fills in missing spots
        c.fillRect (0, 410, 360, 40);       //Fills in missing spots
        c.fillRect (0, 390, 290, 20);       //Fills in missing spots
        c.fillRect (0, 380, 250, 10);       //Fills in missing spots
        c.fillRect (10, 350, 186, 30);      //Fills in missing spots

        //Ice lake
        c.setColor (new Color (148, 194, 244));
        c.fillOval (20, 360, 180, 120);     //Large lake
        c.fillOval (120, 390, 140, 100);
        c.fillOval (335, 435, 80, 50);      //Small lake

        //Cabin
        c.setColor (new Color (86, 112, 135));
        c.fillRect (300, 240, 75, 40);      //Draws house wall
        c.setColor (new Color (51, 37, 30));
        c.fillRect (340, 210, 15, 30);      //Chimney
        c.setColor (new Color (30, 63, 84));
        c.fillPolygon (                     //Roof
                new int[]
        {
            300, 375, 325
        }
        ,
            new int[]
        {
            240, 240, 220
        }
        ,
            3
            );
        c.setColor (new Color (111, 146, 188));
        c.fillRect (311, 250, 10, 10);      //Left Window
        c.fillRect (333, 250, 10, 10);      //Middle Window
        c.fillRect (354, 250, 10, 10);      //Right Window
        c.setColor (new Color (51, 37, 30));
        c.fillRect (328, 265, 20, 15);      //Door
    }
}
