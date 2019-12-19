import java.awt.*;
import hsa.Console;

public class While1 {
    public static void main(String[] args) {
        Console c = new Console();
        c.print("Enter an integer: ");
        int input=c.readInt();
        c.println();
        c.print("Multiples of "+input+" are ");
        int mul=input;
        mul+=input;
        while(mul<100){
            c.print(mul+", ");
            mul+=input;
        }
    }
}