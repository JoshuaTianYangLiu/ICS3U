public class Go implements Tile{
    Go(){

    }
    
    /** 
     * Name: executeTile
     * @param j
     * Nothing needed ISP_Joshua handles GO
     */
    public void executeTile(ISP_Joshua j){
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 1;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information
     */
    public String getInfo(){
        return "Go";
    }
}