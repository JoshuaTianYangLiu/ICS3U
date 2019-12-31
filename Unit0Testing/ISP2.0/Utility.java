public class Utility implements Tile,OwnableTile{
    String name;
    int cost;
    int unMortgage;
    int multiplier1,multiplier2;
    int ownerId;
    Utility(String input){
        String portion[] = input.split("\\|");      // Added \\ as escape characters 
        ownerId=0;
        name = portion[0];
        cost=Integer.parseInt(portion[1]);
        unMortgage=Integer.parseInt(portion[2]);
        multiplier1=Integer.parseInt(portion[3]);
        multiplier2=Integer.parseInt(portion[4]);
    }
    public void buyProperty(ISP_Joshua j,int amount,int playerId){
        j.removeMoney(amount);
        ownerId=playerId;
        j.addToInventory(this);
    }
    void payRent(ISP_Joshua j){
        int utilitiesOwned=j.numberOfTilesOwned(10);
        int totalCost=j.diceOne+j.diceTwo;
        //TODO: Add some way to display how much they have to pay
        if(utilitiesOwned==1){
            j.transferMoney(multiplier1*totalCost,ownerId);
        }else{
            j.transferMoney(multiplier2*totalCost,ownerId);
        }
    }
    public void executeTile(ISP_Joshua j){
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt("Please choose an option:\n"+
                                    "1: Buy utility\n"+
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
        return 10;
    }
    public String getInfo(){
        return name;
    }
    public void mortgage(){

    }
    public void unMortgage(){
        
    }
    public void tranferOwnership(int toPlayer){
        ownerId=toPlayer;
    }
    public int getOwnerId(){
        return ownerId;
    }
    public String getFullInfo(){
        return name+'\n'+'\n'+
            "If one Utility is owned, rent is "+multiplier1+" times amount shown on dice."+'\n'+
            "If both Utilities are owned, rent is "+multiplier2+" times amount shown on dice";

    }
}
/*
Cost&Mortgage&values
*/