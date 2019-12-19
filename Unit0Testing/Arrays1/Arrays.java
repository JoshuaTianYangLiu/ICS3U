import java.awt.*;
import hsa.Console;

public class Arrays {
    static Console c=new Console();
    public static void main(String[] args) {
        int arr[]={1,2,3,4,5,6,7,8,9};
        String stringArr[]={"Ali", "Mohammed", "John", "Maria"};
        c.print("Sum of ");
        print(arr);
        c.println(": "+sumArr(arr));
        c.println();

        c.print("Index of max element of ");
        print(arr);
        c.println(": "+maxArr(arr));
        c.println();

        c.print("Index of min element of ");
        print(arr);
        c.println(": "+minArr(arr));
        c.println();

        c.print("Index of element 3 in array ");
        print(arr);
        c.println(": "+findVal(arr,3));
        c.println();

        c.print("Index of element Maria in array ");
        print(stringArr);
        c.println(": "+findName(stringArr,"Maria"));
        c.println();

        c.print("A shuffle of array ");
        print(arr);
        c.print(": ");
        print(shuffle(arr));
        c.println();
        
    }
    static int sumArr(int arr[]){
        int sum=0;
        for(int i=0; i<arr.length; i++)sum+=arr[i];
        return sum;
    }
    static int maxArr(int arr[]){
        int max=arr[0];
        for(int i=1; i<arr.length; i++)max=Math.max(max,arr[i]);
        return max;
    }
    static int minArr(int arr[]){
        int min=arr[0];
        for(int i=1; i<arr.length; i++)min=Math.min(min,arr[i]);
        return min;
    }
    static int findVal(int arr[],int val){
        for(int i=0; i<arr.length; i++){
            if(arr[i]==val)return i;
        }
        return -1;
    }
    static int findName(String arr[],String val){
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals(val))return i;
        }
        return -1;
    }
    static void print(int arr[]){
        for(int i=0; i<arr.length; i++)c.print(arr[i]+(i==arr.length-1?"":","));
    }
    static void print(String arr[]){
        for(int i=0; i<arr.length; i++)c.print(arr[i]+(i==arr.length-1?"":","));
    }
    static int[] shuffle(int arr[]){
        for(int i=0; i<arr.length*10; i++){
            int a=(int)(Math.random()*arr.length);
            int b=(int)(Math.random()*arr.length);
            while(a==b)b=(int)(Math.random()*arr.length);
            arr[a]=arr[a]+arr[b];
            arr[b]=arr[a]-arr[b];
            arr[a]=arr[a]-arr[b];
        }
        return arr;
    }
}
