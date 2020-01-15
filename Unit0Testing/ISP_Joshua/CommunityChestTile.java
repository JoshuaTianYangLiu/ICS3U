public class CommunityChestTile implements Tile{
    CommunityChestTile(){

    }
    
    /** 
     * Name: executeTile
     * @param j
     * Executes instructions on tile
     */
    public void executeTile(ISP_Joshua j){
        //Same thing as ChanceTile
        j.pickCommunityChestCard();
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 5;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information
     */
    public String getInfo(){
        return "Community Chest";
    }
}