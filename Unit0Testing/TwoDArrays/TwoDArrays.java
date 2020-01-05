import hsa.Console;
import java.io.*;
import java.util.*;

public class TwoDArrays{
    public static void main(String[] args){
        Console c = new Console();
        /////////
        // #1  //
        /////////
        int values[][] = new int[4][5];
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                values[i][j]=j;
                c.print(values[i][j]+" ");
            }
            c.println();
        }

        /////////
        // #2  //
        /////////
        c.println();
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                values[i][j]=j+i;
                c.print(values[i][j]+" ");
            }
            c.println();
        }

        /////////
        // #3  //
        /////////
        c.println();
        int questionThree[][] = new int[5][5];
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                questionThree[i][j]=j==i?i+1:0;
                c.print(questionThree[i][j]+" ");
            }
            c.println();
        }

        /////////
        // #4  //
        /////////
        c.println();
        int questionFour[][] = {{0,171,115,141,240},
                                {171,0,103,194,333},
                                {115,103,0,120,235},
                                {141,194,120,0,104},
                                {240,333,235,104,0}};
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                c.print(questionFour[i][j]+" ");
            }
            c.println();
        }

        /////////
        // #4  //
        /////////
        c.println();
        int questionFive[][] = new int[5][5];
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                questionFive[i][j]=(i+1)*(j+1);
                c.print(questionFive[i][j]+" ");
            }
            c.println();
        }
    }
}