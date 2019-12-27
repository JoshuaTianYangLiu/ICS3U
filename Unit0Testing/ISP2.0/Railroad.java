public class Railroad implements Tile{
    static final int NOTOWNED=0;
    String name;
    int cost;
    int unMortgageValue;
    int ownerId;
    int rentCost[] = new int[5];
    Railroad(String input)throws Exception{
        //TODO: Add exception handler
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
    void buyProperty(ISP_Joshua j,int amount){
        j.removeMoney(amount);
        ownerId=j.curPlayer;
        j.addToInventory(this);
    } 
    void payRent(ISP_Joshua j){
        //TODO: Get number of railroads owned
        int numberOfRails=1;
        j.transferMoney(rentCost[numberOfRails],ownerId);
    }
    public void executeTile(ISP_Joshua j){
        if(ownerId==NOTOWNED){  //If not owned
            int choice = Util.queryInt("Please choose an option:\n"+
                                    "1: Buy railroad\n"+
                                    "2: Put up for auction",
                                    "Please enter a valid option 1,2",
                                    name,1,2);
                                    //TODO: add an option to disable auctions
            if(choice==1){
                if(j.getBalance()>=cost){
                    buyProperty(j,cost);
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