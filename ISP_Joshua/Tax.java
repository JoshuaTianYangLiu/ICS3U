public class Tax implements Tile{
    String name;
    int cost;
    
    /**
     * Name: Tax
     * @param input
     * Parse and assigns values for tax
     */
    Tax(String input){
        String portions[] = input.split("\\|");
        name=portions[0];
        name=name.replace('-', '\n');
        cost=Integer.parseInt(portions[1]);
    }
    
    /** 
     * Name: executeTile 
     * @param j
     * performs execution when tile is land on
     */
    public void executeTile(ISP_Joshua j){
        j.payTax(cost);
    }
    
    /** 
     * Name: getTileType
     * @return int
     * returns tile id
     */
    public int getTileType(){
        return 6;
    }
    
    /** 
     * Name: getInfo
     * @return String
     * Get basic info
     */
    public String getInfo(){
        return name;
    }
}