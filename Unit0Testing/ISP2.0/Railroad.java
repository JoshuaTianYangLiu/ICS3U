public class Railroad implements Tile{
    int cost;
    int mortgageValue;
    int playerId;
    int rentCost[] = new int[5];
    Railroad(String input){
        String portions[] = input.split("&");
        playerId=0;
        cost=Integer.parseInt(portions[0]);
        mortgageValue=Integer.parseInt(portions[1]);
        for(int i=1; i<5; i++){
            rentCost[i]=Integer.parseInt(portions[i+1]);
        }
    }
    void buyProperty(ISP_Joshua j){
        if(playerId==0&j.getBalance()>cost){
            playerId=j.curPlayer;                           //curent player new owns the property
            j.removeMoney(cost);                    //Remove the money
        }else if (j.getBalance()<=cost){
            //Not enough money
        }else{
            //Plot owned, cannot buy
        }
    } 
    void mortgage(ISP_Joshua j){

    }
    void unmortgage(ISP_Joshua j){
        
    }
    void payRent(ISP_Joshua j){
        //Get number of railroads
    }
    public void executeTile(ISP_Joshua j){
        if(j.curPlayer!=playerId)payRent(j);
        //Ask for option
        int option=1;
        //Make sure to check if you can even perform that action
        if(option==1){  //Buy property
            buyProperty(j);
        }else if(option==2){    //Buy mortgaged property
            unmortgage(j);
        }else if(option==3){    //Mortgage property
            mortgage(j);
        }
    }
    public int getTileType(){
        return 3;
    }
    public String getInfo(){
        return name;    //Maybe need other info such of money and such.
    }
}
/*
Cost&mortgage&Rent&two&three&four
Cost
Rent
With two railroad own
With three railroad own
With four railroad own
Mortgage value
*/