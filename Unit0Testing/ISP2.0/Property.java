import javax.swing.JOptionPane;

public class Property implements Tile{
    //This also needs to work with railroads and utilities
    //How am i gonna make this work with chance cards?
    //
    //Let the properties represent everything,
    //If it's not normal property, chance,mystery,jail,go
    //Return the id of the property
    //Main class will handle it
    static final int MORTGAGE=-1;
    String name;
    int propertyColour;     //start from 2. 1 for railroad 0 for utility
    int propertyCost;
    int tierLevel;  //0=rent -1= morgage, >0 houses/ hotels
    int tierCost[] = new int[6];
    int houseCost;
    int hotelCost;
    int mortgageValue,unMortgageValue;
    int playerId;
    Property(String input) throws Exception{
        playerId=0;
        tierLevel=0;
        String portions[] = input.split("\\|");     // Added \\ as escape characters 
        if(portions.length!=12){
            throw new Exception("Not enough parameters in property line\n Input: "+input);
        }
        name=portions[0];
        propertyColour=Integer.parseInt(portions[1]);
        houseCost=Integer.parseInt(portions[2]);
        hotelCost=Integer.parseInt(portions[3]);
        propertyCost=Integer.parseInt(portions[4]);
        unMortgageValue = Integer.parseInt(portions[5]);
        for(int i=0; i<6; i++){
            tierCost[i]=Integer.parseInt(portions[6+i]);
        }
        mortgageValue = propertyCost/2;
        //Monopoly rules says that mortgage value is half the cost of the property cost
        //selling anything is half the cost right?????
    }

    void buyProperty(ISP_Joshua j){
        
    }
    void payRent(ISP_Joshua j){
        
    }
    void buyHouse(ISP_Joshua j){    //In my words, move up a tier level
        
    }
    void sellHouse(ISP_Joshua j){   //Move down a tier, this should also work with mortgaging
        
    }
    public void executeTile(ISP_Joshua j){
        if(j.curPlayer!=playerId)payRent(j);
        //Ask for option
        int option=1;
        if(option==1){  //Buy property
            buyProperty(j);
        }else if(option==2){    //Buy houses
            buyHouse(j);
        }else if(option==3||option==4){    //Sell/Mortgage
            sellHouse(j);
        }
    }
    public int getTileType(){
        return 2;
    }
    public String getInfo(){
        return name;    //Maybe need other info such of money and such.
    }
}
/*
To future me, This should be done.
but idk everything is implemented correctly, or if there's any logic errors.
If there's anything that cant be fixed easily. Tear it down and rebuild it.
Formatting for the property cards are down below
You might need to add exceptions in order to account for invalid moves and operations
This interacts back and forth with the main class
God, I really hope i got this right on the first try
Next off, The chance card!
As to my contest checker, taking a break from that, need a better way to parse
*/
/*
name|group|houseCost|hotelCost|propertyCost|unmortgagevalue|tierCost
Name of Property
Property group
Rent
Mortgage Value
With one house:
With two house:
With three house:
With four house:
With Hotel
House cost
Hotel cost
*/