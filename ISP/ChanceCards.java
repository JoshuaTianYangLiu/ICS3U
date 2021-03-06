public class ChanceCards{
    enum ChanceLabels{
        MOVETO, //int
        ADD,    //int
        REMOVE, //int
        GETOUT, //void
        MAKEHIMLOSE,    //int,int
        NEARUTIL,   //void
        NEARRAIL,   //void
        GIVEPLAYERS,    //int
    }
    String message;
    ChanceLabels operation;
    int value1,value2;
    ChanceCards(String input){
        //We need to parse the string and assign the corresponding info
        //Will be seperated by &
        String portions[] = input.split("&");
        message=portions[0];
        operation=ChanceLabels.valueOf(portions[1]);
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
        case GIVEPLAYERS:
            value1=Integer.parseInt(portions[2]);
            break;
    }
    String getMessage(){
        return message;
    }
    void executeCard(ISP_Joshua j){
        switch(operation){
            case GETOUT:
                j.addGetOutOfJail();
                break;
            case NEARRAIL:
                for(int)
                break;
            case NEARUTIL:

                break;
            case MOVETO:
                j.moveTo(value1);
                break;
            case ADD:
                j.addMoney(value1);
                break;
            case REMOVE:
                //Remove money, figure out what to do
                j.removeMoneyToBank(value1);
                break;
            case MAKEHIMLOSE:
                //Pay __ for each house and __ for each hotel

                break;
            case GIVEPLAYERS:
                for(int i=1; i<=j.numOfPlayers; i++){
                    if(i!=j.curPlayer){
                        j.transferMoney(value1, i);
                    }
                }
                break;
            }
    }
    public static void main(String[] args){
        ChanceCards c= new ChanceCards("Advance to Go (Collect $200)&MOVETO&0");
        System.out.println(c.getMessage());
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