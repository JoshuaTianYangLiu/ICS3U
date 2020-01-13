public class FreeParking implements Tile{
    FreeParking(){

    }
    
    /** 
     * Name: executeTile
     * @param j
     * executes tile when land on (Collects tax)
     */
    public void executeTile(ISP_Joshua j){
        //Remove money from bank
        //Put into player
        if(j.hasFreeParking) j.collectTax();
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 7;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * returns basic information of tile
     */
    public String getInfo(){
        return "Free Parking";
    }
}