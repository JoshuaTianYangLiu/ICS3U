import hsa.Console;
import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;

public class ISP_Joshua{
    DrawGame gameGui;
    Console c;
    //Player id will start from 1
    //Tiles with start from 1
    final int NUMBEROFTILES=40;
    int numOfPlayers=(2)+1;
    int pauseTime=500;
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
    BufferedImage background;
    Font textTitle=new Font("Cambria",Font.PLAIN,40);
    Font textHeader=new Font("Cambria",Font.PLAIN,20);
    Font textInfo = new Font ("Times New Roman", Font.PLAIN, 20);
    Font titleCard=new Font("Cambria",Font.BOLD,75);
    int curPlayer;
    boolean canCollectRentInJail=true,hasFreeParking=true;
    ISP_Joshua(){
        c=new Console(25,80,"ISP_Joshua");
    }
    void splashScreen(){
        Color startingColor = new Color(192,192,192);
        c.setColor(startingColor);
        c.fillRect(0,0,640,500);
        DrawDice.loadAssets(c);
        //Create a 32x25 grid of 20x20 dice
        boolean diceDrawn[] = new boolean[801];
        int cnt=800;
        while(cnt>0){
            if(cnt%91==0){
                try{
                    Thread.sleep(1000);
                }catch(Exception e){}
            }
            int pos=random(1,cnt);
            int cntToPos=1;
            for(int i=0; i<diceDrawn.length; i++){
                if(!diceDrawn[i]){
                    if(cntToPos++==pos){
                        diceDrawn[i]=true;
                        SplashScreen ss = new SplashScreen(c,i%32,i/32);
                        ss.start();
                        break;
                    }
                }
            }
            cnt--;
        }
        try{
            Thread.sleep(4000);
        }catch(Exception e){}
        c.setColor(Color.BLACK);
        c.setFont(titleCard);
        c.drawString("MONOPOLY",125,200);
        c.setFont(textHeader);
        pressAnyKey();
        //Each interval a unrolled dice will run and roll a random number
        //After that it'll have the title screen and "Press any key"
        //Threads will be used to run multiple dice at the same time
    }
    public int random(int a,int b){
        return (int)((b-a+1)*Math.random()+1);
    }
    int mainMenu(){
        title("Main Menu");
        c.setFont(textHeader);
        displayInfo("1: Display\n"+
                    "2: Instructions\n"+
                    "3: Rules\n"+
                    "4: Settings\n"+
                    "5: Scoreboard\n"+
                    "6: Exit",250,40);
        return Util.queryInt("Please choose an option\n"+
                            "1: Display\n"+
                            "2: Instructions\n"+
                            "3: Rules\n"+
                            "4: Settings\n"+
                            "5: Scoreboard\n"+
                            "6: Exit" , "Please choose a valid option","Main Menu",1,6);
    }
    void instructions(){
        title("Instructions");
        c.setFont(textInfo);
        displayInfo("Step 1: Roll the dice and move the corresponding amount\n"+
                    "Step 2: Buy/Auction an unowned property\n"+
                    "Step 3: Collect rent if someone lands on your properties\n"+
                    "Step 4: Become a monopoly and be the last man standing", 50, 30);
        pressAnyKey();
    }
    void rules(){
        int pageNum=1;
        
        //Official rules from https://en.wikibooks.org/wiki/Monopoly/Official_Rules
        while(true){
            title("Rules");
            c.setFont(textInfo);
        //                                                                               |||
            if(pageNum==1){
                displayInfo("On a player's turn, the player must roll the dice and move\n"+
                            "his/her token forward the number of spaces as rolled on the\n"+
                            "dice. In some editions, players must do any trades, building\n"+
                            "improvements etc. at the start of their turn before rolling\n"+
                            "the dice.\n",50,30);
            }else if(pageNum==2){
                displayInfo("If the player lands on an unowned property, the player may buy\n"+
                            "it for the price listed on that property's space. If he or she\n"+
                            "agrees to buy it, he or she pays the Bank the amount shown on\n"+
                            "the property space and receives the deed for that property. If\n"+
                            "he or she refuses to buy the property for the amount stated on\n"+
                            "the deed, the property is auctioned. Bidding may start at any\n"+
                            "price, and all players may bid. The highest bidder wins the\n"+
                            "property and pays the Bank the amount bid and receives the\n"+
                            "property's title deed. Railroads and utilities are also\n"+
                            "considered properties.\n",50,30);
            }else if(pageNum==3){
                displayInfo("If the player lands on an unmortgaged property owned by\n"+
                            "another player, he or she pays rent to that person, as specified\n"+
                            "on the property's deed. It is the property owner's responsibility\n"+
                            "to demand rent, and he or she has until the beginning of the\n"+
                            "second following player's turn to do so.\n",50,30);
            }else if(pageNum==4){
                displayInfo("If the player lands on his or her own property, or on property\n"+
                            "which is owned by another player but currently mortgaged,\n"+
                            "nothing happens.\n", 50,30);
            }else if(pageNum==5){
                displayInfo("If the player lands on Luxury Tax/Super Tax, he or she must\n"+
                            "pay the Bank $100 (in some editions of the game, only $75).\n", 50,30);
            }else if(pageNum==6){
                displayInfo("If the player lands on Income Tax he or she must pay the Bank\n"+
                            "$200",50,30);
            }else if(pageNum==7){
                displayInfo("If the player lands on a Chance or Community Chest, the player\n"+
                            "takes a card from the top of the respective pack and performs\n"+
                            "the instruction given on the card.\n",50,30);
            }else if(pageNum==8){
                displayInfo("If the player lands on the Jail space, he or she is \"Just\n"+
                            "Visiting\". No penalty applies.\n",50,30);
            }else if(pageNum==9){
                displayInfo("If the player lands on the Go to Jail square, he or she must\n"+
                            "move his token directly to Jail.\n", 50, 30);
            }else if(pageNum==10){
                displayInfo("If the player lands on or passes Go in the course of his or\n"+
                            "her turn, he or she receives $200 from the Bank. A player has\n"+
                            "until the beginning of his or her next turn to collect this money.\n",50,30);
            }else if(pageNum==11){
                displayInfo("You may sell houses back to the Bank for half the purchase\n"+
                            "price or sell property deeds to other players in the game.\n",50,30);
            }else if(pageNum==12){
                displayInfo("If a player skips another player's turn and is caught,\n"+
                            "the turn is transferred back to the player whose turn was\n"+
                            "skipped.\n",50,30);
            }
            if(pageNum==1){
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Back to Main Menu","Next Page"});
                if(choice==0)break;
                if(choice==1)pageNum++;
            }else if(pageNum==12){
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Previous Page","Back to Main Menu"});
                if(choice==0)pageNum--;
                if(choice==1)break;
            }else{
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Back to Main Menu","Previous Page","Next Page"});
                if(choice==0)break;
                if(choice==1)pageNum--;
                if(choice==2)pageNum++;
            }
        }
    }
    void settings(){
        while(true){
            title("Settings");
            c.setFont(textHeader);
            String output="";
            output+="1: Number of players:                             "+(numOfPlayers-1)+'\n';
            output+="2: Pause between animations:               "+pauseTime+"ms\n";
            output+="3: Initial balance:                                     "+initialBalance+'\n';
            output+="4: Collect rent in jail?                               "+(canCollectRentInJail?"Yes":"No")+'\n';
            output+="5: Collect money on free parking?          "+(hasFreeParking?"Yes":"No")+'\n';
            displayInfo(output,50,40);
            int choice=Util.queryInt("Please choose an option to change\n"+
                                    "0: Back to Main Menu\n"+
                                    "1: Number of players\n"+
                                    "2: Pause between animations\n"+
                                    "3: Initial Balance\n"+
                                    "4: Collect rent in jail\n"+
                                    "5: Collect money on free parking","Invalid choice!","Settings",0,5);
            if(choice==0)break;
            if(choice==1){
                numOfPlayers=Util.queryInt("Specify number of players.\n2-4", "Invalid input!","Settings",2,4)+1;
            }else if(choice==2){
                pauseTime=Util.queryInt("Specify pause between animations in ms","Invalid input!","Settings",100,1000);
            }else if(choice==3){
                initialBalance=Util.queryInt("Specify initial balance","Invalid input!","Settings",1,999999);
            }else if(choice==4){
                canCollectRentInJail=Util.optionDialog("Collect rent in jail?","Settings",new String[]{"Yes","No"})==0;
            }else if(choice==5){
                hasFreeParking=Util.optionDialog("Collect money on free parking?","Settings",new String[]{"Yes","No"})==0;
            }
        }
    }
    void goodbye(){
        c.drawImage(background,0,0,null);
        c.setFont(textTitle);
        c.setColor(Color.WHITE);
        c.drawString("Thank you for using my program.",50,75);
        c.setFont(textHeader);
        credits();
        pressAnyKey("Press any key to quit");
        c.close();
    }
    void scoreboard(){
        BufferedReader file=null;
        try{
        file = new BufferedReader(new FileReader("assets\\Scoreboard.txt"));
        }catch(Exception e){
            c.close();
            Util.exception("Scoreboard.txt does not exist", e);
        }
        String fileContent="",fileLine="";
        while(true){
            try{
                fileLine=file.readLine();
            }catch(Exception e){
                c.close();
                Util.exception("Reading file from Scoreboard.txt", e);
            }
            if(fileLine==null)break;
            fileContent+=fileLine+'\n';
        }
        try{
            file.close();
        }catch(Exception e){
            c.close();
            Util.exception("Closing Scoreboard.txt", e);
        }
        String contents[] = fileContent.split("\n");
        int len=contents.length/2;
        for(int i=0; i<len-1; i++){
            for(int j=0; j<len-i-1; j++){
                int a=Integer.parseInt(contents[2*j+1]);
                int b=Integer.parseInt(contents[2*j+3]);
                if(a<b){
                    String temp=contents[2*j];
                    contents[2*j]=contents[2*j+2];
                    contents[2*j+2]=temp;
                    temp=contents[2*j+1];
                    contents[2*j+1]=contents[2*j+3];
                    contents[2*j+3]=temp;
                }
            }
        }
        title("Scoreboard");
        c.setFont(textInfo);
        String outputName="";
        String outputScore="";
        for(int i=0; i<Math.min(len,10); i++){
            outputName+=(i+1)+": "+contents[2*i]+'\n';
            outputScore+="$"+contents[2*i+1]+'\n';
        }
        displayInfo(outputName, 50,30);
        displayInfo(outputScore,400,30);
        pressAnyKey();
        //I will score it by whoever has the most money
    }
    void addToScoreboard(String name,int balance){
        PrintWriter fileOutput=null;
        try{
            fileOutput=new PrintWriter(new FileWriter("assets\\Scoreboard.txt",true));
        }catch(Exception e){
            c.close();
            Util.exception("Scoreboard.txt does not exist", e);
        }
        fileOutput.println(name);
        fileOutput.println(balance);
        fileOutput.close();
    }
    void pressAnyKey(String input){
        c.drawString(input,215,450);
        c.getChar();
    }
    void pressAnyKey(){
        pressAnyKey("Press any key to continue");
    }
    void displayInfo(String input,int xOffset,int yGap){
        String line[] = input.split("\n");
        for(int i=0; i<line.length; i++){
            c.drawString(line[i],xOffset,yGap*i+150);
        }
    }
    void title(String title){
        c.drawImage(background,0,0,null);
        c.setFont(textTitle);
        c.setColor(Color.WHITE);
        c.drawString(title,50,75);
        c.setFont(textHeader);
        c.drawString("Monopoly",500,50);
    }
    void credits(){
        c.drawString("Made By Joshua Liu",225,300);
    }
    void resetBoard(){
        curPlayer=1;
        hasGetOutOfJail=new boolean[numOfPlayers];
        inJail = new boolean[numOfPlayers];
        turnsInJail = new int[numOfPlayers];
        positionOfPlayers = new int[numOfPlayers];
        balance = new int[numOfPlayers];
        nameOfPlayer = new String[numOfPlayers];
        monopolyPieces = new BufferedImage[numOfPlayers][4];
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
    void moveTo(int n){
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
    void runAuction(OwnableTile land){
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
                if(choice==0&&balance[currentBidder]>currentBid){
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
            }else{
                int choice = Util.optionDialog(land.getFullInfo()+"\n"+
                                                "-------------------------------------"+"\n"+
                                                "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                                "The current bid is $"+currentBid+".\n"+
                                                "Currently winning: "+nameOfPlayer[winningBidId], "Auction",new String[] {"Fold"});
                numberOfBidders--;
                canBid[currentBidder]=false;
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
    boolean canCollectRentInJail(int id){
        return inJail[id]?canCollectRentInJail:true;
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
                        Util.exception("One of "+pieceNames[choice]+".png images does not exist.", e);
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
    boolean nameTaken(int i){
        for(int j=1; j<i; j++){
            if(nameOfPlayer[i].equals(nameOfPlayer[j])){
                return true;
            }
        }
        return false;
    }
    void display(){
        c.close();
        int concurrentDoubles=0;
        //Choose number of players Max is 4
        resetBoard();
        //Choose names
        nameOfPlayer[0]="No One";
        for(int i=1; i<numOfPlayers;){
            nameOfPlayer[i]=Util.inputDialog("Player "+i+", choose your name.","Invalid name","MONOPOLY");
            if(nameTaken(i)){
                Util.messageDialog("Name Taken","MONOPOLY");
            }else{
                i++;
            }
        }
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
                                            "2: Use get out of jail card\n"+
                                            "3: Pay $200 bail",
                                            "Please choose option 1 or 2.","JAIL", 1, 3);
                    if(choice==1){
                        rollDice();
                        if(diceOne==diceTwo){
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "You are released!", "JAIL");
                            inJail[curPlayer]=false;
                            turnsInJail[curPlayer]=0;
                            break;
                        }else{
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "Still in jail.", "JAIL");
                            turnsInJail[curPlayer]++;
                            if(turnsInJail[curPlayer]==3){
                                inJail[curPlayer]=false;
                                turnsInJail[curPlayer]=0;
                            }
                            nextTurn();
                            continue beginTurn; //Go to next turn
                        }
                    }else if(choice==2){
                        if(hasGetOutOfJail[curPlayer]){
                            useGetOutOfJailCard();
                            break;  //Continue player's turn
                        }else{
                            Util.messageDialog("You do not have a get out of jail card!","JAIL");
                        }
                    }else if(choice==3){
                        if(getBalance()>=200){
                            removeMoney(200);
                            inJail[curPlayer]=false;
                            turnsInJail[curPlayer]=0;
                            nextTurn();
                            continue beginTurn;
                        }else{
                            Util.messageDialog("You do not have enough to pay bail","JAIL");
                        }
                    }
                }
            }
            while(true){
                int choice = Util.queryInt(nameOfPlayer[curPlayer]+"\'s turn, choose an option\n"+
                                            "1: Roll dice\n"+
                                            "2: Mortgage/Unmortgage\n"+
                                            "3: Buy/Sell Houses\n"+
                                            "4: Back to Menu\n", "Please choose a valid option",nameOfPlayer[curPlayer], 1,4);
                if(choice==1)break;
                if(choice==2){
                    DrawMortgage t = new DrawMortgage();
                    t.draw(this);
                    t.close();
                }
                if(choice==3){
                    DrawHouse t = new DrawHouse();
                    t.draw(this);
                    t.close();
                }
                // if(choice==4){
                //     DrawAuction t = new DrawAuction();
                //     t.draw(this);
                //     t.close();
                // }
                if(choice==4){
                    gameGui.close();
                    gameGui=null;
                    c=new Console(25,80,"ISP_Joshua");
                    break beginTurn;
                }
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
                gameGui.close();
                gameGui=null;
                addToScoreboard(nameOfPlayer[1],balance[1]);
                c=new Console(25,80,"ISP_Joshua");
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
            c.close();
            Util.exception("Properties.txt not found, please include file", e);
        }
        try{
            railroad = new BufferedReader(new FileReader(new File("assets\\Railroad"+boardLanguage+".txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Railroad.txt not found, please include file", e);
        }
        try{
            utility = new BufferedReader(new FileReader(new File("assets\\Utility.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Utility.txt not found, please include file", e);
        }
        try{
            tax = new BufferedReader(new FileReader(new File("assets\\Tax.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Tax.txt not found, please include file", e);
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
                        c.close();
                        Util.exception("Something wrong happened on file reading Properties.txt and parsing", e);
                    }
                }else if(boardTileInputId.equals("3")){
                    try{
                        monopolyTiles[i]=new Railroad(railroad.readLine());
                    }catch(Exception e){
                        c.close();
                        Util.exception("Something wrong happened on file reading Railroad.txt and parsing", e);
                    }
                }else if(boardTileInputId.equals("4")){
                    monopolyTiles[i]=new ChanceTile();
                }else if(boardTileInputId.equals("5")){
                    monopolyTiles[i]=new CommunityChestTile();
                }else if(boardTileInputId.equals("6")){
                    try{
                        monopolyTiles[i]=new Tax(tax.readLine());
                    }catch(Exception e){
                        c.close();
                        Util.exception("Something wrong happened on file reading Tax.txt and parsing", e);
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
                        c.close();
                        Util.exception("Something wrong happened on file reading Railroad.txt and parsing", e);
                    }
                }else{
                    c.close();
                    Util.exception("Unknown id in BoardTileId.txt",new Exception());
                }
            }catch(Exception e){
                c.close();
                Util.exception("Error in reading BoardTileId.txt, not enough id.", e);
            }
        }
        try{
            boardTileId.close();
            properties.close();
            railroad.close();
            utility.close();
        }catch(Exception e){
            c.close();
            Util.exception("Error in closing files", e);
        }
        BufferedReader chance=null,communityChest=null;
        try{
            chance = new BufferedReader(new FileReader(new File("assets\\Chance"+boardLanguage+".txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Cannot open Chance.txt, file does not exist", e);
        }
        try{
            communityChest = new BufferedReader(new FileReader(new File("assets\\CommunityChest.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Cannot open CommunityChest.txt, file does not exist", e);
        }
        String inputLine="",entireInput="";
        try{
            while((inputLine=chance.readLine())!=null){
                entireInput+=inputLine+'\n';
            }
        }catch(Exception e){
            c.close();
            Util.exception("Error reading Chance.txt", e);
        }
        String chanceArr[] = entireInput.split("\n");
        entireInput="";
        try{
            while((inputLine=communityChest.readLine())!=null){
                entireInput+=inputLine+'\n';
            }
        }catch(Exception e){
            c.close();
            Util.exception("Error reading CommunityChest.txt", e);
        }
        String communityChestArr[]= entireInput.split("\n");
        chancePile=new ChanceCard[chanceArr.length];
        communityChestPile=new CommunityChestCard[communityChestArr.length];
        for(int i=0; i<chanceArr.length; i++){
            try{
                chancePile[i]=new ChanceCard(chanceArr[i]);
            }catch(Exception e){
                c.close();
                Util.exception("Parsing error in ChanceCard.java make sure each line of ChanceCards.txt is valid", e);
            }
        }
        for(int i=0; i<communityChestArr.length; i++){
            try{
                communityChestPile[i]=new CommunityChestCard(communityChestArr[i]);
            }catch(Exception e){
                c.close();
                Util.exception("Parsing error in CommunityChest.java make sure each line of CommunityChest.txt is valid", e);
            }
        }
        try{
            background=ImageIO.read(new File("assets\\Images\\Background.png"));
        }catch(Exception e){
            c.close();
            Util.exception("Error reading Background.png", e);
        }
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