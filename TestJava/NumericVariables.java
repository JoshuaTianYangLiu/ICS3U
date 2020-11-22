// The "NumericVariables" class.
import java.awt.*;
import hsa.Console;

public class NumericVariables
{
    static Console c;           // The output console
    NumericVariables(){
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
        grossSalary=hourlyRate*hourPerWeek*52/12;
        c.print(" "+employee+"\t$");
        c.print(hourlyRate,0,2);
        c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
        c.println(grossSalary,0,2);
        employee="Coyote";
        hourlyRate=4.25;
        hourPerWeek=40;
        grossSalary=hourlyRate*hourPerWeek*52/12;
        c.print(" "+employee+"\t$");
        c.print(hourlyRate,0,2);
        c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
        c.println(grossSalary,0,2);
        employee="Road Runner";
        hourlyRate=92.00;
        hourPerWeek=15;
        grossSalary=hourlyRate*hourPerWeek*52/12;
        c.print(" "+employee+"\t$");
        c.print(hourlyRate,0,2);
        c.print("\t\t\t"+hourPerWeek+"\t\t\t$");
        c.println(grossSalary,0,2);
    }
    public static void main (String[] args)
    {
        NumericVariables nV;
        nV=new NumericVariables();
        nV.drawTitle();
        nV.displaySalary();
        
        // Place your program here.  'c' is the output console
    } // main method
} // NumericVariables class
