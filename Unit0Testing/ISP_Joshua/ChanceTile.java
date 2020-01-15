public class ChanceTile implements Tile{
    ChanceTile(){

    }
    
    /** 
     * Name: executeTile
     * @param j
     * Executes chance tile
     */
    public void executeTile(ISP_Joshua j){
        //Run some j.___() so choose card
        j.pickChanceCard();
    }
    
    /** 
     * Name: getTileType
     * @return int
     * gets tile id 
     */
    public int getTileType(){
        return 4;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information
     */
    public String getInfo(){
        return "Chance Card";
    }
}