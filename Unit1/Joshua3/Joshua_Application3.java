/*
Joshua Liu
Afsari
Sept 13,2019
Prints the gross salary of ACME employees
*/
import java.awt.*;
import hsa.Console;

public class Joshua_Application3
{
    static Console c;           // The output console
    Joshua_Application3(){
	c=new Console("Acme Chemical Company Gross Monthly Salary Calculator");
    }
    void drawTitle(){
	c.println("\t\t\t\tAcme Chemical Company\n");
    }
    void displaySalary(){
	c.println(" Employee\tHourly Rate\tTotal Hours Per Week\t Gross Monthly Salary\n");
	
	//declaration statements
	String employee;
	double hourlyRate;
	int hourPerWeek;
	double grossSalary;
	
	//assignment statements
	employee="Superman";
	hourlyRate=59.50;
	hourPerWeek=2;
	
	//output
	grossSalary=hourlyRate*hourPerWeek*52/12;
	c.print(" "+employee+"\t$");
	c.print(hourlyRate,0,2);
	c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
	c.println(grossSalary,0,2);
	
	//assignment statements
	employee="Coyote";
	hourlyRate=4.25;
	hourPerWeek=40;
	grossSalary=hourlyRate*hourPerWeek*52/12;
	
	//output
	c.print(" "+employee+"\t\t$");
	c.print(hourlyRate,0,2);
	c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
	c.println(grossSalary,0,2);
	
	//assignment statements
	employee="Road Runner";
	hourlyRate=92.00;
	hourPerWeek=15;
	grossSalary=hourlyRate*hourPerWeek*52/12;
	
	//output
	c.print(" "+employee+"\t$");
	c.print(hourlyRate,0,2);
	c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
	c.println(grossSalary,0,2);
    }
    public static void main (String[] args)
    {
	Joshua_Application3 nV;
	nV=new Joshua_Application3();
	nV.drawTitle();
	nV.displaySalary();
	
	// Place your program here.  'c' is the output console
    } // main method
} // NumericVariables class
