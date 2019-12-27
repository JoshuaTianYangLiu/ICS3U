public class Utility implements Tile{
    String name;
    int cost;
    int unMortgage;
    int multiplier1,multiplier2;
    int playerId;
    Utility(String input){
        String portion[] = input.split("\\|");      // Added \\ as escape characters 
        playerId=0;
        name = portion[0];
        cost=Integer.parseInt(portion[1]);
        unMortgage=Integer.parseInt(portion[2]);
        multiplier1=Integer.parseInt(portion[3]);
        multiplier2=Integer.parseInt(portion[4]);
    }
    public void executeTile(ISP_Joshua j){
        //Check if they own it. If so, pay rent
        
        //Check 
    }
    public int getTileType(){
        return 10;
    }
    public String getInfo(){
        return name;
    }
}
/*
Cost&Mortgage&values
*/