import hsa.Console;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class DrawGame{
    Console boardGame;
    BufferedImage boardImage;
    DrawGame(){
        boardGame=new Console(38,111,"Monopoly");
        try{
            boardImage=ImageIO.read(new File("assets\\Images\\MonopolyBoard.jpg"));
            //999x760
        }catch(Exception e){
            System.out.println("Error reading MonopolyBoard.jpg");
            e.printStackTrace();
            System.exit(1);
        }
    }
    void drawBoard(){
        System.out.println(boardGame.getHeight()+" "+boardGame.getWidth());
        // boardGame.drawImage(boardImage, 0, 0,null);
    }
    public static void main(String[] args){
        DrawGame d = new DrawGame();
        d.drawBoard();
    }
}