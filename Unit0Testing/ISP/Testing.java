import hsa.Console;
import java.io.*;


public class Testing{
    public static void main(String[] args) throws Exception{
        Console c = new Console();
        c.println("Testing");
        try{
            Thread.sleep(5000);
        }catch(Exception e){}
        c.println("Running");
        while(true){
            c.println(c.isCharAvail());
        }
        c.println("Finish");
    }
}