import java.io.*;
import hsa.Console;

public class Joshua_Part3{
    final int MAX=20;
    Console c;
    Joshua_Part3(){
        c=new Console();
    }
    void getAndStoreInput(){
        double arr[]= new double[MAX];
        for(int i=0; i<MAX;){
            try{
                arr[i]=Double.parseDouble(c.readLine());
                i++;
            }catch(Exception e){
                c.println("Please enter a double");
            }
        }
        try{
            PrintWriter bw = new PrintWriter(new FileWriter("data.txt"));
            for(int i=0; i<MAX; i++){
                bw.printf("%.2f\n",arr[i]);
            }
            bw.close();
        }catch(Exception e){}
    }
    void displayInput(){
        c.println("Press any key to display to file");
        c.getChar();
        c.clear();
        try{
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            for(int i=0; i<MAX; i++){
                c.println(Double.parseDouble(br.readLine()),0,2);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        c.println("Press any key to close");
        c.getChar();
        c.close();
    }
    public static void main(String[] args){
        Joshua_Part3 j = new Joshua_Part3();
        j.getAndStoreInput();
        j.displayInput();
    }
}