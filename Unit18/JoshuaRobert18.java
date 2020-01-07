/*
Joshua And Robert
January 1,2020
Mr.Afsari
This program will allow the user to open, edit, create, and save files
*/
//Header is JR18
//Extention is .ics
//I am very brain dead doing this. Get rid of isEdited there no need for it
import hsa.Console;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class JoshuaRobert18{
    Console c;
    int contents;
    String inputStr,fileName="Untitled.ics";
    String menuChoice;
    boolean isEdited,noContent=true;
    JoshuaRobert18(){
	c=new Console("Joshua And Robert - Java 18 File IO");
    }
    public void title(){
	c.clear();
	c.println("------------------------------");
	c.println("| File IO Joshua and Robert. |");
	c.println("------------------------------");
    }
    public void mainMenu(){
	title();
	c.println("1: New");
	c.println("2: Open");
	c.println("3: Display");
	c.println("4: Modify");
	c.println("5: Save");
	c.println("6: Save As");
	c.println("Anything else: Quit");
    }
    public void askData(){
	menuChoice=JOptionPane.showInputDialog(null,"Please choose an option","Main Menu",JOptionPane.DEFAULT_OPTION);
	c.clear();
    }
    public void goodbye(){
	title();
	c.println("Thank you for using our program made by Joshua and Robert.");
	c.println("Press any key to quit.");
	c.getChar();
	c.close();
    }
    public void newFile(){
	title();
	saveExisting();
	try{
	    c.println("Please enter an integer for the file contents");
        contents=Integer.parseInt(c.readLine());
        noContent=false;
	    isEdited=true;
	}catch(Exception e){
	    JOptionPane.showMessageDialog(null, "Please enter an integer","Main Menu",JOptionPane.WARNING_MESSAGE);
	    newFile();
	}
	pressAnyKey();
    }
    public void saveExisting(){
	if(isEdited){
	    int choice=JOptionPane.showConfirmDialog(null,"You have an unsaved file. Do you want to save?");
	    if(choice==JOptionPane.YES_OPTION){
		save();
	    }
	}
    }
    public void pressAnyKey(){
	JOptionPane.showMessageDialog(null,"Press enter to continue","JoshuaRobert18",JOptionPane.DEFAULT_OPTION);
    }
    public void openFile(){
	title();
	saveExisting();
	c.println("Please enter file name");
    fileName=c.readLine();
    if(fileName.length()<4){
        c.println("Too short of a file name.");
        pressAnyKey();
        openFile();
        return;
    }
	if(!fileName.substring(fileName.length()-4).equals(".ics")){
	    c.println("Invalid extention.");
	    pressAnyKey();
	    openFile();
	    return;
	}
	BufferedReader fileContents=null;
	try{
	    fileContents=new BufferedReader(new FileReader(fileName));
	}catch(Exception e){
	    c.println("File does not exist.");
	    pressAnyKey();
	    openFile();
	    return;
	}
	try{
	    String header = fileContents.readLine();
	    if(header==null){
		c.println("Missing Header.");
		pressAnyKey();
		openFile();
		return;
	    }
	    if(!header.equals("JR18")){
		c.println("Invalid Header.");
		pressAnyKey();
		openFile();
		return;
	    }
	}catch(Exception e){}
	try{
	    String contentStr=fileContents.readLine();
	    if(contentStr==null){
		c.println("Missing Contents.");
		pressAnyKey();
		openFile();
		return;
	    }
        contents=Integer.parseInt(contentStr);
        noContent=false;
	}catch(Exception e){
	    c.println("Invalid content.");
	    pressAnyKey();
	    openFile();
	    return;
	}
	c.println("Contents of File:");
	c.println(contents);
	isEdited=false;
	try{
	fileContents.close();
	}catch(Exception e){}
	pressAnyKey();
    }
    public void displayFile(){
	title();
	if(noContent){
	    c.println("There is no value at this time");
	}else{
	    c.print("Current contents of the file: ");
	    c.println(contents);
	}
	pressAnyKey();
    }
    public void modifyFile(){
	title();
	if(noContent){
	    c.println("There is no value at this time");
	}else{
	    c.print("Current contents of the file: ");
	    c.println(contents);
	}
	try{
	    c.println("Please enter an integer");
	    contents=Integer.parseInt(c.readLine());
	}catch(Exception e){
	    c.println("Please enter an integer!");
	    pressAnyKey();
	    modifyFile();
	    return;
	}
	c.print("New contents of the file: ");
	c.println(contents);
	pressAnyKey();
	isEdited=true;
    }
    public void save(){
	title();
	if(isEdited){
	    PrintWriter output;
	    try{
		output=new PrintWriter(new FileWriter(fileName));
		output.println("JR18");
		output.println(contents);
		output.close();
		c.println("Saved to "+fileName);
		output.close();
	    }catch(Exception e){}
	    isEdited=false;
	}else{
	    c.println("There has been no changes to the file. Save not needed");
	}
	pressAnyKey();
    }
    public void saveAs(){
	title();
	if(noContent){
	    c.println("There is no value at this time. Cannot save empty file");
	    pressAnyKey();
	    return;
	}
	c.println("Please enter file name.");
	fileName=c.readLine();
	if(!fileName.substring(fileName.length()-4).equals(".ics")){
	    c.println("Invalid extention.");
	    pressAnyKey();
	    saveAs();
	    return;
    }
    for(int i=0; i<fileName.length(); i++){
        if(fileName.charAt(i)=='<'||fileName.charAt(i)=='<'||
        fileName.charAt(i)==':'||fileName.charAt(i)=='/'||
        fileName.charAt(i)=='\\'||fileName.charAt(i)=='|'||
        fileName.charAt(i)=='?'||fileName.charAt(i)=='*'){
            c.println("File name contains invalid characters.");
            pressAnyKey();
            saveAs();
            return;
        }
    }
	PrintWriter output;
	try{
	    output=new PrintWriter(new FileWriter(fileName));
	    output.println("JR18");
	    output.println(contents);
	    output.close();
	}catch(Exception e){}
	isEdited=false;
	pressAnyKey();
    }
    public void quit(){
	saveExisting();
    }
    public static void main(String[] args){
	JoshuaRobert18 j = new JoshuaRobert18();
	while(true){
	    j.mainMenu();
	    j.askData();
	    if(j.menuChoice.equals("1"))j.newFile();
	    else if(j.menuChoice.equals("2"))j.openFile();
	    else if(j.menuChoice.equals("3"))j.displayFile();
	    else if(j.menuChoice.equals("4"))j.modifyFile();
	    else if(j.menuChoice.equals("5"))j.save();
	    else if(j.menuChoice.equals("6"))j.saveAs();
	    else break;
	}
	j.quit();
	j.goodbye();
    }
}
