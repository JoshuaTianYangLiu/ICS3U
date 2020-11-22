public class Utility implements Tile,OwnableTile{
    String name;
    int cost;
    int unMortgage;
    int multiplier1,multiplier2;
    int ownerId;
    boolean isMortgaged;

    /**
     * Name:Utility
     * @param input
     * Parse input
     */
    Utility(String input){
        isMortgaged=false;
        String portion[] = input.split("\\|");      // Added \\ as escape characters 
        ownerId=0;
        name = portion[0];
        cost=Integer.parseInt(portion[1]);
        unMortgage=Integer.parseInt(portion[2]);
        multiplier1=Integer.parseInt(portion[3]);
        multiplier2=Integer.parseInt(portion[4]);
    }

    /**
     * Name: reset
     * reset object for new game
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
     * Buys property for player
     */
    public void buyProperty(ISP_Joshua j,int amount,int playerId){
        ownerId=playerId;
        j.removeMoney(amount,playerId);
    }
    
    /** 
     * Name: payRent
     * @param j
     * pays rent for for current player to player who owns it
     */
    void payRent(ISP_Joshua j){
        if(isMortgaged){
            Util.messageDialog(name+" is mortgaged\n"+
                                "Property owned by "+j.nameOfPlayer[ownerId], name);
        }else if(j.canCollectRentInJail(ownerId)){  //If the owner can even collect rent
            int utilitiesOwned=j.numberOfTilesOwned(10,ownerId);    //Number of utilities owned
            int totalCost=j.diceOne+j.diceTwo;
            Util.messageDialog("You landed on "+name+"\n"+
                                "Pay "+j.nameOfPlayer[ownerId]+" $("+j.diceOne+" + "+j.diceTwo+")*"+
                                (utilitiesOwned==1?multiplier1:multiplier2)+"= $"+
                                ((utilitiesOwned==1?multiplier1:multiplier2)*totalCost)+".","Pay rent on "+name);
            if(utilitiesOwned==1){
                j.transferMoney(multiplier1*totalCost,ownerId);
            }else{
                j.transferMoney(multiplier2*totalCost,ownerId);
            }
        }else{
            Util.messageDialog("Owner in jail, no rent paid","Do not pay rent on "+name);
        }
    }
    
    /** 
     * Name: executeTile
     * @param j
     * Execute tile which player is currently on
     */
    public void executeTile(ISP_Joshua j){
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt(getFullInfo()+'\n'+
                                    "Please choose an option:\n"+
                                    "1: Buy utility\n"+
                                    "2: Put up for auction",
                                    "Please enter a valid option 1,2",
                                    name,1,2);
            if(choice==1){  //Buy Utility
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
            if(j.curPlayer!=ownerId){   //Pay rent
                payRent(j);
            }
        }
    }
    
    /** 
     * Name getTileType
     * @return int
     * return tile id
     */
    public int getTileType(){
        return 10;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic info of tile
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
        isMortgaged=true;
        j.addMoney(cost/2);
    }
    
    /** 
     * Name: unMortgage
     * @param j
     * unmortgage property
     */
    public void unMortgage(ISP_Joshua j){
        if(j.getBalance()>=unMortgage){ //Has enough money
            isMortgaged=false;
            j.removeMoney(unMortgage);
        }else{
            Util.messageDialog("You do not have enough money,\n"+
                                "Cannot unmortgage this property.", name);
        }
    }
    
    /** 
     * Name: isMortgaged
     * @return boolean
     * returns if the property is mortgaged or not
     */
    public boolean isMortgaged(){
        return isMortgaged;
    }
    
    /** 
     * Name: transferOwnership
     * @param toPlayer
     * transfers ownership to another player
     */
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    
    /** 
     * Name: getOwnerId
     * @return int
     * returns id of owner
     */
    public int getOwnerId(){
        return ownerId;
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
            name +'\n'+
            "If one Utility is owned, rent is "+multiplier1+" times amount shown on dice."+'\n'+
            "If both Utilities are owned, rent is "+multiplier2+" times amount shown on dice";
    }
    
    /** 
     * Name: getMortgage
     * @return int
     * returns cost of mortgage
     */
    public int getMortgage(){
        return cost/2;
    }
    
    /** 
     * Name: getUnMortgage
     * @return int
     * returns cost of unmortgage
     */
    public int getUnMortgage(){
        return unMortgage;
    }
}