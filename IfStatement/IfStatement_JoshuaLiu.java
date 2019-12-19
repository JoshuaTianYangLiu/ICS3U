/* 
    Joshua Liu
    November 13, 2019
    Mr. Afsari
    This program will calculate the missing vaule of the equation R=116/D
*/

import java.awt.*;
import hsa.Console;
import javax.swing.*;  

public class IfStatement_JoshuaLiu {
    Console c;
    char option;
    double input;
    Color greenBG= new Color(50, 168, 82);
    Color whiteText= new Color(255,255,255);
    Font tnr40 = new Font("TimesRoman", Font.PLAIN, 40);
    Font tnr20 = new Font("TimesRoman", Font.PLAIN, 20);
    IfStatement_JoshuaLiu(){    //Constructor
        c= new Console("If Statement Problem");
    }
    void title(){   //Title
        c.setColor(greenBG);    //Setbackground to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //White text for text
        c.setFont(tnr40);   //Times new roman size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        c.setFont(tnr20);   //Size 20
        c.drawString("Press any key to continue",250,450);  //Text
        c.getChar();    //Prompts for keyboard input
    }
    void intro(){
        c.setColor(greenBG);    //Background to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //White text
        c.setFont(tnr40);   //Times new roman size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        c.setFont(tnr20);   //Size 20
        c.drawString("Equation: R = 166 / D",50,100);   //Equation
        c.drawLine(50,120,220,120); //Line
        c.drawString("A: Solve for Resolution in seconds",50,200);  //Options
        c.drawString("B: Solve for Diameter of telescope",50,250);
        c.drawString("C: Pause program",50,300);
        c.drawString("D: Quit",50,350);
        String optionStr;
        while(true){
            optionStr = JOptionPane.showInputDialog(null,"Please enter an option","Put option here");   //Prompt for input
            try{
                if(optionStr.length()==1){  //Makes sure its a character
                    option=optionStr.charAt(0); //Gets character
                    if(option=='A'||option=='B'||option=='C'||option=='D')break; //Checks valid input
                }
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    void askData(){
        c.setColor(greenBG);    //Background to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //Set text to white
        c.setFont(tnr40);   //Size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        String inputStr;
        if(option=='A'){    //Solve for R
            c.drawString("D = ___mm",50,150);   //Draw string
            while(true){
                inputStr=JOptionPane.showInputDialog(null,"Enter the Diameter of telescope in mm.");    //prompt for input
                try{
                    input=Double.parseDouble(inputStr); //Parse input
                    break;
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
                }
            }
        }else{  //Solve for D
            c.drawString("R = ___s",50,150);    //Draw string
            while(true){
                inputStr=JOptionPane.showInputDialog(null,"Enter the Resolution in seconds.");  //Prompt for input
                try{
                    input=Double.parseDouble(inputStr); //Parse input
                    break;
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Please enter a valid input.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
                }
            }
        }
    }
    void display(){ //Display
        c.setColor(greenBG);    //Background to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //Set text to white
        c.setFont(tnr40);   //Size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        if(option=='A'){    //Solve for R
            c.drawString("D = "+input+" mm",50,150);    //Input
            double result = 166/input;  //Find answer
            c.drawString("R = "+result+" s",50,400);    //Result
        }else{
            c.drawString("R = "+input+" s",50,150); //Input
            double result = 166/input;  //Find answer
            c.drawString("D = "+result+" mm",50,400);   //Result
        }
        c.setFont(tnr20);   //Size 20
        c.drawString("Press any key to continue",250,450);  //String
        c.getChar();    //Prompt for keyboard input
    }
    void goodbye(){ //Exit program
        c.setColor(greenBG);    //Background to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //Set text to white
        c.setFont(tnr40);   //Size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        c.drawString("Thank you for using my program.",100,250);    //Line
        c.setFont(tnr20);   //Size 20
        c.drawString("Press any key to exit",250,450);  //String
        c.getChar();    //Prompt for keyboard input
        System.exit(0); //Exit program
    }
    void pauseProgram(){
        c.setColor(greenBG);    //Background to green
        c.fillRect(0,0,740,500);    //Draw background
        c.setColor(whiteText);  //Set text to white
        c.setFont(tnr40);   //Size 40
        c.drawString("Resolution and Diameter calculator", 20, 50); //Title
        c.drawString("PROGRAM PAUSED",175,250); //Line
        c.setFont(tnr20);   //Size 20
        c.drawString("Press any key to continue",250,450);  //String
        c.getChar();    //Prompt for keyboard input
    }
    public static void main(String[] args) throws Exception{
        IfStatement_JoshuaLiu j = new IfStatement_JoshuaLiu();
        j.title();  //Call title
        while(true){
            j.intro();  //Call intro
            if(j.option=='C'){  //If pause program
                j.pauseProgram();
                continue;   //Back to top of the loop
            }
            if(j.option=='D')break; //Break out of loop
            j.askData();    //Ask data
            j.display();    //Display
        }
        j.goodbye();    //Goodbye
    }
}
