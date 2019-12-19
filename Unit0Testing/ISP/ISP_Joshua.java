import hsa.Console;
import java.io.*;
import java.util.*;

public class ISP_Joshua{
    Console c;
    ISP_Joshua(){
        c=new Console("Monopoly");
    }
    void splashScreen(){

    }
    int mainMenu(){
        return 1;
    }
    void instructions(){

    }
    void rules(){

    }
    void settings(){

    }
    void goodbye(){

    }
    void display(){
import hsa.Console;
import java.io.*;


public class ReadFile{
    static String prompt[] = new String[31000];
    static String theme[] = new String[31000];
    public static void main(String[] args) throws Exception{
        Console c = new Console();
        BufferedReader br = new BufferedReader(new FileReader("WordList.txt"));
        int b=0;
        for(String a = br.readLine(); a!=null; a=br.readLine(),b++){
            String[] cTemp = a.split("\\|");
            prompt[b]=cTemp[0];
            theme[b]=cTemp[1];
        }
        br.close();
        while(true){
            int choice= (int)(Math.random()*29532);
            String phrase = prompt[choice];
            c.println("Hint: "+theme[choice]);
            phrase = phrase.replaceAll("[A-z]", "_ ");
            int lives=5;
            while(lives>0&&phrase.contains("_")){
                c.println("Hint: "+theme[choice] + " "+prompt[choice].length()+" characters");
                c.println(phrase+" Lives: "+lives);
                // c.println(prompt[choice]+" DEBUG INFO");
                c.println("Choose Option:");
                c.println("--------------------");
                c.println("1: Choose character");
                c.println("2: Solve phrase");
                int option;
                while(true){
                    try{
                        option=Integer.parseInt(c.readString());
                        if(option==1||option==2)break;
                        c.println("Choose option 1 or 2");
                    }catch(Exception e){
                        c.println("Enter option reee: ");
                    }
                }
                if(option==1){
                    c.print("Enter a character: ");
                    char input=c.readLine().charAt(0);
                    String inputStr=input+"";
                    if(prompt[choice].contains(input+"")&&inputStr.matches("[A-z]")){
                        for(int i=0; i<prompt[choice].length(); i++){
                            if(prompt[choice].charAt(i)==input){
                                phrase=phrase.substring(0,2*i)+input+phrase.substring(2*i+1);
                            }
                        }
                    }else{
                        lives--;
                    }
                }else{
                    c.print("Solve the phrase");
                    if(prompt[choice].toLowerCase().equals(c.readLine().toLowerCase())){
                        phrase=prompt[choice];
                    }else{
                        lives--;
                    }
                }
            }
            if(lives==0){
                c.println("YOU LOST");
                c.println("Phrase is "+prompt[choice]);
            }else{
                c.println("YOU WIN!");
            }
            c.println("Press any key for a new game");
            c.getChar();
            c.println();
            c.println("NEW GAME");
            c.println("-----------");
        }
    }
}
    }
    void scoreboard(){

    }
    public static void main(String[] args){
        ISP_Joshua isp = new ISP_Joshua();
        isp.splashScreen();
        for(int i =isp.mainMenu(); i!=6; i=isp.mainMenu()){
            if(i==1)isp.display();
            if(i==2)isp.instructions();
            if(i==3)isp.rules();
            if(i==4)isp.settings();
            if(i==5)isp.scoreboard();
        }
        isp.goodbye();
    }
}