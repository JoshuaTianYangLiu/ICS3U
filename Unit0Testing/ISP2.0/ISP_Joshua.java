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
    int diceOne,diceTwo;
    ChanceCard chancePile[];
    CommunityChestCard communityChestPile[];
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
        Util.messageDialog("This is main menu", "Main Menu");
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

        Arrays.fill(positionOfPlayers,1);
        Arrays.fill(balance,initialBalance);
        Arrays.fill(hasGetOutOfJail,false);
        Arrays.fill(inJail,false);
        Arrays.fill(turnsInJail,0);
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
                break;
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
        int numberOfBidders=numOfPlayers-1; //numOfPlayers-1 is the true number 
        int currentBid=10;
        int winningBidId=0;
        int currentBidder=curPlayer;
        while(true){
            if(canBid[currentBidder]&&balance[currentBidder]>currentBid){
                int choice = Util.optionDialog(land.getFullInfo()+"\n"+
                                                "-------------------------------------"+"\n"+
                                                "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                                "The current bid is $"+currentBid+".\n"+
                                                "Currently winning: "+nameOfPlayer[winningBidId], "Auction",new String[] {"Bid","Fold"});
                if(choice==0){
                    choice=Util.queryInt(land.getFullInfo()+"\n"+
                                        "-------------------------------------"+"\n"+
                                        "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                        "The current bid is $"+currentBid+".\n"+
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
            if(currentBidder==numOfPlayers) currentBidder-=numOfPlayers-1;
        }
        land.buyProperty(this,currentBid,currentBidder);
    }
    void pickChanceCard(){
        int randCard = (int)(chancePile.length*Math.random());
        Util.messageDialog(chancePile[randCard].getInfo(),"Chance");
        chancePile[randCard].executeTile(this);
    }
    void pickCommunityChestCard(){
        int randCard = (int)(communityChestPile.length*Math.random());
        Util.messageDialog(communityChestPile[randCard].getInfo(),"Community Chest");
        communityChestPile[randCard].executeTile(this);
    }
    int getNumOfHousesOwned(){
        int retVal=0;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                Property t = (Property)monopolyTiles[i];
                if(t.getOwnerId()==curPlayer){
                    if(t.tierLevel!=5)retVal+=t.tierLevel;
                }
            }
        }
        return retVal;
    }
    int getNumOfHotelsOwned(){
        int retVal=0;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                Property t = (Property)monopolyTiles[i];
                if(t.getOwnerId()==curPlayer){
                    if(t.tierLevel==5)retVal++;
                }
            }
        }
        return retVal;
    }
    int numberOfTilesOwned(int id,int playerId){
        int numberOfTilesWithSameId=0;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==id){
                OwnableTile t = (OwnableTile)monopolyTiles[i];
                if (t.getOwnerId()==playerId) {
                    numberOfTilesWithSameId++;
                }
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
        if(bankAmount!=0){
            Util.messageDialog("Free Parking "+nameOfPlayer[curPlayer]+" Collects $"+bankAmount, "Free Parking");
            balance[curPlayer]+=bankAmount;
            bankAmount=0;
        }
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
    void displayMortgage(){
        Console mortgage = new Console("Mortgage/Unmortgage");
        int tilesOwned[] = new int[NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==3||
            monopolyTiles[i].getTileType()==2||
            monopolyTiles[i].getTileType()==10){
                OwnableTile t = (OwnableTile)monopolyTiles[i];
                if(t.getOwnerId()==curPlayer){
                    tilesOwned[arrPointer++]=i;
                }
            }
        }
        while(true){
            mortgage.clear();
            mortgage.println(nameOfPlayer[curPlayer]+" \'s balance: $"+balance[curPlayer]);
            mortgage.print("Name",40);
            mortgage.println("Status");
            mortgage.println("----------------------------------------------------");
            for(int i=1; i<arrPointer; i++){
                mortgage.print(i+": "+monopolyTiles[tilesOwned[i]].getInfo(),40);
                OwnableTile t = (OwnableTile)monopolyTiles[tilesOwned[i]];
                if(t.isMortgaged())mortgage.println("Mortgaged");
                else mortgage.println("Not Mortgaged");
            }
            int choice;
            if(arrPointer==1){
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }else{
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window\n"+
                                        "1-"+(arrPointer-1)+": Mortgage/Unmortgage property", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }
            if(choice==0)break;
            //TODO: Add option to confirm
            OwnableTile t = (OwnableTile)monopolyTiles[tilesOwned[choice]];
            if(t.isMortgaged())t.unMortgage(this);
            else t.mortgage(this);
        }
        mortgage.close();
    }
    int getMinOfColourSet(int colourId){
        int retVal=5;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                Property t = (Property)monopolyTiles[i];
                if(t.propertyColour==colourId){
                    if(t.getOwnerId()!=curPlayer){
                        return -1;
                    }else if(t.isMortgaged()){
                        return -2;
                    }else{
                        retVal=Math.min(retVal,t.tierLevel);
                    }
                }
            }
        }
        return retVal;
    }
    int getMaxOfColourSet(int colourId){
        int retVal=0;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                Property t = (Property)monopolyTiles[i];
                if(t.propertyColour==colourId){
                    if(t.getOwnerId()!=curPlayer){
                        return -1;
                    }else if(t.isMortgaged()){
                        return -2;
                    }else{
                        retVal=Math.max(retVal,t.tierLevel);
                    }
                }
            }
        }
        return retVal;
    }
    void displayHouses(){
        Console houses = new Console("Buy/Sell Houses");
        int propertiesOwned[] = new int[NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                OwnableTile t = (OwnableTile)monopolyTiles[i];
                if(t.getOwnerId()==curPlayer){
                    propertiesOwned[arrPointer++]=i;
                }
            }
        }
        while(true){
            houses.clear();
            houses.println(nameOfPlayer[curPlayer]+" \'s balance: $"+balance[curPlayer]);
            houses.print("Name",30);
            houses.print("Is mortgaged",15);
            houses.print("Houses Owned",12);
            houses.println("Hotels Owned",12);
            houses.println("----------------------------------------------------");
            for(int i=1; i<arrPointer; i++){
                houses.print(i+": "+monopolyTiles[propertiesOwned[i]].getInfo(),30);
                Property t = (Property)monopolyTiles[propertiesOwned[i]];
                if(t.isMortgaged())houses.print("Mortgaged",15);
                else houses.print("Not Mortgaged",15);
                houses.print(t.getNumOfHouses(),12);
                houses.println(t.getNumOfHotels(),12);
            }
            int choice;
            if(arrPointer==1){
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window", "Please choose a valid option","Buy/Sell",0,arrPointer-1);
            }else{
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window\n"+
                                        "1-"+(arrPointer-1)+": Buy/Sell Houses", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }
            if(choice==0)break;
            Property p = (Property)monopolyTiles[propertiesOwned[choice]];
            int buyOrSell = Util.optionDialog(p.getFullInfo(),p.getInfo(), new String[]{"Buy","Sell"});
            if(buyOrSell==0){   //Buy
                p.buyHouse(this);
            }else{  //Sell
                p.sellHouse(this);
            }
        }
        houses.close();
    }
    void executeCurrentTile(){
        monopolyTiles[getPosOfCurPlayer()].executeTile(this);
    }
    void randomizePlayers(){
        for(int i=0; i<100; i++){
            int a = (int)((numOfPlayers-1)*Math.random()+1);
            int b = (int)((numOfPlayers-1)*Math.random()+1);
            String switchVar=nameOfPlayer[a];
            nameOfPlayer[a]=nameOfPlayer[b];
            nameOfPlayer[b]=switchVar;
        }
    }
    void display(){
        int concurrentDoubles=0;
        resetBoard();
        // randomizePlayers();
        nameOfPlayer[0]="No One";
        nameOfPlayer[1]="Player 1 (CAT)";   //TODO: TEMP LINES
        nameOfPlayer[2]="Player 2 (SHIP)";
        beginTurn:
        while(true){
            c.println(nameOfPlayer[curPlayer]);
            if(inJail[curPlayer]){
                while(true){
                    int choice=Util.queryInt("You are in Jail. Choose an option\n"+
                                            "1: Roll dice\n"+
                                            "2: Use get out of jail card",
                                            "Please choose option 1 or 2.","JAIL", 1, 2);
                    if(choice==1){
                        rollDice();
                        if(diceOne==diceTwo){
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "You are released!", "JAIL");
                            inJail[curPlayer]=false;
                            break;
                        }else{
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "Still in jail.", "JAIL");
                            turnsInJail[curPlayer]++;
                            if(turnsInJail[curPlayer]==3){
                                inJail[curPlayer]=false;
                            }
                            nextTurn();
                            c.clear();
                            continue beginTurn;
                        }
                    }else{
                        if(hasGetOutOfJail[curPlayer]){
                            inJail[curPlayer]=false;
                            hasGetOutOfJail[curPlayer]=true;
                            break;
                        }else{
                            Util.messageDialog("You do not have a get out of jail card!","JAIL");
                        }
                    }
                }
            }
            while(true){
                int choice = Util.queryInt(nameOfPlayer[curPlayer]+"\'s turn, choose an option\n"+
                                            "1: Roll dice\n"+
                                            "2: Mortgage/Unmortgage\n"+
                                            "3: Buy/Sell Houses\n"+
                                            "4: Back to Menu", "Please choose a valid option",nameOfPlayer[curPlayer], 1,4);
                if(choice==1)break;
                if(choice==2)
                    displayMortgage();
                if(choice==3)
                    displayHouses();
                if(choice==4)
                    break beginTurn;
            }
            c.println("--------------------");
            c.println("Press any key to roll");
            rollDice();
            c.getChar();
            c.println();
            c.println("--------------------------");
            c.println("| You rolled a "+diceOne+" and a "+diceTwo+" |");
            c.println("--------------------------");
            c.println();
            if(diceOne==diceTwo){
                concurrentDoubles++;
                if(concurrentDoubles==3){   //Roll three doubles rule
                    c.println("----------------------------------------------");
                    c.println("| You rolled 3 doubles in a row, GO TO JAIL! |");
                    c.println("----------------------------------------------");
                    sendToJail();
                    nextTurn();
                    Util.messageDialog("Press any key to continue", "MONOPOLY");
                    concurrentDoubles=0;
                    c.clear();
                    continue;
                }
                c.println("------------");
                c.println("| Doubles! |");
                c.println("------------");
            }
            c.println("Was on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            c.println("-----");
            c.println("  |  ");
            c.println("  v  ");
            moveForward(diceOne+diceTwo);
            executeCurrentTile();
            c.println("Now on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            c.println();
            c.println("----------------------------------------");
            c.println("BALANCE:");
            for(int i=1; i<numOfPlayers; i++){
                c.print(nameOfPlayer[i]+":",20);
                c.println(balance[i]+"",10);
            }
            Util.messageDialog("Press any key to continue", "MONOPOLY");
            if(diceOne==diceTwo){
                c.clear();
                c.println("--------------");
                c.println("| Roll again |");
                c.println("--------------");
                c.println();
            }else{
                c.println("Next turn");
                c.clear();
                concurrentDoubles=0;
                nextTurn();
            }
            removeLosers();
            if(hasWinner()){
                c.println("WINNER!");
                break;
            }
        }
    }
    boolean hasWinner(){
        return numOfPlayers==2;
    }
    void removeLosers(){
        for(int i=1; i<numOfPlayers; i++){
            if(balance[i]<0){
                numOfPlayers--;
                for(int j=i; j<numOfPlayers; i++){
                    hasGetOutOfJail[j]=hasGetOutOfJail[j+1];
                    inJail[j]=inJail[j+1];
                    turnsInJail[j]=turnsInJail[j+1];
                    positionOfPlayers[j]=positionOfPlayers[j+1];
                    balance[j]=balance[j+1];
                    nameOfPlayer[j]=nameOfPlayer[j+1];
                    for(int k=1; k<=NUMBEROFTILES; k++){
                        if(monopolyTiles[k].getTileType()==3||
                            monopolyTiles[k].getTileType()==2||
                            monopolyTiles[k].getTileType()==10){
                            OwnableTile t = (OwnableTile)monopolyTiles[k];
                            if(k+1==t.getOwnerId())t.tranferOwnership(k);
                        }
                    }
                }
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
        BufferedReader boardTileId=null,properties=null,railroad=null,utility=null,tax=null;
        //Two options UK and US
        String boardLanguage="US";

        try{
            boardTileId = new BufferedReader(new FileReader(new File("assets\\BoardTileId.txt")));
        }catch(Exception e){
            System.out.println("BoardTileId.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            properties = new BufferedReader(new FileReader(new File("assets\\Properties"+boardLanguage+".txt")));
        }catch(Exception e){
            System.out.println("Properties.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            railroad = new BufferedReader(new FileReader(new File("assets\\Railroad"+boardLanguage+".txt")));
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
        try{
            tax = new BufferedReader(new FileReader(new File("assets\\Tax.txt")));
        }catch(Exception e){
            System.out.println("Tax.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        for(int i=1; i<=NUMBEROFTILES; i++){
            try{
                String boardTileInputId=boardTileId.readLine();
                if(boardTileInputId.equals("1")){
                    monopolyTiles[i]=new Go();
                }else if(boardTileInputId.equals("2")){
                    try{
                        monopolyTiles[i]=new Property(properties.readLine());
                    }catch(Exception e){
                        System.out.println("Something wrong happened on file reading Properties.txt and parsing\n Stack trace: ");
                        e.printStackTrace();
                        //TODO: Differentiate the error between readline and parsing
                        System.exit(1);
                    }
                }else if(boardTileInputId.equals("3")){
                    try{
                        monopolyTiles[i]=new Railroad(railroad.readLine());
                    }catch(Exception e){
                        System.out.println("Something wrong happened on file reading Railroad.txt and parsing\n Stack trace: ");
                        e.printStackTrace();
                        //TODO: Differentiate the error between readline and parsing
                        System.exit(1);
                    }
                }else if(boardTileInputId.equals("4")){
                    monopolyTiles[i]=new ChanceTile();
                }else if(boardTileInputId.equals("5")){
                    monopolyTiles[i]=new CommunityChestTile();
                }else if(boardTileInputId.equals("6")){
                    try{
                        monopolyTiles[i]=new Tax(tax.readLine());
                    }catch(Exception e){
                        System.out.println("Something wrong happened on file reading Tax.txt and parsing\n Stack trace: ");
                        e.printStackTrace();
                        //TODO: Differentiate the error between readline and parsing
                        System.exit(1);
                    }
                }else if(boardTileInputId.equals("7")){
                    monopolyTiles[i]=new FreeParking();
                }else if(boardTileInputId.equals("8")){
                    monopolyTiles[i]=new GoToJail();
                }else if(boardTileInputId.equals("9")){
                    monopolyTiles[i]=new JailZone();
                }else if(boardTileInputId.equals("10")){
                    try{
                        monopolyTiles[i]=new Utility(utility.readLine());
                    }catch(Exception e){
                        System.out.println("Something wrong happened on file reading Railroad.txt and parsing\n Stack trace: ");
                        e.printStackTrace();
                        //TODO: Differentiate the error between readline and parsing
                        System.exit(1);
                    }
                }else{
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
            chance = new BufferedReader(new FileReader(new File("assets\\Chance"+boardLanguage+".txt")));
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
        String inputLine="",entireInput="";
        try{
            while((inputLine=chance.readLine())!=null){
                entireInput+=inputLine+'\n';
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        String chanceArr[] = entireInput.split("\n");
        entireInput="";
        try{
            while((inputLine=communityChest.readLine())!=null){
                entireInput+=inputLine+'\n';
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        String communityChestArr[]= entireInput.split("\n");
        chancePile=new ChanceCard[chanceArr.length];
        communityChestPile=new CommunityChestCard[communityChestArr.length];
        for(int i=0; i<chanceArr.length; i++){
            try{
                chancePile[i]=new ChanceCard(chanceArr[i]);
            }catch(Exception e){
                System.out.println("Parsing error in ChanceCard.java make sure each line of ChanceCards.txt is valid");
                System.out.println("Error parsing line "+chanceArr[i]);
                e.printStackTrace();
                System.exit(1);
            }
        }
        for(int i=0; i<communityChestArr.length; i++){
            try{
                communityChestPile[i]=new CommunityChestCard(communityChestArr[i]);
            }catch(Exception e){
                System.out.println("Parsing error in CommunityChest.java make sure each line of CommunityChest.txt is valid");
                System.out.println("Error parsing line "+communityChestArr[i]);
                e.printStackTrace();
                System.exit(1);
            }
        }
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