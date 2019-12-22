public class Property{
    static final int MORTGAGE=-1;
    String name;
    int propertyColour;
    int propertyCost;
    int tierLevel;  //0=rent -1= morgage, >0 houses/ hotels
    int tierCost[] = new int[6];
    int houseCost;
    int hotelCost;
    int mortgageValue;
    int playerId;
    Property(String input){
        playerId=0;
        tierLevel=0;
        String portions[] = input.split("&");
        name=portions[0];
        propertyColour=Integer.parseInt(portions[1]);
        houseCost=Integer.parseInt(portions[2]);
        hotelCost=Integer.parseInt(portions[3]);
        mortgageValue = Integer.parseInt(portions[4]);
        for(int i=0; i<6; i++){
            tierCost[i]=Integer.parseInt(portions[5+i]);
        }
        propertyCost=Integer.parseInt(portions[11]);
    }
    void buyProperty(ISP_Joshua j){
        if(playerId==0&j.getBalance()>propertyCost){
            playerId=j.curPlayer;                           //curent player new owns the property
            j.removeMoney(propertyCost);                    //Remove the money
        }else if (j.getBalance()<=propertyCost){
            //Not enough money
        }else{
            //Plot owned, cannot buy
        }
    }
    int getTierLevel(){
        return tierLevel;
    }
    boolean isBought(){
        return playerId!=0;
    }
    void payRent(ISP_Joshua j){
        if(tierLevel!=MORTGAGE&&j.curPlayer!=playerId){
            j.transferMoney(tierCost[tierLevel], playerId);
        }else{
            //If player owns property
            //If property is mortgaged, probably nothing needed
        }
    }
    boolean tierMaxedOut(){
        return tierLevel==5;
    }
    boolean isLowestTier(){
        return tierLevel==-1;
    }
    int costOfNextTier(){
        if(tierLevel+1==5)return hotelCost;
        else return houseCost;
    }
    int costOfPrevTier(){
        if(tierLevel-1==-1)return mortgageValue;
        else return houseCost/2;
    }
    boolean isMortgaged(){
        return tierLevel==-1;
    }
    boolean isOwnBy(int player){
        return player==playerId;
    }
    void buyHouse(ISP_Joshua j){    //In my words, move up a tier level
        if(j.ownsColourSet(propertyColour)&&
        !tierMaxedOut()&&
        j.getBalance()>costOfNextTier()&&
        j.canEvenBuild(propertyColour)){  
            //Added a check to see if the house bought is "level" with the others
            //This rule is called even build
            j.removeMoney(costOfNextTier());
            tierLevel++;
        }else{

        }
    }
    void sellHouse(ISP_Joshua j){   //Move down a tier, this should also work with mortgaging
        if(!isLowestTier()&&
        j.canEvenSell(propertyColour)){  
            //Added a check to see if the house bought is "level" with the others
            //This rule is called even build
            j.addMoney(costOfPrevTier());
            tierLevel--;
        }else{

        }
    }
}
/*
To future me, This should be done.
but idk everything is implemented correctly, or if there's any logic errors.
If there's anything that cant be fixed easily. Tear it down and rebuild it.
Formatting for the property cards are down below
You might need to add exceptions in order to account for invalid moves and operations
This interacts back and forth with the main class
God, I really hope i got this right on the first try
Next off, The chance card!
As to my contest checker, taking a break from that, need a better way to parse
*/
/*
name&group&houseCost&hotelCost&morgageValue&tierCost&propertyCost
Name of Property
Property group
Rent
Morgage Value
With one house:
With two house:
With three house:
With four house:
With Hotel
House cost
Hotel cost
*/