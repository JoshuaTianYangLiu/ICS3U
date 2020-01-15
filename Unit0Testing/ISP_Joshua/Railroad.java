public class Railroad implements Tile,OwnableTile{
    String name;
    int cost;
    int unMortgageValue;
    int ownerId;
    int rentCost[] = new int[5];
    boolean isMortgaged;

    /**
     * Name: Railroad
     * @param input
     * @throws Exception
     * Parses and assigns values for railroad object
     */
    Railroad(String input)throws Exception{
        isMortgaged=false;
        String portions[] = input.split("\\|");     // Added \\ as escape characters 
        ownerId=0;
        name=portions[0];
        cost=Integer.parseInt(portions[1]);
        unMortgageValue=Integer.parseInt(portions[2]);
        for(int i=1; i<5; i++){
            rentCost[i]=Integer.parseInt(portions[i+2]);
        }
    }

    /**
     * Name: reset
     * resets tile
     */
    public void reset(){
        isMortgaged=false;
        ownerId=0;
    }
    
    /** 
     * Name: buyProperty
     * @param j
     * @param amount
     * @param playerId
     * buys the property and give it to player 
     */
    public void buyProperty(ISP_Joshua j,int amount,int playerId){
        ownerId=playerId;
        j.removeMoney(amount,playerId);
    } 
    
    /** 
     * Name: payRent
     * @param j
     * pays rent for current player to person who owns it
     */
    void payRent(ISP_Joshua j){
        if(isMortgaged){
            Util.messageDialog(name+" is mortgaged\n"+
                                "Property owned by "+j.nameOfPlayer[ownerId], name);
        }else if(j.canCollectRentInJail(ownerId)){
            int numberOfRails=j.numberOfTilesOwned(3,ownerId);  //Number of rails owned by owner
            Util.messageDialog("You landed on "+name+"\n"+
                                "Pay "+j.nameOfPlayer[ownerId]+" $"+rentCost[numberOfRails]+".","Pay rent on "+name);
            j.transferMoney(rentCost[numberOfRails],ownerId);
        }else{
            Util.messageDialog("Owner in jail, no rent paid","Do not pay rent on "+name);
        }
    }
    
    /** 
     * Name: executeTile
     * @param j
     * follows instructions of what to do when tile is land on
     */
    public void executeTile(ISP_Joshua j){
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt(getFullInfo()+"\n"+
                                    "Please choose an option:\n"+
                                    "1: Buy railroad\n"+
                                    "2: Put up for auction",
                                    "Please enter a valid option 1,2",
                                    name,1,2);
            if(choice==1){
                if(j.getBalance()>=cost){   //Has enough money
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
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 3;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information about tile
     */
    public String getInfo(){
        return name;    //Maybe need other info such of money and such.
    }
    
    /** 
     * Name: mortgage
     * @param j
     * mortgages the railroad
     */
    public void mortgage(ISP_Joshua j){
        isMortgaged=true;
        j.addMoney(cost/2);
        //We'll need to see what to do here
    }
    
    /** 
     * Name: unMortgage
     * @param j
     * unmortgages the railroad
     */
    public void unMortgage(ISP_Joshua j){
        if(j.getBalance()>=unMortgageValue){
            isMortgaged=false;
            j.removeMoney(unMortgageValue);
        }else{
            Util.messageDialog("You do not have enough money,\n"+
                                "Cannot unmortgage this property.", name);
        }
        //We'll see what we need to do here
    }
    
    /** 
     * Name: isMortgaged
     * @return boolean
     * returns if the tile is mortgaged or not
     */
    public boolean isMortgaged(){
        return isMortgaged;
    }
    
    /** 
     * Name: transferOwnership
     * @param toPlayer
     * transfers ownship to another player
     */
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    
    /** 
     * Name: getOwnerId
     * @return int
     * returns id of owner of tile
     */
    public int getOwnerId(){
        return ownerId;
    }
    
    /** 
     * Name: getFullInfo
     * @return String
     * returns full info of tile
     */
    public String getFullInfo(){
        return 
        "Cost to buy $"+cost+'\n'+
        "--------------------\n"+
        name +'\n'+
        "Rent: $"+rentCost[1]+'\n'+
        "If 2 Stations are owned: $"+rentCost[2]+'\n'+
        "If 3 Stations are owned: $"+rentCost[3]+'\n'+
        "If 4 Stations are owned: $"+rentCost[4]+'\n';
    }
    
    /** 
     * Name: getMortgage
     * @return int
     * returns cost to mortgage
     */
    public int getMortgage(){
        return cost/2;
    }
    
    /** 
     * Name: getUnMortgage
     * @return int
     * returns cost to unmortgage
     */
    public int getUnMortgage(){
        return unMortgageValue;
    }
}