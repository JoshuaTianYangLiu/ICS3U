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
    int positionOfPlayers[] = new int[numOfPlayers];
    int balance[] = new int[numOfPlayers];
    Tile monopolyTiles[] = new Tile[NUMBEROFTILES+1];
    int diceOne,diceTwo;

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

    }
    void resetBoard(){
        curPlayer=1;
        Arrays.fill(positionOfPlayers,1);
        Arrays.fill(balance,initialBalance);
    }
    void moveForward(int n){
        positionOfPlayers[curPlayer]+=n;
        if(positionOfPlayers[curPlayer]>NUMBEROFTILES){    //That mean they passed go
            balance[curPlayer]+=200;    //TODO:Change this to method
            positionOfPlayers[curPlayer]-=NUMBEROFTILES;
        }
    }
    void nextTurn(){
        curPlayer++;
        if(curPlayer==numOfPlayers)curPlayer=1;
    }
    int getPosOfCurPlayer(){
        return positionOfPlayers[curPlayer];
    }
    void display(){
        resetBoard();
        while(true){
            c.println("Waiting for input");
            c.println("---------------------------");
            rollDice();
            c.getChar();
            c.println("You rolled a "+diceOne+" and a "+diceTwo);
            if(diceOne==diceTwo)c.println("Doubles!");
            c.println("---------------------------");
            c.println("You were on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            c.println(getPosOfCurPlayer());
            moveForward(diceOne+diceTwo);
            c.println("You are now on "+monopolyTiles[getPosOfCurPlayer()].getInfo());
            c.println(getPosOfCurPlayer());
            if(diceOne==diceTwo){
                c.println("Roll again");
            }else{
                c.println("Next turn");
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