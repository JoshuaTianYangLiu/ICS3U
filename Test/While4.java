import java.awt.*;
import hsa.Console;

public class While4 {
    public static void main(String[] args) {
        Console c = new Console();
        c.print("Retirement Account: ");
        int balance=c.readInt();
        c.println();
        c.println("Withdrawals");
        int input=0;
        int iterator=1;
        while(input!=-1&&balance>0){
            balance-=input;
            c.print("Year "+iterator+++": ");
            input=c.readInt();
        }
        c.println();
        c.println("You have $"+balance+" left.");
    }
}