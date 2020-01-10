public class CommunityChestCard{    //TODO: Clean up code from removing the Tile interface
    String message;
    String operation;
    int value1,value2;
    CommunityChestCard(String input){
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
        }else if(operation.equals("GETPLAYERS")){
            value1=Integer.parseInt(portions[2]);  
        }
    }
    public void executeTile(ISP_Joshua j){
        if(operation.equals("MOVETO")){
            j.moveTo(value1);
            j.executeCurrentTile();
        }else if(operation.equals("ADD")){
            j.addMoney(value1);
        }else if(operation.equals("REMOVE")){
            j.payTax(value1);
        }else if(operation.equals("GETOUT")){
            j.addGetOutOfJail();
        }else if(operation.equals("GOTOJAIL")){
            j.sendToJail();
        }else if(operation.equals("MAKEHIMLOSE")){
            int numOfHouses=j.getNumOfHousesOwned();
            int numOfHotels=j.getNumOfHotelsOwned();
            int totalPayment = value1*numOfHouses+value2*numOfHotels;
            Util.messageDialog("Number of houses owned: "+numOfHouses+"*"+value1+'\n'+
                                "Number of hotels owned: "+numOfHotels+"*"+value2+'\n'+
                                "---------------------------\n"+
                                "Pay $"+totalPayment, "Chance");
            j.payTax(totalPayment);
        }else if(operation.equals("GETPLAYERS")){
            for(int i=1; i<j.numOfPlayers; i++){
                if(i!=j.curPlayer&&j.canCollectRentInJail(i)){
                    j.transferMoney(value1, i,j.curPlayer);
                }
            }
        }
    }
    public int getTileType(){
        return 5;
    }
    public String getInfo(){
        return message;
    }
}

/*
message&operation&values

Mystery Chest List

Advance to Go (Collect $200)
Bank error in your favor—Collect $200
Doctor's fee—Pay $50
From sale of stock you get $50
Get Out of Jail Free
Go to Jail–Go directly to jail–Do not pass Go–Do not collect $200
Grand Opera Night—Collect $50 from every player for opening night seats
Holiday Fund matures—Receive $100
Income tax refund–Collect $20
It is your birthday—Collect $10
Life insurance matures–Collect $100
Pay hospital fees of $100
Pay school fees of $150
Receive $25 consultancy fee
You are assessed for street repairs–$40 per house–$115 per hotel
You have won second prize in a beauty contest–Collect $10
You inherit $100
*/