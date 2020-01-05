import java.awt.*;

public class Property implements Tile,OwnableTile{
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
    int cost;
    int tierLevel;  //0=rent -1= morgage, >0 houses/ hotels
    int tierCost[] = new int[6];
    int houseCost;
    int hotelCost;
    int mortgageValue,unMortgageValue;
    int ownerId;
    boolean isMortgaged;
    Property(String input) throws Exception{
        isMortgaged=false;
        ownerId=0;
        tierLevel=0;
        String portions[] = input.split("\\|");     // Added \\ as escape characters 
        if(portions.length!=12){
            throw new Exception("Not enough parameters in property line\n Input: "+input);
        }
        name=portions[0];
        propertyColour=Integer.parseInt(portions[1]);
        houseCost=Integer.parseInt(portions[2]);
        hotelCost=Integer.parseInt(portions[3]);
        cost=Integer.parseInt(portions[4]);
        unMortgageValue = Integer.parseInt(portions[5]);
        for(int i=0; i<6; i++){
            tierCost[i]=Integer.parseInt(portions[6+i]);
        }
        mortgageValue = cost/2;
        //Monopoly rules says that mortgage value is half the cost of the property cost
        //selling anything is half the cost right?????
    }

    public void buyProperty(ISP_Joshua j,int cost,int playerId){    //The property can be bought with different costs due to auctions
        j.removeMoney(cost);
        ownerId=playerId;
    }
    void payRent(ISP_Joshua j){
        if(isMortgaged){
            Util.messageDialog(name+" is mortgaged\n"+
                                "Property owned by "+j.nameOfPlayer[ownerId], name);
        }else{
            Util.messageDialog("You landed on "+name+"\n"+
                                "Pay "+j.nameOfPlayer[ownerId]+" $"+tierCost[tierLevel]+".","Pay rent on "+name);
            j.transferMoney(tierCost[tierLevel],ownerId);
        }
    }
    void buyHouse(ISP_Joshua j){    //In my words, move up a tier level
        int retVal=j.getMinOfColourSet(propertyColour);
        if(retVal==-1){ //Does not own full colour set
            Util.messageDialog("You do not own all "+getColourName()+"s!", "Buy House");
            return;
        }
        if(retVal==-2){   //One of the properties is mortgaged
            Util.messageDialog("One of your properties is mortgaged!","Buy House");
            return;
        }
        if(tierLevel!=retVal){
            Util.messageDialog("Please follow the even build rule.\n"+
                                "You must first buy houses for properties with "+retVal+" houses.", "Buy House");
            return;
        }
        if(tierLevel==5){
            Util.messageDialog("You cannot build anymore on this property","Buy House");
            return;
        }
        if(tierLevel<4){    //Buying house
            if(j.getBalance()<houseCost){
                Util.messageDialog("Not enough money to buy house!", "Buy House");
                return;
            }
            Util.messageDialog("House bought on "+name+".","Buy House");    //Maybe need additional info
            j.removeMoney(houseCost);
            tierLevel++;
        }else{  //Buying hotel
            if(j.getBalance()<hotelCost){
                Util.messageDialog("Not enough money to buy hotel!", "Buy House");
                return;
            }
            Util.messageDialog("Hotel bought on "+name+".","Buy House");    //Maybe need additional info
            j.removeMoney(hotelCost);
            tierLevel++;
        }
    }
    void sellHouse(ISP_Joshua j){   //Move down a tier, this should also work with mortgaging
        int retVal=j.getMaxOfColourSet(propertyColour);
        if(retVal==-1||retVal==-2||tierLevel==0){
            Util.messageDialog("You cannot sell houses if you do not have any houses", "Sell House");
            return;
        }
        if(tierLevel!=retVal){
            Util.messageDialog("Please follow the even sell rule.\n"+
                                "You must first sell houses for properties with "+retVal+" houses.", "Sell House");
            return;
        }
        if(tierLevel==5){
            Util.messageDialog("Hotel Sold on "+name+".","Sell House");
            j.addMoney(hotelCost/2);
            tierLevel--;
        }else{
            Util.messageDialog("House Sold on "+name+".","Sell House");
            j.addMoney(houseCost/2);
            tierLevel--;
        }

    }
    int getNumOfHouses(){
        if(tierLevel==5)return 0;
        return tierLevel;
    }
    int getNumOfHotels(){
        return tierLevel==5?1:0;
    }
    public void executeTile(ISP_Joshua j){
        //For the property tile, it is difficult to execute the property and known what function it should do.
        //This method if for just what should happen when a person lands on the tile
        //Meaning this will only have the option to buy and pay rent
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt(getFullInfo()+'\n'+
                                    "-------------------------\n"+
                                    "Please choose an option:\n"+
                                    "1: Buy property\n"+
                                    "2: Put up for auction",
                                    "Please enter a valid option 1,2",
                                    name,1,2);
                                    //TODO: add an option to disable auctions
            if(choice==1){
                if(j.getBalance()>=cost){
                    buyProperty(j,cost,j.curPlayer);
                    return;
                }else{
                    Util.messageDialog("You do not have enough money,\n"+
                                        "Putting property up for auction.", name);
                }
            }
            j.runAuction(this);
        }else{
            if(j.curPlayer!=ownerId){
                payRent(j);
            }
        }
    }
    public int getTileType(){
        return 2;
    }
    public String getInfo(){
        return name;    //Maybe need other info such of money and such.
    }
    public void mortgage(ISP_Joshua j){
        if(tierLevel==0){
            isMortgaged=true;
            j.addMoney(cost/2);
        }else{
            Util.messageDialog("You have houses on this property, Sell those first", name);
        }
    }
    public void unMortgage(ISP_Joshua j){
        if(j.getBalance()>=unMortgageValue){
            isMortgaged=false;
            j.removeMoney(unMortgageValue);
        }else{
            Util.messageDialog("You do not have enough money,\n"+
                                "Cannot unmortgage this property.", name);
        }
    }
    public boolean isMortgaged(){
        return isMortgaged;
    }
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    public int getOwnerId(){
        return ownerId;
    }
    public String getColourName(){
        switch(propertyColour){
            case 1:
                return "Brown";
            case 2:
                return "Light Blue";
            case 3:
                return "Purple";
            case 4:
                return "Orange";
            case 5:
                return "Red";
            case 6:
                return "Yellow";
            case 7:
                return "Green";
            case 8:
                return "Blue";
        }
        return "";
    }
    public Color getColour(){
        switch(propertyColour){
            case 1:
                return new Color(151,94,57);
            case 2:
                return new Color(49,168,222);
            case 3:
                return new Color(255,219,3);
            case 4:
                return new Color(233,21,33);
            case 5:
                return new Color(222,5,131);
            case 6:
                return new Color(223,147,45);
            case 7:
                return new Color(3,148,60);
            case 8:
                return new Color(36,52,135);
        }
        return null;
    }
    public String getFullInfo(){
        return 
            "Cost to buy $"+cost+'\n'+
            "--------------------\n"+
            name+'\n'+
            "Colour set: "+getColourName()+'\n'+
            "Rent: $"+tierCost[0]+'\n'+
            "Rent with 1 house: $"+tierCost[1]+'\n'+
            "Rent with 2 house: $"+tierCost[2]+'\n'+
            "Rent with 3 house: $"+tierCost[3]+'\n'+
            "Rent with 4 house: $"+tierCost[4]+'\n'+
            "Rent with hotel: $"+tierCost[5]+'\n'+
            "Houses cost: $"+houseCost+'\n'+
            "Hotel cost: $"+hotelCost;
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
name|group|houseCost|hotelCost|cost|unmortgagevalue|tierCost
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