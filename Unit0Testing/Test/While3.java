import java.awt.*;
import hsa.Console;

public class While3 {
    public static void main(String[] args) {
        Console c = new Console();
        c.println("Enter positive integers (0 to quit)");
        int input=1;
        int iterator=1;
        int ans=1;
        while(input!=0){
            c.print("#"+iterator+++": ");
            input =c.readInt();
            if(input<0)c.println(input+" not counted");
            else if(input!=0){
                ans*=input;
                iterator--;
            }
        }
        c.println();
        c.println("The product of the "+(iterator-1)+" postives is "+ans);
    }
}