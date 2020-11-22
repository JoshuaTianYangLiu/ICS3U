import java.awt.*;
import hsa.Console;

public class While2 {
    public static void main(String[] args) {
        Console c = new Console();
        c.print("Distance to destination: ");
        int input=c.readInt();
        c.println();
        double totalDist=0;
        int step=1;
        while(totalDist<input){
            c.print("Step "+step+++" Distance: ");
            totalDist+=c.readDouble();
            c.println("\tTotal is "+totalDist+" km");
        }
        c.println();
        c.println("You have reached your destination.");
    }
}