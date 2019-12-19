/*
Joshua Liu
Nov 28,2019
Mr.Afsari
This program solves the quadratic equation
*/

import java.awt.*;
import hsa.Console;
import javax.swing.*;  

public class Methods {
    Console con;
    char choice;
    int a,b,c,d;
    Color blueBG= new Color(50, 98, 168);
    Color whiteText= new Color(255,255,255);
    Font tnr80 = new Font("TimesRoman", Font.PLAIN, 80);
    Font tnr40 = new Font("TimesRoman", Font.PLAIN, 40);
    Font tnr20 = new Font("TimesRoman", Font.PLAIN, 20);
    Methods(){
        con=new Console("Math Return Methods");
    }
    private int lerp (int x, int y, double t)	//Linear Interpolation forumla
    {
        return (int) ((1 - t) * x + y * t);
    }
    private Color lerpColor (Color x, Color y, double t)	//Lerp with colors
    {
        return new Color (
                lerp (x.getRed (), y.getRed (), t),
                lerp (x.getGreen (), y.getGreen (), t),
                lerp (x.getBlue (), y.getBlue (), t));
    }
    void splashScreen(){
        con.setColor(blueBG);    //Setbackground to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text for text
        con.setFont(tnr80);   //Times new roman size 80
        con.drawString("a",100,250);
        con.drawString("x",140,250);
        con.setFont(tnr40);   //Times new roman size 40
        con.drawString("2",180,220);
        con.setFont(tnr80);   //Times new roman size 80
        con.drawString("+",220,250);
        con.drawString("b",260,250);
        con.drawString("x",300,250);
        con.drawString("+",340,250);
        con.drawString("c",380,250);
        con.drawString("=",420,250);
        con.drawString("d",460,250);
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        for(int i=0; i<100; i++){
            con.setColor(lerpColor(whiteText,blueBG,i/100.0));
            con.drawString("a",100,250);
            con.drawString("x",140,250);
            con.setFont(tnr40);   //Times new roman size 40
            con.drawString("2",180,220);
            con.setFont(tnr80);   //Times new roman size 80
            con.drawString("+",220,250);
            con.drawString("b",260,250);
            con.drawString("x",300,250);
            con.drawString("+",340,250);
            con.drawString("c",380,250);
            con.drawString("=",420,250);
            con.drawString("d",460,250);
            con.setColor(lerpColor(whiteText,blueBG,(100-i)/100.0));
            con.drawString("x",50,250);
            con.drawString("=",90,250);
            con.drawLine(150,220,700,220);
            con.drawString("-",150,200);
            con.drawString("b",190,200);
            con.drawString("-",250,220);
            con.drawString("+",240,180);
            con.drawLine(290,180,300,215);
            con.drawLine(300,215,310,120);
            con.drawLine(310,120,700,120);
            con.drawString("b",330,200);
            con.setFont(tnr40);   //Times new roman size 40
            con.drawString("2",370,180);
            con.setFont(tnr80);   //Times new roman size 80
            con.drawString("-",410,200);
            con.drawString("4",450,200);
            con.drawString("a",490,200);
            con.drawString("(",530,200);
            con.drawString("c",570,200);
            con.drawString("-",610,200);
            con.drawString("d",650,200);
            con.drawString(")",690,200);
            con.drawString("2",340,280);
            con.drawString("a",380,280);
        }
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //Text
        con.getChar();
    }
    void title(){
        con.setColor(blueBG);    //Setbackground to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text for text
        con.setFont(tnr40);   //Times new roman size 40
        con.drawString("Math Return Methods", 20, 50); //Title
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //Text
        con.getChar();    //Prompts for keyboard input
    }
    void mainMenu(){
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text
        con.setFont(tnr40);   //Times new roman size 40
        con.drawString("Math Return Methods", 20, 50); //Title
        con.setFont(tnr20);   //Size 20
        con.drawString("Equation: ax +bx+c=d",50,100);   //Equation
        con.drawString("2",150,90);
        con.drawLine(50,120,220,120); //Line
        con.drawString("A: Solve quadratic",50,200);  //Options
        con.drawString("B: Instructions",50,250);
        con.drawString("C: Pause Program",50,300);
        con.drawString("D: Quit",50,350);
        String optionStr;
        while(true){
            optionStr = JOptionPane.showInputDialog(null,"Please enter an option","Put option here");   //Prompt for input
            try{
                if(optionStr.length()==1){  //Makes sure its a character
                    choice=optionStr.charAt(0); //Gets character
                    if(choice=='A'||choice=='B'||choice=='C'||choice=='D')break; //Checks valid input
                }
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter a valid input", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    void instructions(){
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //White text
        con.setFont(tnr40);   //Times new roman size 40
        con.drawString("Math Return Methods: Instructions", 20, 50); //Title
        con.setFont(tnr20);   //Size 20
        con.drawString("Equation: ax +bx+c=d",50,100);   //Equation
        con.drawString("2",150,90);
        con.drawLine(50,120,220,120); //Line
        con.drawString("You will provide the variables for a,b,c,d, the input must be an integer",50,200);  //Options
        con.drawString("Press any key to continue",250,450);  //Text
        con.getChar();
    }
    private String evalExpression(int a,int b,int c,int d,int type){    //Have the expression simplify correctly so it looks nicer
        String out="";
        if(type==0)return "ax^2+bx+c=d";
        if(a==1)out+="x^2";
        else if(a==-1)out+="-x^2";
        else out+=a+"x^2";
        if(type==1)return out+"+bx+c=d";
        if(b==1)out+="+x";
        else if(b==-1)out+="-x";
        else if(b>0) out+="+"+b+"x";
        else if(b<0) out+=b+"x";
        if(type==2)return out+"+c=d";
        if(c<0)out+=c;
        else if(c>0)out+="+"+c;
        if(type==3)return out+"=d";
        out+="="+d;
        return out;
    }
    void askData(){
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.setFont(tnr40);   //Size 40
        con.drawString("Resolution and Diameter calculator", 20, 50); //Title
        String inputStr;
        
        con.drawString(evalExpression(0, 0, 0, 0, 0),50,150);   //Draw string
        while(true){
            inputStr=JOptionPane.showInputDialog(null,"Enter the value of a.");    //prompt for input
            try{
                a=Integer.parseInt(inputStr); //Parse input
                if(a!=0)break;
                JOptionPane.showMessageDialog(null, "Please enter an non-zero Integer.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter an Integer.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,100,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.drawString(evalExpression(a, 0, 0, 0, 1),50,150);   //Draw string   //Draw string
        while(true){
            inputStr=JOptionPane.showInputDialog(null,"Enter the value of b.");    //prompt for input
            try{
                b=Integer.parseInt(inputStr); //Parse input
                break;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter an Integer.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,100,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.drawString(evalExpression(a, b, 0, 0, 2),50,150);   //Draw string
        while(true){
            inputStr=JOptionPane.showInputDialog(null,"Enter the value of c.");    //prompt for input
            try{
                c=Integer.parseInt(inputStr); //Parse input
                break;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter an Integer.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,100,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.drawString(evalExpression(a, b, c, 0, 3),50,150);   //Draw string
        while(true){
            inputStr=JOptionPane.showInputDialog(null,"Enter the value of d.");    //prompt for input
            try{
                d=Integer.parseInt(inputStr); //Parse input
                break;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter an Integer.", "Warning",JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,100,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.drawString(evalExpression(a, b, c, d, 4),50,150);   //Draw string
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    void pauseProgram(){
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.setFont(tnr40);   //Size 40
        con.drawString("Math Return Methods", 20, 50); //Title
        con.drawString("PROGRAM PAUSED",175,250); //Line
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    void goodbye(){
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.setFont(tnr40);   //Size 40
        con.drawString("Math Return Methods", 20, 50); //Title
        con.drawString("Thank you for using my program.",100,250);    //Line
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to exit",250,450);  //String
        con.getChar();    //Prompt for keyboard input
        System.exit(0); //Exit program
    }

    private double[] quadEquation(int a,int b,int c,int d){
        int discriminant = b*b-4*a*(c-d);
        if(discriminant<0)return new double[0];
        if(discriminant==0)return new double[]{(double)-b/(2*a)};
        double low=0;
        double high=discriminant;
        double dif=1;
        while(dif<0.0001){
            double mid=(low+high)/2;
            if(mid*mid>=discriminant){
                high=mid;
                dif=low*low-discriminant;
                if(dif<0)dif=-dif;
            }else{
                low=mid;
                dif=low*low-discriminant;
                if(dif<0)dif=-dif;
            }
        }
        double ret1=(-b-low)/(2*a);
        double ret2=(-b+low)/(2*a);
        return new double[]{ret1,ret2};
    }
    void display(){
        double blackBox[] = quadEquation(a, b, c, d);
        con.setColor(blueBG);    //Background to green
        con.fillRect(0,0,740,500);    //Draw background
        con.setColor(whiteText);  //Set text to white
        con.setFont(tnr40);   //Size 40
        con.drawString("Math Return Methods", 20, 50); //Title
        con.drawString(evalExpression(a, b, c, d, 4),50,150);    //Input
        con.setFont(tnr20);   //Size 20
        if(blackBox.length==0){
            con.drawString("There are no solutions",50,250);    //Result
        }else if(blackBox.length==1){
            con.drawString("There is 1 solution",50,250);    //Result
            con.drawString("x="+blackBox[0],50,350);    //Result
        }else{
            con.drawString("There is 2 solutions",50,250);    //Result
            con.drawString("x="+blackBox[0]+","+blackBox[1],50,350);    //Result
        }
        con.setFont(tnr20);   //Size 20
        con.drawString("Press any key to continue",250,450);  //String
        con.getChar();    //Prompt for keyboard input
    }
    public static void main(String[] args) {
        Methods m = new Methods();
        m.splashScreen();
        m.title();
        while(true){
            m.mainMenu();
            if(m.choice=='A'){
                m.askData();
                m.display();
            }else if(m.choice=='B'){
                m.instructions();
            }else if(m.choice=='C'){
                m.pauseProgram();
            }else if(m.choice=='D'){
                break;
            }
        }
        m.goodbye();
    }
}
