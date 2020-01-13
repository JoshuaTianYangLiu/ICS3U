/*
Joshua Liu
Jan 10,2020
This game is monopoly, implemented rule by rule. Including original cards, properties and messages.
This program also allows customization of each tile, allowing you to create your own version
*/

/*
READ ME!
In the event of a OutOfMemoryException. Please raise the memory in
File -> Preferences -> Java
Please set Max Memory for JVM to 2048
*/

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
    final int NUMBEROFTILES=40;                                     //Number of tiles on the board starting from 1
    int numOfPlayers=(2)+1;                                         //Number of players offset by 1 starting from 1
    int pauseTime=500;                                              //Dictates how much does the program pauses per animation
    int initialBalance=1500;                                        //Initial balance of players
    int bankAmount=0;                                               //Use for free parking and tax
    boolean hasGetOutOfJail[];                                      //Has get out of jail card
    boolean inJail[];                                               //Is in jail
    int turnsInJail[];                                              //Keeps track how many turns the players are in jail
    int positionOfPlayers[];                                        //Position of players relative to the board
    int balance[];                                                  //Balannce of each player
    String nameOfPlayer[];                                          //Names of each player
    Tile monopolyTiles[] = new Tile[NUMBEROFTILES+1];               //Contains each tile and their execution
    int diceOne,diceTwo;                                            //Two values of the dice
    ChanceCard chancePile[];                                        //Chance pile randomly chosen and executed
    CommunityChestCard communityChestPile[];                        //Community chest pile randomly chosen and executed
    BufferedImage monopolyPieces[][] = new BufferedImage[numOfPlayers][4];  //Stores the images of the player's tiles in each orientation
    BufferedImage background;                                       //Background image
    Font textTitle=new Font("Cambria",Font.PLAIN,40);               //Text title font
    Font textHeader=new Font("Cambria",Font.PLAIN,20);              //Text header font
    Font textInfo = new Font ("Times New Roman", Font.PLAIN, 20);   //Text info font
    Font titleCard=new Font("Cambria",Font.BOLD,75);                //Title card font
    int curPlayer;                                                  //Id of current player
    boolean canCollectRentInJail=true,hasFreeParking=true;          //Settings
    String boardLanguages[];                                        //Stores the supported board languages
    String boardLanguage="US";                                      //Current language of the board


    ISP_Joshua(){
        c=new Console(25,80,"ISP_Joshua");
    }

    /**
     * Name: splashScreen
     * Decription: Draws the splash screen animation using threads
     */
    void splashScreen(){
        //Each interval a unrolled dice will run and roll a random number
        //After that it'll have the title screen and "Press any key"
        //Threads will be used to run multiple dice at the same time
        Color startingColor = new Color(192,192,192);   //Grey colour
        c.setColor(startingColor);
        c.fillRect(0,0,1000,1000);    //Draw bg
        DrawDice.loadAssets(c);
        //Create a 32x25 grid of 20x20 dice
        boolean diceDrawn[] = new boolean[801];
        int cnt=800;    //Number of dice left
        while(cnt>0){
            if(cnt%91==0){  //Every 91 dice pause and allow the program to catch up
                try{
                    Thread.sleep(1000);
                }catch(Exception e){}
            }
            int pos=random(1,cnt);  //Target dice
            int cntToPos=1;
            for(int i=0; i<diceDrawn.length; i++){
                if(!diceDrawn[i]){      //Not drawn yet
                    if(cntToPos++==pos){    //If dice drawn is target dice
                        diceDrawn[i]=true;
                        SplashScreen ss = new SplashScreen(c,i%32,i/32);    //Console window along with coords on dice
                        ss.start();
                        break;
                    }
                }
            }
            cnt--;
        }
        try{
            Thread.sleep(4000); //Allow remaining dice to load
        }catch(Exception e){}
        c.setColor(Color.BLACK);
        c.setFont(titleCard);
        c.drawString("MONOPOLY",125,200);
        c.setFont(textHeader);
        pressAnyKey();
    }
    
    /** 
     * Name: random
     * @param a
     * @param b
     * @return int
     * Returns random int from a-b inclusive
     */
    public int random(int a,int b){
        return (int)((b-a+1)*Math.random()+a);
    }
    
    /** 
     * Name: mainMenu
     * @return int
     * Draw main menu along with options and JOptionPane
     */
    int mainMenu(){
        title("Main Menu");
        c.setFont(textHeader);
        displayInfo("1: Play Monopoly\n"+
                    "2: Instructions\n"+
                    "3: Rules\n"+
                    "4: Settings\n"+
                    "5: Scoreboard\n"+
                    "6: Exit",250,40);
        return Util.queryInt("Please choose an option\n"+
                            "1: Play Monopoly\n"+
                            "2: Instructions\n"+
                            "3: Rules\n"+
                            "4: Settings\n"+
                            "5: Scoreboard\n"+
                            "6: Exit" , "Please choose a valid option","Main Menu",1,6);
    }

    /**
     * Name: instructions 
     * Displays instructions to play monopoly
     */
    void instructions(){
        title("Instructions");
        c.setFont(textInfo);
        displayInfo("Step 1: Roll the dice and move the corresponding amount\n"+
                    "Step 2: Buy/Auction an unowned property\n"+
                    "Step 3: Collect rent if someone lands on your properties\n"+
                    "Step 4: Become a monopoly and be the last man standing", 50, 30);
        pressAnyKey();
    }

    /**
     * Name: rules
     * Displays rules of monopoly
     */
    void rules(){
        int pageNum=1;  //Current page number
        
        //Official rules taken from https://en.wikibooks.org/wiki/Monopoly/Official_Rules
        while(true){
            title("Rules");
            c.setFont(textInfo);
        //                                                                               |||    The point where the text goes off screen
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
            if(pageNum==1){ //First page
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Back to Main Menu","Next Page"});
                if(choice==0)break;
                if(choice==1)pageNum++;
            }else if(pageNum==12){  //Last page
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Previous Page","Back to Main Menu"});
                if(choice==0)pageNum--;
                if(choice==1)break;
            }else{  //Any other page
                int choice=Util.optionDialog("Please choose an option","Da Rules", new String[] {"Back to Main Menu","Previous Page","Next Page"});
                if(choice==0)break;
                if(choice==1)pageNum--;
                if(choice==2)pageNum++;
            }
        }
    }

    /**
     * Name: settings
     * Allows user to change settings of the game monopoly 
     */
    void settings(){
        while(true){
            title("Settings");
            c.setFont(textHeader);
            String output="";   //Setup string
            output+="1: Number of players:                             "+(numOfPlayers-1)+'\n';
            output+="2: Pause between animations:               "+pauseTime+"ms\n";
            output+="3: Initial balance:                                     "+initialBalance+'\n';
            output+="4: Collect rent in jail?                               "+(canCollectRentInJail?"Yes":"No")+'\n';
            output+="5: Collect money on free parking?         "+(hasFreeParking?"Yes":"No")+'\n';
            output+="6: Board Language:                                   "+boardLanguage+'\n';
            displayInfo(output,50,40);
            int choice=Util.queryInt("Please choose an option to change\n"+ //Options
                                    "0: Back to Main Menu\n"+
                                    "1: Number of players\n"+
                                    "2: Pause between animations\n"+
                                    "3: Initial Balance\n"+
                                    "4: Collect rent in jail\n"+
                                    "5: Collect money on free parking\n"+
                                    "6: Board Language","Invalid choice!","Settings",0,6);
            if(choice==0)break;
            if(choice==1){  //Change options accordingly
                numOfPlayers=Util.queryInt("Specify number of players.\n2-4", "Invalid input!","Settings",2,4)+1;
            }else if(choice==2){
                pauseTime=Util.queryInt("Specify pause between animations in ms","Invalid input!","Settings",100,1000);
            }else if(choice==3){
                initialBalance=Util.queryInt("Specify initial balance","Invalid input!","Settings",1,999999);
            }else if(choice==4){
                canCollectRentInJail=Util.optionDialog("Collect rent in jail?","Settings",new String[]{"Yes","No"})==0;
            }else if(choice==5){
                hasFreeParking=Util.optionDialog("Collect money on free parking?","Settings",new String[]{"Yes","No"})==0;
            }else if(choice==6){
                int languageId=Util.optionDialog("Please choose a language","Settings",boardLanguages);
                File f = new File("assets\\"+boardLanguages[languageId]);
                if(f.exists()){
                    boardLanguage=boardLanguages[languageId];
                    loadAssets();   //Load new assets into the game
                }else{
                    Util.messageDialog("The path assets\\"+boardLanguages[languageId]+" does not exist", "Settings");
                }
            }
        }
    }

    /**
     * Name: goodbye
     * Display screen when exiting
     */
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

    /**
     * Name: scoreboard
     * Loads data from scoreboard file, sorts and parses data and displays top 10
     */
    void scoreboard(){
        BufferedReader file=null;
        try{
        file = new BufferedReader(new FileReader("assets\\Scoreboard.txt"));    //Open file
        }catch(Exception e){
            c.close();
            Util.exception("Scoreboard.txt does not exist", e);
        }
        String fileContent="",fileLine="";
        while(true){    //Read all content into string
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
            Util.exception("Error in closing Scoreboard.txt", e);
        }
        String contents[] = fileContent.split("\n");    //Seperate to lines
        int len=contents.length/2;  //Number of players in the file
        for(int i=0; i<len-1; i++){ //Bubble sort
            for(int j=0; j<len-i-1; j++){
                int a=Integer.parseInt(contents[2*j+1]);
                int b=Integer.parseInt(contents[2*j+3]);
                if(a<b){
                    String temp=contents[2*j];  //Swap values
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
        for(int i=0; i<Math.min(len,10); i++){  //Display top 10
            outputName+=(i+1)+": "+contents[2*i]+'\n';
            outputScore+="$"+contents[2*i+1]+'\n';
        }
        displayInfo(outputName, 50,30);
        displayInfo(outputScore,400,30);
        pressAnyKey();
        //I will score it by whoever has the most money
    }
    
    /** 
     * Name: addToScoreboard
     * @param name
     * @param balance
     * Writes score to scoreboard file.
     */
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
    
    /** 
     * Name: pressAnyKey
     * @param input
     * Draws string and waits for input, shortens repetitiveness
     */
    void pressAnyKey(String input){
        c.drawString(input,215,450);
        c.getChar();
    }

    /**
     * Name: pressAnyKey
     * Calls pressAnyKey
     */
    void pressAnyKey(){
        pressAnyKey("Press any key to continue");
    }
    
    /** 
     * Name: displayInfo
     * @param input
     * @param xOffset
     * @param yGap
     * Draws string left justified with gap between each line
     */
    void displayInfo(String input,int xOffset,int yGap){
        String line[] = input.split("\n");
        for(int i=0; i<line.length; i++){
            c.drawString(line[i],xOffset,yGap*i+150);
        }
    }
    
    /** 
     * Name: title
     * @param title
     * Draws title
     */
    void title(String title){
        c.drawImage(background,0,0,null);
        c.setFont(textTitle);
        c.setColor(Color.WHITE);
        c.drawString(title,50,75);
        c.setFont(textHeader);
        c.drawString("Monopoly",500,50);
    }

    /**
     * Name: credits
     * Draws credits
     */
    void credits(){
        c.drawString("Made By Joshua Liu",225,300);
    }

    /**
     * Name: resetBoard
     * Reset board by reassigning the values 
     */
    void resetBoard(){
        curPlayer=1;
        bankAmount=0;
        hasGetOutOfJail=new boolean[numOfPlayers];
        inJail = new boolean[numOfPlayers];
        turnsInJail = new int[numOfPlayers];
        positionOfPlayers = new int[numOfPlayers];
        balance = new int[numOfPlayers];
        nameOfPlayer = new String[numOfPlayers];
        monopolyPieces = new BufferedImage[numOfPlayers][4];
        nameOfPlayer[0]="No One";
        Arrays.fill(positionOfPlayers,1);
        Arrays.fill(balance,initialBalance);
        Arrays.fill(hasGetOutOfJail,false);
        Arrays.fill(inJail,false);
        Arrays.fill(turnsInJail,0);
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(isOwnableTile(i)){
                OwnableTile t = (OwnableTile)monopolyTiles[i];
                t.reset();
            }
        }
    }

    /**
     * Name: addGetOutOfJail
     * Adds get out of jail free card to current player and runs animation
     */
    void addGetOutOfJail(){
        hasGetOutOfJail[curPlayer]=true;
        gameGui.drawGetOutOfJail(curPlayer);
    }

    /**
     * Name: useGetOutOfJailCard
     * removes get out of jail card and frees player from jail
     */
    void useGetOutOfJailCard(){
        inJail[curPlayer]=false;
        hasGetOutOfJail[curPlayer]=true;
        gameGui.hideGetOutOfJail(curPlayer);
    }
    
    /** 
     * Name: moveBack
     * @param spaces
     * Moves back _ spaces, only used by community chest and chance cards
     */
    void moveBack(int spaces){
        int origPos=positionOfPlayers[curPlayer];
        positionOfPlayers[curPlayer]-=spaces;
        gameGui.moveBack(origPos,positionOfPlayers[curPlayer], this);
        //As this is only activated by chance tiles this will have loop over from the start
    }
    
    /** 
     * Name: moveUntil
     * @param id
     * Move until the piece lands on a certain tile id
     */
    void moveUntil(int id){
        int position = getPosOfCurPlayer();
        while(monopolyTiles[position].getTileType()!=id){   //Continue till it reaches tile with id
            position++;
            if(position>NUMBEROFTILES){
                position=1;
            }
        }
        moveTo(position);   //Move to that position
    }
    
    /** 
     * Name: moveTo
     * @param n
     * To move to a position of the board
     */
    void moveTo(int n){
        int moveBy=n-getPosOfCurPlayer();   //Number of tiles you need to move by
        if(moveBy<0){
            moveBy+=NUMBEROFTILES;  //Takes care of "wrapping" back around the board
        }
        moveForward(moveBy);
    }
    
    /** 
     * Name: moveForward
     * @param n
     * Moves forward n spaces, checks if passed go and animates the movement
     */
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
    
    /** 
     * Name: transferMoney
     * @param amount
     * @param player1
     * @param player2
     * Transfers money from player1 to player2, and animates
     */
    void transferMoney(int amount,int player1,int player2){
        balance[player1]-=amount;
        balance[player2]+=amount;
        gameGui.modifyTwoBalance(amount, player1, player2, this);
    }
    
    /** 
     * Name: transferMoney
     * @param amount
     * @param player
     * Transfer money from curplayer to player
     */
    void transferMoney(int amount,int player){
        transferMoney(amount,curPlayer,player);
    }
    
    /** 
     * Name: addMoney
     * @param amount
     * @param playerId
     * Add money to playerId, and animate
     */
    void addMoney(int amount,int playerId){
        balance[playerId]+=amount;
        gameGui.modifyBalance(amount,playerId, this);
    }
    
    /** 
     * Name: removeMoney
     * @param amount
     * @param playerId
     * Removes money from playerId, and animate
     */
    void removeMoney(int amount,int playerId){  //This will have an animation added
        balance[playerId]-=amount;
        gameGui.modifyBalance(-amount,playerId, this);
    }
    
    /** 
     * Name: addMoney
     * @param amount
     * adds money to current player
     */
    void addMoney(int amount){  //This will have an animation added
        addMoney(amount,curPlayer);
    }
    
    /** 
     * Name: removeMoney
     * @param amount
     * Removes money from current player
     */
    void removeMoney(int amount){  //This will have an animation added
        removeMoney(amount,curPlayer);
    }
    
    /** 
     * Name: runAuction
     * @param land
     * Runs auction to auction off land
     */
    void runAuction(OwnableTile land){
        String info = land.getFullInfo();   //Get full information
        boolean canBid[] = new boolean[numOfPlayers];   //Players who can bit
        Arrays.fill(canBid,true);
        int numberOfBidders=numOfPlayers-1; //numOfPlayers-1 is the true number 
        int currentBid=10;  //Starting bid
        int winningBidId=0; //Id of the winning bid
        int currentBidder=curPlayer;    //Current bidder
        while(true){
            if(canBid[currentBidder]&&balance[currentBidder]>currentBid){   //If can bid and has enough to bid
                int choice = Util.optionDialog(land.getFullInfo()+"\n"+     //Get choice to bid or fold
                                                "-------------------------------------"+"\n"+
                                                "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                                "The current bid is $"+currentBid+".\n"+
                                                "Currently winning: "+nameOfPlayer[winningBidId], "Auction",new String[] {"Bid","Fold"});
                if(choice==0&&balance[currentBidder]>currentBid){   //If can bid and has enough money
                    choice=Util.queryInt(land.getFullInfo()+"\n"+   //Enter bid
                                        "-------------------------------------"+"\n"+
                                        "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                        "The current bid is $"+currentBid+".\n"+
                                        "Currently winning: "+nameOfPlayer[winningBidId], "Please bid more than the current amount and make sure you have enough money to bid", "Auction", currentBid+1, balance[currentBidder]);
                    currentBid=choice;
                    winningBidId=currentBidder; //Update winning bid and winning bid id
                }else if(choice==1){
                    canBid[currentBidder]=false;    //Cannot bid, out of auction
                    numberOfBidders--;  //Number of bidders left
                }
            }else{
                int choice = Util.optionDialog(land.getFullInfo()+"\n"+ //Force fold
                                                "-------------------------------------"+"\n"+
                                                "Currently Bidding: "+nameOfPlayer[currentBidder]+".\n"+
                                                "The current bid is $"+currentBid+".\n"+
                                                "Currently winning: "+nameOfPlayer[winningBidId], "Auction",new String[] {"Fold"});
                numberOfBidders--;
                canBid[currentBidder]=false;
            }
            if(numberOfBidders==1)break;    //There's one bidder left
            currentBidder++;    //Move to next bidder
            if(currentBidder==numOfPlayers) currentBidder-=numOfPlayers-1;  //Wrap back to 1
        }
        for(int i=1; i<numOfPlayers; i++){  //Checks who wins the auction
            if(canBid[i]){
                winningBidId=i;
                break;
            }
        }
        land.buyProperty(this,currentBid,winningBidId); //Buy property for id for amount
    }

    /**
     * Name: pickChanceCard
     * Execute random chance card
     */
    void pickChanceCard(){
        int randCard = random(0,chancePile.length-1);   //Choose random card
        // int randCard = (int)(chancePile.length*Math.random());
        Util.messageDialog(chancePile[randCard].getInfo(),"Chance");    //Show info
        chancePile[randCard].executeTile(this); //Execute
    }

    /**
     * Name: pickCommunityChestCard
     * Execute random community chest card
     */
    void pickCommunityChestCard(){
        int randCard = random(0,communityChestPile.length-1);   //Choose random card
        // int randCard = (int)(communityChestPile.length*Math.random());
        Util.messageDialog(communityChestPile[randCard].getInfo(),"Community Chest");   //Show info
        communityChestPile[randCard].executeTile(this); //Execute
    }
    
    /** 
     * Name: getNumOfHousesOwned
     * @return int
     * get total number of houses owned by current player
     */
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
    
    /** 
     * Name: getNumOfHotelsOwned
     * @return int
     * get total number of hotels owned by current player
     */
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
    
    /** 
     * Name: numberOfTilesOwned
     * @param id
     * @param playerId
     * @return int
     * Gets number of tiles types id owned by player
     */
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
    
    /** 
     * Name: getBalance
     * @return int
     * Returns balance of current player
     */
    int getBalance(){
        return balance[curPlayer];
    }
    
    /** 
     * Name: payTax
     * @param amount
     * Current player pays tax of amount amount is added to bank and animates
     */
    void payTax(int amount){
        balance[curPlayer]-=amount;
        bankAmount+=amount;
        gameGui.modifyBalance(-amount, this);
    }

    /**
     * Name: collectTax
     * Free Parking gives tax to player
     */
    void collectTax(){
        if(bankAmount!=0){
            Util.messageDialog("Free Parking "+nameOfPlayer[curPlayer]+" Collects $"+bankAmount, "Free Parking");
            balance[curPlayer]+=bankAmount;
            bankAmount=0;
        }
    }
    
    /** 
     * Name: canCollectRentInJail
     * @param id
     * @return boolean
     * Returns if the person can collect rent of current player
     */
    boolean canCollectRentInJail(int id){
        return inJail[id]?canCollectRentInJail:true;
    }

    /**
     * Name: nextTurn
     * Moves to next player
     */
    void nextTurn(){
        curPlayer++;
        if(curPlayer==numOfPlayers)curPlayer=1;
    }
    
    /** 
     * Name: getPosOfCurPlayer
     * @return int
     * returns position of current player
     */
    int getPosOfCurPlayer(){
        return positionOfPlayers[curPlayer];
    }

    /**
     * Name: sendToJail
     * Send current player to jail and animates
     */
    void sendToJail(){
        int posOfJailZone=0;
        for(int i=1; i<=NUMBEROFTILES; i++){    //Search for jail tile
            if(monopolyTiles[i].getTileType()==9){
                posOfJailZone=i;
                break;
            }
        }
        positionOfPlayers[curPlayer]=posOfJailZone; //Move to jail
        inJail[curPlayer]=true;
        turnsInJail[curPlayer]=0;
        gameGui.moveToJail(posOfJailZone, this);    //Run animation 
    }
    
    /** 
     * Name: tileOwnedBy
     * @param pos
     * @return int
     * Return person who owns the tile pos
     */
    int tileOwnedBy(int pos){
        if(isOwnableTile(pos)){
            OwnableTile t=(OwnableTile)monopolyTiles[pos];
            return t.getOwnerId();
        }
        return 0;
    }
    
    /** 
     * Name: isOwnableTile
     * @param n
     * @return boolean
     * Returns if tile is one that can be owned
     */
    boolean isOwnableTile(int n){
        return monopolyTiles[n].getTileType()==3||
                monopolyTiles[n].getTileType()==2||
                monopolyTiles[n].getTileType()==10;
    }
    
    /** 
     * Name: getMinOfColourSet
     * @param colourId
     * @return int
     * Returns minimum value of houses of a colour set  (For even building rule)
     */
    int getMinOfColourSet(int colourId){
        int retVal=5;
        for(int i=1; i<=NUMBEROFTILES; i++){    //Go through tiles
            if(monopolyTiles[i].getTileType()==2){  //Property tile
                Property t = (Property)monopolyTiles[i];
                if(t.propertyColour==colourId){ //Same id
                    if(t.getOwnerId()!=curPlayer){
                        return -1;
                    }else if(t.isMortgaged()){
                        return -2;
                    }else{
                        retVal=Math.min(retVal,t.tierLevel);    //Min of houses
                    }
                }
            }
        }
        return retVal;
    }
    
    /** 
     * Name: getMaxOfColourSet
     * @param colourId
     * @return int
     * Returns maximum value of houses of a colour set  (For even selling rule)
     */
    int getMaxOfColourSet(int colourId){    //Similar idea to getMinOfColourSet
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
    
    /** 
     * Name: getMonopolyPiece
     * @param playerId
     * @param orientation
     * @return BufferedImage
     * returns image of monopoly piece playerid and orientation
     */
    BufferedImage getMonopolyPiece(int playerId,int orientation){
        return monopolyPieces[playerId][orientation];
    }

    /**
     * Name: executeCurrentTile
     * execute tile of current player pos 
     */
    void executeCurrentTile(){
        monopolyTiles[getPosOfCurPlayer()].executeTile(this);
    }

    /**
     * Name: randomizePlayers
     * randomize order of players
     */
    void randomizePlayers(){
        for(int i=0; i<100; i++){
            int a = random(1,numOfPlayers-1);
            int b = random(1,numOfPlayers-1);
            // int a = (int)((numOfPlayers-1)*Math.random()+1);
            // int b = (int)((numOfPlayers-1)*Math.random()+1);
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

    /**
     * Name: choosePieces
     * Allow user to choose pieces 
     */
    void choosePieces(){
        String pieceNames[] = new String[]{"Boot","Boat","Car","Thimble","TopHat"}; //Pieces list
        boolean pieceChosen[] = new boolean[pieceNames.length]; //Ones chosen
        for(int i=1; i<numOfPlayers; i++){
            while(true){
                int choice=Util.optionDialog(nameOfPlayer[i]+", please choose a monopoly piece.", "Monopoly Pieces",pieceNames);
                if(pieceChosen[choice]){    //Already chosen
                    Util.messageDialog("The "+pieceNames[choice]+" is already chosen.\nPlease choose another","Monopoly Pieces");
                }else{
                    Util.messageDialog(pieceNames[choice]+" chosen for "+nameOfPlayer[i],"Monopoly Pieces");
                    pieceChosen[choice]=true;
                    try{
                        monopolyPieces[i][0]=ImageIO.read(new File("assets\\Images\\"+pieceNames[choice]+"0.png")); //Load images
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
    
    /** 
     * Name: getInfoStepsAhead
     * @param step
     * @return String
     * get info about the tile n steps ahead of current player
     */
    String getInfoStepsAhead(int step){
        int pos = getPosOfCurPlayer()+step;
        if(pos>NUMBEROFTILES)pos-=NUMBEROFTILES;
        return monopolyTiles[pos].getInfo();
    }
    
    /** 
     * Name nameTaken
     * @param i
     * @return boolean
     * returns if name is taken
     */
    boolean nameTaken(int i){
        for(int j=1; j<i; j++){ //1 to i
            if(nameOfPlayer[i].equals(nameOfPlayer[j])){
                return true;
            }
        }
        return false;
    }

    /**
     * Name: display
     * Main method to run and control the game of monopoly
     */
    void display(){
        c.close();
        int concurrentDoubles=0;
        //Choose number of players Max is 4
        resetBoard();
        //Choose names
        for(int i=1; i<numOfPlayers;){  //Loop through players
            nameOfPlayer[i]=Util.inputDialog("Player "+i+", choose your name.","Invalid name","MONOPOLY");
            if(nameTaken(i)){
                Util.messageDialog("Name Taken","MONOPOLY");
            }else{
                i++;
            }
        }
        //Choose pieces
        choosePieces(); //Choose pieces
        randomizePlayers(); //Randomize order
        gameGui=new DrawGame(this); //Draw board game
        long timeElasped=System.currentTimeMillis();    //Start time elasped
        beginTurn:
        while(true){
            gameGui.drawPlayerList(this);   //Draw list of players
            if(inJail[curPlayer]){  //In jail
                while(true){
                    int choice=Util.queryInt("You are in Jail. Choose an option\n"+ //Options
                                            "1: Roll dice\n"+
                                            "2: Use get out of jail card\n"+
                                            "3: Pay $200 bail",
                                            "Please choose option 1 or 2.","JAIL", 1, 3);
                    if(choice==1){  //Roll dice
                        rollDice();
                        if(diceOne==diceTwo){   //Doubles is released
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "You are released!", "JAIL");
                            inJail[curPlayer]=false;
                            turnsInJail[curPlayer]=0;
                            break;
                        }else{  //Not released yet
                            Util.messageDialog("You rolled a "+diceOne+" and "+diceTwo+".\n"+
                                                "Still in jail.", "JAIL");
                            turnsInJail[curPlayer]++;
                            if(turnsInJail[curPlayer]==3){  //In jail for 3 turns
                                inJail[curPlayer]=false;
                                turnsInJail[curPlayer]=0;
                            }
                            nextTurn();
                            continue beginTurn; //Go to next turn
                        }
                    }else if(choice==2){    //Use get out of jail card
                        if(hasGetOutOfJail[curPlayer]){ //Has card
                            useGetOutOfJailCard();
                            break;  //Continue player's turn
                        }else{
                            Util.messageDialog("You do not have a get out of jail card!","JAIL");
                        }
                    }else if(choice==3){    //Pay bail
                        if(getBalance()>=200){  //Enough money
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
                int choice = Util.queryInt(nameOfPlayer[curPlayer]+"\'s turn, choose an option\n"+      //Options
                                            "1: Roll dice\n"+
                                            "2: Mortgage/Unmortgage\n"+
                                            "3: Buy/Sell Houses\n"+
                                            "4: Concede\n"+
                                            "5: Back to Menu\n", "Please choose a valid option",nameOfPlayer[curPlayer], 1,5);
                if(choice==1)break;
                if(choice==2){
                    DrawMortgage t = new DrawMortgage();    //Display mortgage
                    t.draw(this);
                    t.close();
                }
                if(choice==3){
                    DrawHouse t = new DrawHouse();  //Display Houses
                    t.draw(this);
                    t.close();
                }
                if(choice==4){
                    removePlayer(curPlayer);    //Concede player
                    nextTurn();
                    continue beginTurn;
                }
                if(choice==5){              //To main menu
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
            if(diceOne==diceTwo){   //Doubles
                concurrentDoubles++;    //Add doubles in a row
                if(concurrentDoubles==3){   //Roll three doubles rule (GO TO JAIL)

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
            moveForward(diceOne+diceTwo);   //Move
            executeCurrentTile();   //Execute tile the player is on
            gameGui.drawPlayerList(this);   //Update list
            removeLosers(); //Remove anyone in debt
            if(hasWinner()){    //One player left
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
            }
            Util.messageDialog("Press any enter to continue", "MONOPOLY");
            if(diceOne==diceTwo){   //Doubles
                Util.messageDialog("--------------\n"+
                                    "| Roll again |\n"+
                                    "--------------\n","MONOPOLY");
            }else{
                concurrentDoubles=0;    //Not doubles
                nextTurn();
                Util.messageDialog("Next turn: "+nameOfPlayer[curPlayer],"MONOPOLY");
            }
        }
    }
    
    /** 
     * Name: hasWinng
     * @return boolean
     * if there is a winner
     */
    boolean hasWinner(){
        return numOfPlayers==2;
    }
    
    /** 
     * Name: removePlayer
     * @param id
     * Remove player and swap player infos down
     */
    void removePlayer(int id){
        Util.messageDialog(nameOfPlayer[id]+", YOU LOSE!","MONOPOLY");
        numOfPlayers--;
        for(int k=1; k<=NUMBEROFTILES; k++){    //Remove ownership from any tiles
            if(isOwnableTile(k)){
                OwnableTile t = (OwnableTile)monopolyTiles[k];
                if(id==t.getOwnerId())t.reset();;
            }
        }
        for(int j=id; j<numOfPlayers; j++){ //Copy down the information
            hasGetOutOfJail[j]=hasGetOutOfJail[j+1];
            inJail[j]=inJail[j+1];
            turnsInJail[j]=turnsInJail[j+1];
            positionOfPlayers[j]=positionOfPlayers[j+1];
            balance[j]=balance[j+1];
            nameOfPlayer[j]=nameOfPlayer[j+1];
            for(int k=1; k<=NUMBEROFTILES; k++){
                if(isOwnableTile(k)){   //Transfer ownership
                    OwnableTile t = (OwnableTile)monopolyTiles[k];
                    if(j+1==t.getOwnerId())t.tranferOwnership(j);
                }
            }
        }
    }

    /**
     * Name: removeLosers
     * remove those who are in debt
     */
    void removeLosers(){
        for(int i=1; i<numOfPlayers; i++){
            if(balance[i]<0){
                removePlayer(i);
            }
        }
    }

    /**
     * Name: rollDice
     * Roll dice 
     */
    void rollDice(){
        diceOne=random(1, 6);
        diceTwo=random(1, 6);
    }

    /**
     * Name: loadAssets
     * Load information, images important for the game's function
     */
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
        BufferedReader boardTileId=null,properties=null,railroad=null,utility=null,tax=null;    //Read files
        try{
            boardTileId = new BufferedReader(new FileReader(new File("assets\\BoardTileId.txt")));
        }catch(Exception e){
            System.out.println("BoardTileId.txt not found, please include file");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            properties = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\Properties.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Properties.txt not found, please include file", e);
        }
        try{
            railroad = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\Railroad.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Railroad.txt not found, please include file", e);
        }
        try{
            utility = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\Utility.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Utility.txt not found, please include file", e);
        }
        try{
            tax = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\Tax.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Tax.txt not found, please include file", e);
        }
        for(int i=1; i<=NUMBEROFTILES; i++){    //Loop through tiles
            try{
                String boardTileInputId=boardTileId.readLine();
                //Assign tile with corresponding object
                //Error trapping if needed
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
        //Close files
        try{
            boardTileId.close();
            properties.close();
            railroad.close();
            utility.close();
        }catch(Exception e){
            c.close();
            Util.exception("Error in closing files", e);
        }

        //Read chance and community chest
        BufferedReader chance=null,communityChest=null;
        try{
            chance = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\Chance.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Cannot open Chance.txt, file does not exist", e);
        }
        try{
            communityChest = new BufferedReader(new FileReader(new File("assets\\"+boardLanguage+"\\CommunityChest.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Cannot open CommunityChest.txt, file does not exist", e);
        }
        //Read entire file into string
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

        //Parse information into objects
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

        //Read additional board information
        try{
            background=ImageIO.read(new File("assets\\Images\\Background.png"));
        }catch(Exception e){
            c.close();
            Util.exception("Error reading Background.png", e);
        }
        BufferedReader boardLang=null;
        try{
            boardLang=new BufferedReader(new FileReader(new File("assets\\BoardLanguages.txt")));
        }catch(Exception e){
            c.close();
            Util.exception("Error reading BoardLanguages", e);
        }
        String languageContent="";
        String languageLine="";
        //Read contents into string
        try{
            while((languageLine=boardLang.readLine())!=null){
                languageContent+=languageLine+'\n';
            }
        }catch(Exception e){
            c.close();
            Util.exception("Error reading BoardLanguages.txt", e);
        }
        boardLanguages=languageContent.split("\n");
    }
    

    
    /** 
     * Name: main
     * @param args
     * Main method
     */
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