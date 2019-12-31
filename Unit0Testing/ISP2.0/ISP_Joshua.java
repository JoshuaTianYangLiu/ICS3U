import hsa.Console;
import java.io.*;
import java.util.*;

public class ISP_Joshua{
    Console c;
    //Player id will start from 1
    //Tiles with start from 1
    final int NUMBEROFTILES=40;
    int numOfPlayers=(2)+1;
    int initialBalance=1500;
    int bankAmount=0;
    boolean hasGetOutOfJail[]=new boolean[numOfPlayers];
    boolean inJail[] = new boolean[numOfPlayers];
    int turnsInJail[] = new int[numOfPlayers];
    int positionOfPlayers[] = new int[numOfPlayers];
    int balance[] = new int[numOfPlayers];
    String nameOfPlayer[] = new String[numOfPlayers];
    Tile monopolyTiles[] = new Tile[NUMBEROFTILES+1];
    ArrayList<OwnableTile> playerInventory[] = new ArrayList[numOfPlayers];
    int diceOne,diceTwo;
    Queue<ChanceCard> chancePile = new LinkedList<ChanceCard>();
    Queue<CommunityChestCard> communityChestPile = new LinkedList<CommunityChestCard>();
    // int propertyToPlayer[] = new int[numOfPlayers];
    // int propertyToHotel[] = new int[numOfPlayers];   //Player can own a max of 1 hotel
    // int propertyToHouse[] = new int[numOfPlayers];  //Player can own a max of __ hotel
    int curPlayer;
    ISP_Joshua(){
        c=new Console("Monopoly");
    }
    void splashScreen(){

    }
    int mainMenu(){
        return 1;
    }
    void instructions(){

    }
    void rules(){

    }
    void settings(){

    }
    void goodbye(){

    }
    void scoreboard(){
        //I will score it by whoever has the most money
    }
    void resetBoard(){
        curPlayer=1;

        for(int i=0; i<numOfPlayers; i++){
            playerInventory[i]=new ArrayList<OwnableTile>();
        }
        Arrays.fill(positionOfPlayers,1);
        Arrays.fill(balance,initialBalance);
        Arrays.fill(hasGetOutOfJail,false);
    }
    void addGetOutOfJail(){
        hasGetOutOfJail[curPlayer]=true;
    }
    void moveBack(int spaces){
        positionOfPlayers[curPlayer]-=spaces;
        //As this is only activated by chance tiles this will have loop over from the start
    }
    void moveUntil(int id){
        int position = getPosOfCurPlayer();
        while(monopolyTiles[getPosOfCurPlayer()].getTileType()!=id){
            position++;
            if(position>NUMBEROFTILES){
                position=1;
            }
        }
        moveTo(position);
    }
    void moveTo(int n){ //TODO: Test this method
        int moveBy=n-getPosOfCurPlayer();
        if(moveBy<0){
            moveBy+=NUMBEROFTILES;
        }
        moveForward(moveBy);
    }
    void moveForward(int n){
        positionOfPlayers[curPlayer]+=n;
        if(positionOfPlayers[curPlayer]>NUMBEROFTILES){    //That mean they passed go
            balance[curPlayer]+=200;    //TODO: Change this to method
            positionOfPlayers[curPlayer]-=NUMBEROFTILES;
        }
    }
    void transferMoney(int amount,int player1,int player2){
        balance[player1]-=amount;
        balance[player2]+=amount;
    }
    void transferMoney(int amount,int player){  //Transfer money from curplayer to player
        transferMoney(amount,curPlayer,player);
    }
    void addMoney(int amount){  //This will have an animation added
        balance[curPlayer]+=amount;
    }
    void removeMoney(int amount){  //This will have an animation added
        balance[curPlayer]-=amount;
    }
    void runAuction(OwnableTile land){ //TODO: Run auction for property
        String info = land.getFullInfo();
        boolean canBid[] = new boolean[numOfPlayers];
        Arrays.fill(canBid,true);
        int numberOfBidders=numOfPlayers;
        int currentBid=10;
        int winningBidId=0;
        int currentBidder=curPlayer;
        while(true){
            if(canBid[currentBidder]){
                int choice = Util.optionDialog("The current bid is $"+currentBid+".\n"+
                                                "Currently winning: "+nameOfPlayer[winningBidId], "Auction",new String[] {"Bid","Fold"});
                if(choice==0){
                    choice=Util.queryInt("The current bid is $"+currentBid+".\n"+
                                        "Currently winning: "+nameOfPlayer[winningBidId], "Please bid more than the current amount and make sure you have enough money to bid", "Auction", currentBid+1, balance[currentBidder]);
                    currentBid=choice;
                    winningBidId=currentBidder;
                }else if(choice==1){
                    canBid[currentBidder]=false;
                    numberOfBidders--;
                }
            }
            if(numberOfBidders==1)break;
            currentBidder++;
            if(currentBidder>numOfPlayers) currentBidder-=numOfPlayers;
        }
        land.buyProperty(this,currentBid,currentBidder);
    }
    void addToInventory(OwnableTile land){
        //Add tile to inventory to make it easier to design ui for buying/selling/trading property
        //This also may make it easier to do some searching even though it may be done in a few extra lines
        playerInventory[curPlayer].add(land);
    }
    int numberOfTilesOwned(int id){
        int numberOfTilesWithSameId=0;
        for(OwnableTile t:playerInventory[curPlayer]){
            if (t.getOwnerId()==curPlayer&&((Tile)t).getTileType() == id) {
                numberOfTilesWithSameId++;
            }
        }
        return numberOfTilesWithSameId;
    }
    int getBalance(){
        return balance[curPlayer];
    }
    void payTax(int amount){
        balance[curPlayer]-=amount;
        bankAmount+=amount;
    }
    void collectTax(){
        balance[curPlayer]+=bankAmount;
        bankAmount=0;
    }
    void nextTurn(){
        curPlayer++;
        if(curPlayer==numOfPlayers)curPlayer=1;
    }
    int getPosOfCurPlayer(){
        return positionOfPlayers[curPlayer];
    }
    void sendToJail(){
        int posOfJailZone=0;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==9){
                posOfJailZone=i;
                break;
            }
        }
        positionOfPlayers[curPlayer]=posOfJailZone;
        inJail[curPlayer]=true;
        turnsInJail[curPlayer]=0;
    }
    void display(){
        int concurrentDoubles=0;
        resetBoard();
        while(true){
            c.println("Waiting for input");
            c.println("---------------------------");
            if(inJail[curPlayer]){
                while(true){
                    int choice=Util.queryInt("You are in Jail. Choose an option\n"+
                                            "1: Roll dice\n"+
                                            "2: Use get out of jail card",
                                            "Please choose option 1 or 2.","JAIL", 1, 2);
                    if(choice==1){
                        rollDice();
                        if(diceOne==diceTwo||turnsInJail[curPlayer]==3){
                            inJail[curPlayer]=false;
                        }
                        turnsInJail[curPlayer]++;
                    }else{
                        if(hasGetOutOfJail[curPlayer]){
                            inJail[curPlayer]=false;
                            hasGetOutOfJail[curPlayer]=true;
                        }else{
                            Util.messageDialog("You do not have a get out of jail card!","JAIL");
                        }
                    }
                }
            }
            rollDice();
            c.getChar();
            c.println("You rolled a "+diceOne+" and a "+diceTwo);
            if(diceOne==diceTwo){
                concurrentDoubles++;
                c.println("Doubles!");
            }
            c.println("---------------------------");
            c.println("You were on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            if(concurrentDoubles==3){   //Roll three doubles rule
                sendToJail();
                nextTurn();
                continue;
            }else{
                moveForward(diceOne+diceTwo);
            }
            c.println("You are now on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            monopolyTiles[getPosOfCurPlayer()].executeTile(this);
            if(diceOne==diceTwo){
                c.println("Roll again");
            }else{
                c.println("Next turn");
                concurrentDoubles=0;
                nextTurn();
            }
        }
    }
    void rollDice(){
        diceOne=(int)(6*Math.random()+1);
        diceTwo=(int)(6*Math.random()+1);
    }
    void loadAssets(){
        /*
        Loading from boardTiles.txt to decide which tile is what.
        Number to type of tile as follows:
        1	GO
        2	PROPERTY
        3	RAILROAD
        4	CHANCE
        5	COMMUNITYCHEST
        6	TAX
        7	FREEPARKING
        8	GOTOJAIL
        9	JAILZONE
        10	UTILITY
        */
        BufferedReader boardTileId=null,properties=null,railroad=null,utility=null;
        //Two options UK and US
        String boardLanguage="UK";

        try{
            boardTileId = new BufferedReader(new FileReader(new File("assets\\BoardTileId.txt")));
        }catch(Exception e){
            System.out.println("BoardTileId.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            if(boardLanguage.equals("US"))
                properties = new BufferedReader(new FileReader(new File("assets\\PropertiesUS.txt")));
            else
                properties = new BufferedReader(new FileReader(new File("assets\\PropertiesUK.txt")));
        }catch(Exception e){
            System.out.println("Properties.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            if(boardLanguage.equals("US"))
                railroad = new BufferedReader(new FileReader(new File("assets\\RailroadUS.txt")));
            else
                railroad = new BufferedReader(new FileReader(new File("assets\\RailroadUK.txt")));
        }catch(Exception e){
            System.out.println("Railroad.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            utility = new BufferedReader(new FileReader(new File("assets\\Utility.txt")));
        }catch(Exception e){
            System.out.println("Utility.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        for(int i=1; i<=NUMBEROFTILES; i++){
            try{
                switch(boardTileId.readLine()){
                    case "1":
                        monopolyTiles[i]=new Go();
                        break;
                    case "2":
                        try{
                            monopolyTiles[i]=new Property(properties.readLine());
                        }catch(Exception e){
                            System.out.println("Something wrong happened on file reading Properties.txt and parsing\n Stack trace: ");
                            e.printStackTrace();
                            //TODO: Differentiate the error between readline and parsing
                            System.exit(1);
                        }
                        break;
                    case "3":
                        try{
                            monopolyTiles[i]=new Railroad(railroad.readLine());
                        }catch(Exception e){
                            System.out.println("Something wrong happened on file reading Railroad.txt and parsing\n Stack trace: ");
                            e.printStackTrace();
                            //TODO: Differentiate the error between readline and parsing
                            System.exit(1);
                        }
                        //TODO: Add file for railroad
                        break;
                    case "4":
                        monopolyTiles[i]=new ChanceTile();
                        break;
                    case "5":
                        monopolyTiles[i]=new CommunityChestTile();
                        break;
                    case "6":
                        monopolyTiles[i]=new Tax();
                        break;
                    case "7":
                        monopolyTiles[i]=new FreeParking();
                        break;
                    case "8":
                        monopolyTiles[i]=new GoToJail();
                        break;
                    case "9":
                        monopolyTiles[i]=new JailZone();
                        break;
                    case "10":
                        try{
                            monopolyTiles[i]=new Utility(utility.readLine());
                        }catch(Exception e){
                            System.out.println("Something wrong happened on file reading Railroad.txt and parsing\n Stack trace: ");
                            e.printStackTrace();
                            //TODO: Differentiate the error between readline and parsing
                            System.exit(1);
                        }
                        break;
                    default:
                        System.out.println("Unknown id in BoardTileId.txt");
                        System.exit(1);
                }
            }catch(Exception e){
                System.out.println("Error in reading BoardTileId.txt, not enough id.");
                e.printStackTrace();
                System.exit(1);
            }
        }
        try{
            boardTileId.close();
            properties.close();
            railroad.close();
            utility.close();
        }catch(Exception e){
            System.out.println("Error in closing files");
            e.printStackTrace();
            System.exit(1);
        }
        BufferedReader chance=null,communityChest=null;
        try{
            chance = new BufferedReader(new FileReader(new File("assets\\Chance.txt")));
        }catch(Exception e){
            System.out.println("Cannot open Chance.txt, file does not exist");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            communityChest = new BufferedReader(new FileReader(new File("assets\\CommunityChest.txt")));
        }catch(Exception e){
            System.out.println("Cannot open CommunityChest.txt, file does not exist");
            e.printStackTrace();
            System.exit(1);
        }
        while(chance.lines())
        //TODO: Also load the community chest and chance cards into array
    }
    

    public static void main(String[] args){
        ISP_Joshua isp = new ISP_Joshua();
        isp.loadAssets();
        isp.splashScreen();
        for(int i=isp.mainMenu(); i!=6; i=isp.mainMenu()){
            if(i==1)isp.display();
            if(i==2)isp.instructions();
            if(i==3)isp.rules();
            if(i==4)isp.settings();
            if(i==5)isp.scoreboard();
        }
        isp.goodbye();
    }
}