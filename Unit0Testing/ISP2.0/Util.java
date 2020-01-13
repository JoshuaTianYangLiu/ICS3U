/**
 * This class is for code which is used repetitively throughout all classes
 * This is to shorten the code and make it look much nicer without error traps along the actual code
 */


import javax.swing.*;

public class Util{
    
    /** 
     * Name: queryInt
     * @param inputMessage
     * @param errorMessage
     * @param title
     * @param lowerBound
     * @param upperBound
     * @return int
     * Returns an integer, the option it chooses while error trapping and allowing the input be in a range
     */
    static int queryInt(String inputMessage,String errorMessage,String title,int lowerBound,int upperBound){
        String inputStr;
        while(true){
            try{
                inputStr=JOptionPane.showInputDialog(null,inputMessage,title,JOptionPane.DEFAULT_OPTION);    //prompt for input
                int tempInt=Integer.parseInt(inputStr); //Parse for input
                if(lowerBound<=tempInt&&tempInt<=upperBound)    //In ranges
                    return tempInt;
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    
    /** 
     * Name: inputDialog
     * @param inputMessage
     * @param errorMessage
     * @param title
     * @return String
     * returns whatever the user inputs
     */
    static String inputDialog(String inputMessage,String errorMessage,String title){
        String inputStr;
        while(true){
            try{
                inputStr=JOptionPane.showInputDialog(null,inputMessage,title,JOptionPane.DEFAULT_OPTION);    //prompt for input
                if(inputStr!=null&&!inputStr.equals("")){   //If not empty and not null
                    return inputStr;
                }
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, errorMessage,title,JOptionPane.WARNING_MESSAGE);   //Error trap
            }
        }
    }
    
    /** 
     * Name: optionDialog
     * @param message
     * @param title
     * @param choices
     * @return int
     * returns choice given a list of options
     */
    static int optionDialog(String message, String title,String[] choices){
        int retValue=0;
        while(true){
            try{
                retValue=JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null,choices,choices[0]);
                if(retValue!=JOptionPane.CLOSED_OPTION)break;
                messageDialog("Please choose an option.", title);
            }catch(Exception e){}
        }
        return retValue;
    }
    
    /** 
     * Name: messageDialog
     * @param message
     * @param title
     * displays a message
     */
    static void messageDialog(String message,String title){
        JOptionPane.showMessageDialog(null, message,title,JOptionPane.PLAIN_MESSAGE);
    }
    
    /** 
     * Name: exception
     * @param input
     * @param e
     * Displays, and handles exception by stopping the program
     */
    static void exception(String input,Exception e){
        Util.messageDialog("An Exception has occured\n"+input+"---------------\n"+'\n'+e.toString(),"ERROR");
        System.exit(1);
    }
}