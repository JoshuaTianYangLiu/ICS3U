import hsa.Console;
import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;

public class DrawDice{
    static Console c;
    static BufferedImage dice[] = new BufferedImage[7];
    DrawDice(){}
    
    /** 
     * Name: loadAssets
     * @param con
     * Loads necessary images for animation
     */
    public static void loadAssets(Console con){
        c=con;
        for(int i=1; i<=6; i++){
            try{
                dice[i]=ImageIO.read(new File("assets\\Images\\Dice"+i+".png"));
            }catch(Exception e){
                c.close();
                Util.exception("Error reading dice"+i+".png", e);
            }
        }
    }
    
    /** 
     * Name: drawDice
     * @param diceVal
     * @param x
     * @param y
     * draws dices with value and at certain coordinates
     */
    public static void drawDice(int diceVal,int x,int y){
        c.drawImage(dice[diceVal],x,y,null);
    }
}