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
    boolean hasGetOutOfJail[]=new boolean[numOfPlayers];
    int positionOfPlayers[] = new int[numOfPlayers];
    int balance[] = new int[numOfPlayers];
    Property monopolyTiles[] = new Property[NUMBEROFTILES];
    int bankAmount=0;
    // int propertyToPlayer[] = new int[numOfPlayers];
    // int propertyToHotel[] = new int[numOfPlayers];   //Player can own a max of 1 hotel
    // int propertyToHouse[] = new int[numOfPlayers];  //Player can own a max of __ hotel
    int curPlayer;
    ISP_Joshua(){
        // c=new Console("Monopoly");
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
    boolean canEvenSell(int colourId){
        int curTile = positionOfPlayers[curPlayer];
        int max=-1,min=-1;
        for(Property tile:monopolyTiles){
            if(tile.propertyColour==colourId){  //Same colour set
                if(max==-1)max=tile.getTierLevel();
                else max=Math.max(max,tile.getTierLevel());
                if(min==-1)min=tile.getTierLevel();
                else min=Math.min(min,tile.getTierLevel());
            }
        }
        return max==monopolyTiles[curTile].getTierLevel();
    }
    boolean canEvenBuild(int colourId){
        int curTile = positionOfPlayers[curPlayer];
        int max=-1,min=-1;
        for(Property tile:monopolyTiles){
            if(tile.propertyColour==colourId){  //Same colour set
                if(max==-1)max=tile.getTierLevel();
                else max=Math.max(max,tile.getTierLevel());
                if(min==-1)min=tile.getTierLevel();
                else min=Math.min(min,tile.getTierLevel());
            }
        }
        return min==monopolyTiles[curTile].getTierLevel();
    }
    boolean ownsColourSet(int colourId){
        for(Property tile:monopolyTiles){
            if(tile.propertyColour==colourId){  //Same colour set
                if(!tile.isOwnBy(curPlayer))return false;   //Someone else owns the tile
                if(tile.isMortgaged())return false; //Is mortgaged
            }
        }
        return true;
    }
    int getHotels(){
        int numOfHotels=0;
        // for(int i=1; i<)
        return 0;
    }
    int getBalance(int player){
        return balance[player];
    }
    int getBalance(){
        return getBalance(curPlayer);
    }
    void addGetOutOfJail(){
        addGetOutOfJail(curPlayer);
    }
    void addGetOutOfJail(int player){ //This will give the current player a get out of jail card
        hasGetOutOfJail[player]=true;
    }
    void addMoney(int amount,int player){   //This will change to include animation
        balance[player]+=amount;
    }
    void addMoney(int amount){
        addMoney(amount,curPlayer);
    }
    void removeMoney(int amount,int player){
        balance[player]-=amount;
    }
    void removeMoney(int amount){
        removeMoney(amount,curPlayer);
    }
    void removeMoneyToBank(int amount){ //Different from remove money, this money is kept and is able to collect from free parking
        balance[curPlayer]-=amount;
        bankAmount+=amount;
    }
    void transferMoney(int amount,int player,int player1){ //Transfer money from player to player1
        balance[player]-=amount;
        balance[player]+=amount;
    }
    void transferMoney(int amount,int player){  //Transfer money from curent player to player
        transferMoney(amount,curPlayer, player);
    }
    void moveTo(int properties,int player){
        //Make sure to check if pass go
    }
    void moveTo(int properties){
        moveTo(properties,curPlayer);
    }
    void resetBoard(){  //Resets board
        curPlayer=1;
        bankAmount=0;
        // Arrays.fill(propertyToHotel, 0);
        // Arrays.fill(propertyToHouse, 0);
        // Arrays.fill(propertyToPlayer,0);
        Arrays.fill(positionOfPlayers,1);
        Arrays.fill(balance,initialBalance);
        Arrays.fill(hasGetOutOfJail,false);
        
    }
    int[] rollDice(){
        int diceOne = (int)(6*Math.random()+1);
        int diceTwo = (int)(6*Math.random()+1);
        return new int[]{diceOne+diceTwo};
    }
    void move(int spaces){
        positionOfPlayers[curPlayer]+=spaces;
        if(positionOfPlayers[curPlayer]>=NUMBEROFTILES){    //Loop back to beginning
            positionOfPlayers[curPlayer]-=NUMBEROFTILES+1;
        }
    }
    void goToJail(){
        positionOfPlayers[curPlayer]=10;    //JAIL
    }
    void executeTile(){
        
    }
    void display(){
        while (true){
            int dice[]=rollDice();
            System.out.printf("You rolled a %d and a %d\nMove %d spaces",dice[0],dice[1],dice[0]+dice[1]);
            move(dice[0]+dice[1]);

        }
        /*
        Reset board
        Generate the properties
        Generate/Randomize cards


        Problems:
        How should i alternate between players? boolean?
        */
    }
    public static void main(String[] args){
        ISP_Joshua isp = new ISP_Joshua();
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