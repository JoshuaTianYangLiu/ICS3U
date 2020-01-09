import hsa.Console;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class ISP_Joshua{
    DrawGame gameGui;
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
    BufferedImage monopolyPieces[][] = new BufferedImage[numOfPlayers][4];
    // int propertyToPlayer[] = new int[numOfPlayers];
    // int propertyToHotel[] = new int[numOfPlayers];   //Player can own a max of 1 hotel
    // int propertyToHouse[] = new int[numOfPlayers];  //Player can own a max of __ hotel
    int curPlayer;
    ISP_Joshua(){
    }
    void splashScreen(){

    }
    int mainMenu(){
        // Util.messageDialog("This is main menu", "Main Menu");
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
        gameGui.drawGetOutOfJail(curPlayer);
    }
    void useGetOutOfJailCard(){
        inJail[curPlayer]=false;
        hasGetOutOfJail[curPlayer]=true;
        gameGui.hideGetOutOfJail(curPlayer);
    }
    void moveBack(int spaces){
        int origPos=positionOfPlayers[curPlayer];
        positionOfPlayers[curPlayer]-=spaces;
        gameGui.moveBack(origPos,positionOfPlayers[curPlayer], this);
        //As this is only activated by chance tiles this will have loop over from the start
    }
    void moveUntil(int id){
        int position = getPosOfCurPlayer();
        while(monopolyTiles[position].getTileType()!=id){
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
        int originalPos = positionOfPlayers[curPlayer];
        positionOfPlayers[curPlayer]+=n;
        if(positionOfPlayers[curPlayer]>NUMBEROFTILES){    //That mean they passed go
            positionOfPlayers[curPlayer]-=NUMBEROFTILES;
            gameGui.moveFromTo(originalPos, positionOfPlayers[curPlayer],this);
            addMoney(200);
        }else{
            gameGui.moveFromTo(originalPos, positionOfPlayers[curPlayer],this);
        }
        
    }
    void transferMoney(int amount,int player1,int player2){
        balance[player1]-=amount;
        balance[player2]+=amount;
        gameGui.modifyTwoBalance(amount, player1, player2, this);
    }
    void transferMoney(int amount,int player){  //Transfer money from curplayer to player
        transferMoney(amount,curPlayer,player);
    }
    void addMoney(int amount,int playerId){  //This will have an animation added
        balance[playerId]+=amount;
        gameGui.modifyBalance(amount,playerId, this);
    }
    void removeMoney(int amount,int playerId){  //This will have an animation added
        balance[playerId]-=amount;
        gameGui.modifyBalance(-amount,playerId, this);
    }
    void addMoney(int amount){  //This will have an animation added
        balance[curPlayer]+=amount;
        gameGui.modifyBalance(amount, this);
    }
    void removeMoney(int amount){  //This will have an animation added
        balance[curPlayer]-=amount;
        gameGui.modifyBalance(-amount, this);
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
        for(int i=1; i<numOfPlayers; i++){
            if(canBid[i]){
                winningBidId=i;
                break;
            }
        }
        land.buyProperty(this,currentBid,winningBidId);
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
        gameGui.modifyBalance(-amount, this);
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
        gameGui.moveToJail(posOfJailZone, this);
    }
    int tileOwnedBy(int pos){
        if(isOwnableTile(pos)){
            OwnableTile t=(OwnableTile)monopolyTiles[pos];
            return t.getOwnerId();
        }
        return 0;
    }
    boolean isOwnableTile(int n){
        return monopolyTiles[n].getTileType()==3||
                monopolyTiles[n].getTileType()==2||
                monopolyTiles[n].getTileType()==10;
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
    BufferedImage getMonopolyPiece(int playerId,int orientation){
        return monopolyPieces[playerId][orientation];
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
            for(int j=0; j<4; j++){
                BufferedImage biSwitchVar = monopolyPieces[a][j];
                monopolyPieces[a][j]=monopolyPieces[b][j];
                monopolyPieces[b][j]=biSwitchVar;
            }
        }
    }
    void choosePieces(){
        String pieceNames[] = new String[]{"Boot","Boat","Car","Thimble","TopHat"};
        boolean pieceChosen[] = new boolean[pieceNames.length];
        for(int i=1; i<numOfPlayers; i++){
            while(true){
                int choice=Util.optionDialog(nameOfPlayer[i]+", please choose a monopoly piece.", "Monopoly Pieces",pieceNames);
                if(pieceChosen[choice]){
                    Util.messageDialog("The "+pieceNames[choice]+" is already chosen.\nPlease choose another","Monopoly Pieces");
                }else{
                    Util.messageDialog(pieceNames[choice]+" chosen for "+nameOfPlayer[i],"Monopoly Pieces");
                    pieceChosen[choice]=true;
                    try{
                        monopolyPieces[i][0]=ImageIO.read(new File("assets\\Images\\"+pieceNames[choice]+"0.png"));
                        monopolyPieces[i][1]=ImageIO.read(new File("assets\\Images\\"+pieceNames[choice]+"90.png"));
                        monopolyPieces[i][2]=ImageIO.read(new File("assets\\Images\\"+pieceNames[choice]+"180.png"));
                        monopolyPieces[i][3]=ImageIO.read(new File("assets\\Images\\"+pieceNames[choice]+"270.png"));
                    }catch(Exception e){
                        System.out.println("One of image "+pieceNames[choice]+".png does not exist.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                    break;
                }
            }
        }
    }
    String getInfoStepsAhead(int step){
        int pos = getPosOfCurPlayer()+step;
        if(pos>NUMBEROFTILES)pos-=NUMBEROFTILES;
        return monopolyTiles[pos].getInfo();
    }
    void display(){
        int concurrentDoubles=0;
        //Choose number of players Max is 4
        resetBoard();
        //Choose names
        nameOfPlayer[0]="No One";
        nameOfPlayer[1]="Player 1 (CAT)";   //TODO: TEMP LINES
        nameOfPlayer[2]="Player 2 (SHIP)";
        // nameOfPlayer[3]="Justin";
        // nameOfPlayer[4]="Josh";
        //Choose pieces
        choosePieces();
        randomizePlayers();
        gameGui=new DrawGame(this);
        long timeElasped=System.currentTimeMillis();
        beginTurn:
        while(true){
            gameGui.drawPlayerList(this);
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
                            continue beginTurn;
                        }
                    }else{
                        if(hasGetOutOfJail[curPlayer]){
                            useGetOutOfJailCard();
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
                if(choice==2){
                    DrawMortgage t = new DrawMortgage();
                    t.draw(this);
                    t.close();
                }
                if(choice==3)
                    displayHouses();
                if(choice==4)
                    break beginTurn;
            }
            rollDice();
            Util.messageDialog("--------------------------\n"+
                                "| You rolled a "+diceOne+" and a "+diceTwo+" |\n"+
                                "--------------------------\n","MONOPOLY");
            if(diceOne==diceTwo){
                concurrentDoubles++;
                if(concurrentDoubles==3){   //Roll three doubles rule

                    Util.messageDialog("----------------------------------------------\n"+
                                        "| You rolled 3 doubles in a row, GO TO JAIL! |\n"+
                                        "----------------------------------------------\n","MONOPOLY");
                    sendToJail();
                    nextTurn();
                    Util.messageDialog("Press any key to continue", "MONOPOLY");
                    concurrentDoubles=0;
                    continue;
                }
            }
            Util.messageDialog("Move to "+getInfoStepsAhead(diceOne+diceTwo)+"\n","MONOPOLY");
            moveForward(diceOne+diceTwo);
            executeCurrentTile();
            gameGui.drawPlayerList(this);
            removeLosers();
            if(hasWinner()){
                long sec=timeElasped;
                long min=sec/60;
                sec%=60;
                long hour=min/60;
                min%=60;
                Util.messageDialog("WINNER!\n"+nameOfPlayer[1]+"\n"+
                                    "Won with $"+balance[1]+"\n"+
                                    "Time Elasped, "+hour+":"+min+":"+sec,"MONOPOLY");
                //Save to leaderboard
                gameGui.close();
                gameGui=null;
                break;
                //Make sure to clear screen and stuff
            }
            Util.messageDialog("Press any enter to continue", "MONOPOLY");
            if(diceOne==diceTwo){
                Util.messageDialog("--------------\n"+
                                    "| Roll again |\n"+
                                    "--------------\n","MONOPOLY");
            }else{
                concurrentDoubles=0;
                nextTurn();
                Util.messageDialog("Next turn: "+nameOfPlayer[curPlayer],"MONOPOLY");
            }
        }
    }
    boolean hasWinner(){
        return numOfPlayers==2;
    }
    void removeLosers(){
        for(int i=1; i<numOfPlayers; i++){
            if(balance[i]<0){
                Util.messageDialog(nameOfPlayer[i]+", YOU LOSE!","MONOPOLY");
                numOfPlayers--;
                for(int k=1; k<=NUMBEROFTILES; k++){
                    if(isOwnableTile(k)){
                        OwnableTile t = (OwnableTile)monopolyTiles[k];
                        if(i==t.getOwnerId())t.reset();;
                    }
                }
                for(int j=i; j<numOfPlayers; j++){
                    hasGetOutOfJail[j]=hasGetOutOfJail[j+1];
                    inJail[j]=inJail[j+1];
                    turnsInJail[j]=turnsInJail[j+1];
                    positionOfPlayers[j]=positionOfPlayers[j+1];
                    balance[j]=balance[j+1];
                    nameOfPlayer[j]=nameOfPlayer[j+1];
                    for(int k=1; k<=NUMBEROFTILES; k++){
                        if(isOwnableTile(k)){
                            OwnableTile t = (OwnableTile)monopolyTiles[k];
                            if(j+1==t.getOwnerId())t.tranferOwnership(j);
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