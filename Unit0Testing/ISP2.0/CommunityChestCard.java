public class CommunityChestCard{    //TODO: Clean up code from removing the Tile interface
    String message;
    CardLabels operation;
    int value1,value2;
    CommunityChestCard(String input){
        String portions[] = input.split("\\|");
        message=portions[0];
        operation=CardLabels.valueOf(portions[1]);
        switch(operation){
            case MOVETO:
                value1=Integer.parseInt(portions[2]);  
                break;
            case ADD:
                value1=Integer.parseInt(portions[2]);  
                break;
            case REMOVE:
                value1=Integer.parseInt(portions[2]);  
                break;
            case MAKEHIMLOSE:
                value1=Integer.parseInt(portions[2]);  
                value2=Integer.parseInt(portions[3]);  
                break;
            case GETPLAYERS:
                value1=Integer.parseInt(portions[2]);  
                break;
        }
    }
    public void executeTile(ISP_Joshua j){
        switch(operation){
            case MOVETO:
                j.moveTo(value1);
                break;
            case ADD:
                j.addMoney(value1);
                break;
            case REMOVE:
                j.payTax(value1);
                break;
            case GETOUT:
                j.addGetOutOfJail();
                break;
            case GOTOJAIL:
                j.sendToJail();
                break;
            case MAKEHIMLOSE:

                break;
            case GETPLAYERS:
                for(int i=1; i<=j.numOfPlayers; i++){
                    if(i!=j.curPlayer){
                        j.transferMoney(value1, i,j.curPlayer);
                    }
                }
                break;
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