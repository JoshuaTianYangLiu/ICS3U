public class Railroad implements Tile,OwnableTile{
    String name;
    int cost;
    int unMortgageValue;
    int ownerId;
    int rentCost[] = new int[5];
    boolean isMortgaged;
    Railroad(String input)throws Exception{
        //TODO: Add exception handler
        isMortgaged=false;
        String portions[] = input.split("\\|");     // Added \\ as escape characters 
        if(portions.length!=7){
            throw new Exception("Not enough parameters in railroad line\n Input: "+input);
        }
        ownerId=0;
        name=portions[0];
        cost=Integer.parseInt(portions[1]);
        unMortgageValue=Integer.parseInt(portions[2]);
        for(int i=1; i<5; i++){
            rentCost[i]=Integer.parseInt(portions[i+2]);
        }
    }
    public void buyProperty(ISP_Joshua j,int amount,int playerId){
        j.removeMoney(amount);
        ownerId=playerId;
        // j.addToInventory(this);
    } 
    void payRent(ISP_Joshua j){
        //TODO: Get number of railroads owned
        if(isMortgaged){
            Util.messageDialog(name+" is mortgaged\n"+
                                "Property owned by "+j.nameOfPlayer[ownerId], name);
        }else{
            int numberOfRails=j.numberOfTilesOwned(3,ownerId);
            Util.messageDialog("You landed on "+name+"\n"+
                                "Pay "+j.nameOfPlayer[ownerId]+" $"+rentCost[numberOfRails]+".","Pay rent on "+name);
            j.transferMoney(rentCost[numberOfRails],ownerId);
        }
    }
    public void executeTile(ISP_Joshua j){
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt(getFullInfo()+"\n"+
                                    "Please choose an option:\n"+
                                    "1: Buy railroad\n"+
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
        return 3;
    }
    public String getInfo(){
        return name;    //Maybe need other info such of money and such.
    }
    public void mortgage(ISP_Joshua j){
        isMortgaged=true;
        j.addMoney(cost/2);
        //We'll need to see what to do here
    }
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
    public boolean isMortgaged(){
        return isMortgaged;
    }
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    public int getOwnerId(){
        return ownerId;
    }
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
}
/*
Cost&mortgage&Rent&two&three&four
Cost
Rent
With two railroad own
With three railroad own
With four railroad own
Mortgage value
*/