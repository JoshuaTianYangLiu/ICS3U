import java.awt.*;

public class Property implements Tile,OwnableTile{
    //Let the properties represent everything,
    //If it's not normal property, chance,mystery,jail,go
    //Return the id of the property
    //Main class will handle it
    static final int MORTGAGE=-1;
    String name;
    int propertyColour;     //start from 1.
    int cost;
    int tierLevel;  //0=rent >0 houses/ hotels
    int tierCost[] = new int[6];    //Cost of rent for each tier
    int houseCost;
    int hotelCost;
    int mortgageValue,unMortgageValue;
    int ownerId;
    boolean isMortgaged;

    /**
     * Name: Property
     * @param input
     * Parse input and setup usage for game
     */
    Property(String input){
        isMortgaged=false;
        ownerId=0;
        tierLevel=0;
        String portions[] = input.split("\\|");     // Added \\ as escape characters 
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

    /**
     * Name: reset
     * reset game
     */
    public void reset(){
        isMortgaged=false;
        ownerId=0;
        tierLevel=0;
    }
    
    /** 
     * Name: buyProperty
     * @param j
     * @param cost
     * @param playerId
     * Buys property for player
     */
    public void buyProperty(ISP_Joshua j,int cost,int playerId){    //The property can be bought with different costs due to auctions
        ownerId=playerId;
        j.removeMoney(cost,playerId);
    }
    
    /** 
     * Name: payRent
     * @param j
     * Deals with dialog and transfering money
     */
    void payRent(ISP_Joshua j){
        if(isMortgaged){
            Util.messageDialog(name+" is mortgaged\n"+
                                "Property owned by "+j.nameOfPlayer[ownerId], name);
        }else if(j.canCollectRentInJail(ownerId)){
            if(tierLevel==0){
                Util.messageDialog("You landed on "+name+"\n"+
                                    "Pay "+j.nameOfPlayer[ownerId]+" $"+tierCost[tierLevel]+".","Pay rent on "+name);
            }else if(tierLevel<5){
                Util.messageDialog("You landed on "+name+" with "+tierLevel+" houses\n"+
                                    "Pay "+j.nameOfPlayer[ownerId]+" $"+tierCost[tierLevel]+".","Pay rent on "+name);
            }else{
                Util.messageDialog("You landed on "+name+" with 1 hotel\n"+
                                    "Pay "+j.nameOfPlayer[ownerId]+" $"+tierCost[tierLevel]+".","Pay rent on "+name);
            }
            j.transferMoney(tierCost[tierLevel],ownerId);
        }else{
            Util.messageDialog("Owner in jail, no rent paid","Do not pay rent on "+name);
        }
    }
    
    /** 
     * Name: buyHouse
     * @param j
     * Buys house for player
     */
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
        if(tierLevel!=retVal){  //Tier level not minimum house level
            Util.messageDialog("Please follow the even build rule.\n"+
                                "You must first buy houses for properties with "+retVal+" houses.", "Buy House");
            return;
        }
        if(tierLevel==5){   //Is Hotel
            Util.messageDialog("You cannot build anymore on this property","Buy House");
            return;
        }
        if(tierLevel<4){    //Buying house
            if(j.getBalance()<houseCost){   //Not enough money
                Util.messageDialog("Not enough money to buy house!", "Buy House");
                return;
            }
            Util.messageDialog("House bought on "+name+".","Buy House");
            j.removeMoney(houseCost);
            tierLevel++;
        }else{  //Buying hotel
            if(j.getBalance()<hotelCost){   //Not enough money
                Util.messageDialog("Not enough money to buy hotel!", "Buy House");
                return;
            }
            Util.messageDialog("Hotel bought on "+name+".","Buy House");
            j.removeMoney(hotelCost);
            tierLevel++;
        }
    }
    
    /** 
     * Name: sellHouse
     * @param j
     * Sells house
     */
    void sellHouse(ISP_Joshua j){   //Move down a tier, this should also work with mortgaging
        int retVal=j.getMaxOfColourSet(propertyColour);
        if(retVal==-1||retVal==-2||tierLevel==0){   //All of these cases mean they do not own any houses
            Util.messageDialog("You cannot sell houses if you do not have any houses", "Sell House");
            return;
        }
        if(tierLevel!=retVal){  //Even sell rule
            Util.messageDialog("Please follow the even sell rule.\n"+
                                "You must first sell houses for properties with "+retVal+" houses.", "Sell House");
            return;
        }
        if(tierLevel==5){   //Is hotel
            Util.messageDialog("Hotel Sold on "+name+".","Sell House");
            j.addMoney(hotelCost/2);
            tierLevel--;
        }else{  //Is house
            Util.messageDialog("House Sold on "+name+".","Sell House");
            j.addMoney(houseCost/2);
            tierLevel--;
        }

    }
    
    /** 
     * Name: getNumOfHouses
     * @return int
     * Returns number of houses
     */
    int getNumOfHouses(){
        if(tierLevel==5)return 0;
        return tierLevel;
    }
    
    /** 
     * Name: getNumOfHotels
     * @return int
     * returns number of hotels
     */
    int getNumOfHotels(){
        return tierLevel==5?1:0;
    }
    
    /** 
     * Name: executeTile
     * @param j
     * Will execute when land on
     */
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
            if(choice==1){  //Buy
                if(j.getBalance()>=cost){
                    buyProperty(j,cost,j.curPlayer);
                    return;
                }else{
                    Util.messageDialog("You do not have enough money,\n"+
                                        "Putting property up for auction.", name);
                }
            }
            j.runAuction(this); //Auction
        }else{  //Owned, pay rent
            if(j.curPlayer!=ownerId){
                payRent(j);
            }
        }
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 2;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information
     */
    public String getInfo(){
        return name;
    }
    
    /** 
     * Name: mortgage
     * @param j
     * mortgage property
     */
    public void mortgage(ISP_Joshua j){
        int maxLevel = j.getMaxOfColourSet(propertyColour);
        if(maxLevel==0){   //No houses
            isMortgaged=true;
            j.addMoney(cost/2); //Mortgage amount
        }else{
            Util.messageDialog("You have houses on colour set, Sell those first", name);
        }
    }
    
    /** 
     * Name: unMortgage
     * @param j
     * unmortgage property
     */
    public void unMortgage(ISP_Joshua j){
        if(j.getBalance()>=unMortgageValue){    //Has enough money
            isMortgaged=false;
            j.removeMoney(unMortgageValue);
        }else{
            Util.messageDialog("You do not have enough money,\n"+
                                "Cannot unmortgage this property.", name);
        }
    }
    
    /** 
     * Name: isMortgaged
     * @return boolean
     * returns if property is mortgaged
     */
    public boolean isMortgaged(){
        return isMortgaged;
    }
    
    /** 
     * Name: transferOwnership
     * @param toPlayer
     * Transfer the ownerid to new player
     */
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    
    /** 
     * Name: getOwnerId
     * @return int
     * returns id of person who owns the land
     */
    public int getOwnerId(){
        return ownerId;
    }
    
    /** 
     * Name: getColourName
     * @return String
     * Returns name of the colour of the property
     */
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
    
    /** 
     * Name: getColour
     * @return Color
     * returns colour object of the property
     */
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
    
    /** 
     * Name: getFullInfo
     * @return String
     * returns full info of property
     */
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
    
    /** 
     * Name: getMortgage
     * @return int
     * returns value to mortgage
     */
    public int getMortgage(){
        return mortgageValue;
    }
    
    /** 
     * Name: getUnMortgage
     * @return int
     * returns value to unmortgage
     */
    public int getUnMortgage(){
        return unMortgageValue;
    }
}