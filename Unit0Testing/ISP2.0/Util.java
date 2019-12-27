import hsa.Console;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Util{
    static int queryInt(String inputMessage,String errorMessage,String title,int lowerBound,int upperBound){
        String inputStr;
        while(true){
            try{
                inputStr=JOptionPane.showInputDialog(null,inputMessage,title,JOptionPane.DEFAULT_OPTION);    //prompt for input
                int tempInt=Integer.parseInt(inputStr);
                if(lowerBound<=tempInt&&tempInt<=upperBound)
                    return tempInt;
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    static void messageDialog(String message,String title){
        JOptionPane.showMessageDialog(null, message,title,JOptionPane.WARNING_MESSAGE);
    }
}