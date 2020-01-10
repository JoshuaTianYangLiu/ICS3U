public class ChanceCard{    //TODO: Clean up code from removing the Tile interface
    String message;
    String operation;
    int value1,value2;
    ChanceCard(String input)throws Exception{
        //We need to parse the string and assign the corresponding info
        //Will be seperated by &
        String portions[] = input.split("\\|");
        message=portions[0];
        message=message.replace('-', '\n');
        operation=portions[1];
        if(operation.equals("MOVETO")){
            value1=Integer.parseInt(portions[2]);
        }else if(operation.equals("ADD")){
            value1=Integer.parseInt(portions[2]);
        }else if(operation.equals("REMOVE")){
            value1=Integer.parseInt(portions[2]);
        }else if(operation.equals("MAKEHIMLOSE")){
            value1=Integer.parseInt(portions[2]);
            value2=Integer.parseInt(portions[3]);
        }else if(operation.equals("GIVEPLAYERS")){
            value1=Integer.parseInt(portions[2]);
        }
    }
    public String getInfo() {
        return message;
    }
    public int getTileType(){
        return 4;
    }
    void executeTile(ISP_Joshua j){
        if(operation.equals("GETOUT")){
            j.addGetOutOfJail();
        }else if(operation.equals("NEARRAIL")){
            j.moveUntil(3);
            j.executeCurrentTile();
        }else if(operation.equals("NEARUTIL")){
            j.moveUntil(10);
            j.executeCurrentTile();
        }else if(operation.equals("MOVETO")){
            j.moveTo(value1);
            //Execute tile
            j.executeCurrentTile();
        }else if(operation.equals("ADD")){
            j.addMoney(value1);
        }else if(operation.equals("REMOVE")){
            //Remove money, figure out what to do
            j.payTax(value1);
        }else if(operation.equals("MAKEHIMLOSE")){
            //Pay __ for each house and __ for each hotel
            int numOfHouses=j.getNumOfHousesOwned();
            int numOfHotels=j.getNumOfHotelsOwned();
            int totalPayment = value1*numOfHouses+value2*numOfHotels;
            Util.messageDialog("Number of houses owned: "+numOfHouses+"*"+value1+'\n'+
                                "Number of hotels owned: "+numOfHotels+"*"+value2+'\n'+
                                "---------------------------\n"+
                                "Pay $"+totalPayment, "Chance");
            j.payTax(totalPayment);
        }else if(operation.equals("GIVEPLAYERS")){
            for(int i=1; i<j.numOfPlayers; i++){
                if(i!=j.curPlayer&&j.canCollectRentInJail(i)){
                    j.transferMoney(value1, i);
                }
            }
        }else if(operation.equals("MOVEBACK")){
            j.moveBack(3);
            j.executeCurrentTile();
        }else if(operation.equals("GOTOJAIL")){
            j.sendToJail();
        }
    }
}
/*
Chance Card List:

Advance to Go (Collect $200)
Advance to Illinois Ave—If you pass Go, collect $200
Advance to St. Charles Place – If you pass Go, collect $200
Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown.
Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.
Bank pays you dividend of $50
Get Out of Jail Free
Go Back 3 Spaces    TODO: Figure out this
Go to Jail–Go directly to Jail–Do not pass Go, do not collect $200
Make general repairs on all your property–For each house pay $25–For each hotel $100  TODO: Given to bank
Pay poor tax of $15 TODO: this is given to bank
Take a trip to Reading Railroad–If you pass Go, collect $200
Take a walk on the Boardwalk–Advance token to Boardwalk     TODO: ?? What is this?
You have been elected Chairman of the Board–Pay each player $50
Your building and loan matures—Collect $150
You have won a crossword competition—Collect $100


*/