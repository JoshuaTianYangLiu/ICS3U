import java.awt.*;
import java.math.BigInteger;

import hsa.Console;

public class Method {
    static Console c=new Console();
    public static void main(String[] args) {
        while(true){
            c.println("Methods Practice");
            c.println("1: Get sum of a range of numbers");
            c.println("2: Calculate n!");
            c.println("3: Check if value is prime");
            c.println("4: Quit");
            int choice=getInt(4,1);
            c.clear();
            if(choice==1) runSumRange();
            else if(choice==2) runFactorial();
            else if(choice==3) runPrime();
            else break;
            c.println("Press any key to continue...");
            c.getChar();
            c.clear();
        }
        c.println("Good bye!");
        c.println("Press any key to quit...");
        c.getChar();
        System.exit(0);
    }
    static void runPrime(){
        c.println("Primality test");
        int n = getInt(Integer.MAX_VALUE,0);
        c.println();
        if(prime(n))c.println(n+" is prime");
        else c.println(n+" is not prime");
    }
    static void runFactorial(){
        c.println("Factorial");
        int n = getInt(18,0);
        c.println();
        c.println("The factorial of "+n+" is: "+factorial(n));
    }
    static void runSumRange(){
        c.println("Sum range");
        int a = getInt(1000000,-1000000);
        int b = getInt(1000000,a);
        c.println();
        c.println("The sum of the consecutive integers from "+a+" to "+b+" is: "+sumRange(a, b));
    }
    static int getInt(int h,int l){
        while(true){
            c.print("Enter a number: ");
            int input = c.readInt();
            if(input<=h&&input>=l){
                return input;
            }
            c.clear();
            c.println("Please enter a valid integer between "+l+" and "+h+" inclusive");
        }
    }
    static int sumRange(int a,int b){
        return b*(b+1)/2-a*(a+1)/2;
    }
    static long factorial(int n){
        long ans=1;
        for(int i=2; i<n; i++)ans*=i;
        return ans;
    }
    static boolean prime(int n){
        BigInteger a = new BigInteger(n+"");    //You never said what to use
        return a.isProbablePrime(1);
    }
}