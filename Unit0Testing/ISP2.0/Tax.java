public class Tax implements Tile{
    String name;
    int cost;
    Tax(String input){
        String portions[] = input.split("\\|");
        name=portions[0];
        cost=Integer.parseInt(portions[1]);
    }
    public void executeTile(ISP_Joshua j){
        //Remove money from player
        //Put into bank
        j.payTax(cost);
    }
    public int getTileType(){
        return 6;
    }
    public String getInfo(){
        return "Tax: Pay $200";
    }
}