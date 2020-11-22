/*
Joshua Liu
Dec 17,2019
Mr.Afsari
This program will add a suffix to any substring which matches another string
*/

import java.awt.*;
import hsa.Console;
import javax.swing.*;  

public class StringMethods {
    Console con;
    int choice;
    String str,search,suffix;
    Color purpleBG= new Color(128,0,128);
    Color whiteText= new Color(255,255,255);
    Font tnr80 = new Font("TimesRoman", Font.PLAIN, 80);
    Font tnr40 = new Font("TimesRoman", Font.PLAIN, 40);
    Font tnr20 = new Font("TimesRoman", Font.PLAIN, 20);
    StringMethods(){
        con=new Console("String Return Methods");
    }
    void splashScreen(){
        con.setColor(purpleBG);    //Set background to purple
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text for text
        con.setFont(tnr40);   //Times new roman size 40
        con.setColor(whiteText);	//White text for text
        con.drawString("S R M",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("S Re Me",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("St Ret Met",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("St Retu Met",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("St Retu Meth",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("Str Retur Meth",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("Stri Retur Meth",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("Stri Retur Metho",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("Stri Retur Method",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("Strin Return Method",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.setColor(whiteText);	//White text for text
        con.drawString("String Return Method",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setColor(purpleBG);		//Set background to purple
        con.fillRect(100, 200, 500, 75);	//Redraw background
        con.drawString("String Return Methods",100,250);
        con.setColor(whiteText);	//White text for text
        con.drawString("String Return Methods",100,250);
        try{Thread.sleep(500);}catch(Exception e){}
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //Text
        con.getChar();
    }
    void title(){
        con.setColor(purpleBG);    //Set background to purple
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text for text
        con.setFont(tnr40);   //Times new roman size 40
        con.drawString("String Return Methods", 20, 50); //Title
        con.setFont(tnr20);   //Size 20
    }
    void mainMenu(){
        title();
        con.drawString("Main menu",50,100);   //Equation
        con.drawLine(50,120,220,120); //Line
        con.drawString("1: Add Suffix",50,200);  //Options
        con.drawString("2: Instructions",50,250);
        con.drawString("3: Pause Program",50,300);
        con.drawString("4: Quit",50,350);
        String optionStr;
        while(true){
            optionStr = JOptionPane.showInputDialog(null,"Please enter an option","Put option here");   //Prompt for input
            try{
                choice = Integer.parseInt(optionStr);
                if(choice<5&&choice>0)break;
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    void instructions(){
        title();
        con.drawString("Instruction",50,100);   //Equation
        con.drawLine(50,120,220,120); //Line
        con.drawString("Enter 3 strings, the string itself, the search string, and the suffix string",50,200);  //Options
        con.drawString("This program will search through the string and if there's any matches,",50,250);  //Options
        con.drawString("it'll add the suffix",50,300);
        con.drawString("Press any key to continue",250,450);  //Text
        con.getChar();
    }
    private String addSuffix(String str,String search,String suffix){    //Have the expression simplify correctly so it looks nicer
        String returnVal="";
        int i=0;
        for(; i<=str.length()-search.length(); i++){    //Loop through main string
            boolean isEqual=true;
            for(int j=i; j<i+search.length(); j++){ //Checks if substring equal the search string
                if(str.charAt(j)!=search.charAt(j-i))isEqual=false; //Set false if not equal
            }
            if(isEqual){    //If string matches
                for(int j=i; j<i+search.length(); j++)returnVal+=str.charAt(j); //Append the substring
                returnVal+=suffix;  //Append the suffix
                i+=search.length()-1;   //move the character pointer to the next character that needs to be analysed, after the substring
            }else{
                returnVal+=str.charAt(i); //Append the character
            }
        }
        for(;i<str.length(); i++){  //Append any leftover string that we know won't fit with any search string
            returnVal+=str.charAt(i);
        }
        return returnVal;
    }
    void askData(){
        title();
        con.drawString("Input Data",50,100);   //Equation
        con.drawLine(50,120,220,120); //Line
        con.drawString("Main String:",50,150);   //Draw string
        while(true){
            str=JOptionPane.showInputDialog(null,"Enter the main String.");    //prompt for input
            if(str.length()>0)break;
            JOptionPane.showMessageDialog(null, "Please non empty string.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
        }
        con.drawString("Main String: "+str,50,150);   //Draw string
        con.drawString("Search String: ",50,250);   //Draw string
        while(true){
            search=JOptionPane.showInputDialog(null,"Enter the search string.");    //prompt for input
            if(search.length()>0)break;
            JOptionPane.showMessageDialog(null, "Please non empty string.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
        }
        con.drawString("Search String: "+search,50,250);   //Draw string
        con.drawString("Suffix String: ",50,350);   //Draw string
        while(true){
            suffix=JOptionPane.showInputDialog(null,"Enter the suffix string.");    //prompt for input
            if(suffix.length()>0)break;
            JOptionPane.showMessageDialog(null, "Please non empty string.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
        }
        con.drawString("Suffix String: "+suffix,50,350);   //Draw string
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    void pauseProgram(){
        title();
        con.setFont(tnr40);   //Size 40
        con.drawString("PROGRAM PAUSED",175,250); //Line
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    void goodbye(){
        title();
        con.drawString("Thank you for using my program.",100,250);    //Line
        con.setFont(tnr20);   //Size 20
        con.drawString("Made by Joshua Liu",100,350);
        con.drawString("Press any key to exit",250,450);  //String
        con.getChar();    //Prompt for keyboard input
        System.exit(0); //Exit program
    }
    void display(){
        String result=addSuffix(str, search, suffix);
        title();
        con.drawString("The resulting string is:", 50, 150);
        con.drawString(result, 50, 250);
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    public static void main(String[] args) {
        StringMethods s = new StringMethods();
        s.splashScreen();
        while(true){
            s.mainMenu();
            if(s.choice==1){
                s.askData();
                s.display();
            }else if(s.choice==2){
                s.instructions();
            }else if(s.choice==3){
                s.pauseProgram();
            }else if(s.choice==4){
                break;
            }
        }
        s.goodbye();
    }
}
