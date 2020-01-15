import java.io.*;
import hsa.Console;

public class Joshua_Part3{
    String input[] = new String[5];
    Console c;
    Joshua_Part3(){
        c=new Console();
    }
    void read(){
        c.println("Press any key to read file");
        c.getChar();
        try{
            BufferedReader br = new BufferedReader(new FileReader("dataIn.txt"));
            for(int i=4; i>=0; i--){
                input[i]=br.readLine();
            }
            br.close();
        }catch(Exception e){}
    }
    void write(){
        c.println("Press any key to write ");
        for(int i=4; i>=0; i--)c.print(input[i]+" ");
        c.println("to file");
        c.getChar();
        try{
            PrintWriter bw = new PrintWriter(new FileWriter("dataOut.txt"));
            for(int i=0; i<5; i++){
                bw.print(input[i]+"          ");
            }
            bw.close();
        }catch(Exception e){}
        c.close();
    }
    public static void main(String[] args){
        Joshua_Part3 j = new Joshua_Part3();
        j.read();
        j.write();
    }
}