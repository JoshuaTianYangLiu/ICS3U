public class GoToJail implements Tile{
    
    /** 
     * Name: executeTile
     * @param j
     * executes instructions of the GoToJail tile
     */
    public void executeTile(ISP_Joshua j){
        // j.goToJail();
        //The method should set some sort of status so we know what to do during their turns
        j.sendToJail();
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 8;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic info
     */
    public String getInfo(){
        return "Go To Jail";
    }
}