import hsa.Console;
import java.awt.*;

public class DrawHouse{
    Console c;
    Color background=new Color(255,209,111);
    Color text=new Color(0,0,0);
    Color notMortgage=new Color(33,108,42);
    Color isMortgaged=new Color(207,20,43);
    Font textTitle=new Font("Cambria",Font.PLAIN,26);
    Font textInfo = new Font ("Times New Roman", Font.PLAIN, 20);

    /**
     * Name: DrawHouse
     * Creates window 
     */
    DrawHouse(){
        c=new Console(30,90,"Buy/Sell Houses");
        //Load assets
    }
    
    /** 
     * Name: draw
     * @param j
     * Allows the user to see the current status of each property and can choose the buy or sell houses
     */
    //720x500
    void draw(ISP_Joshua j){
        //Get list of properties own by the current player
        int propertiesOwned[] = new int[j.NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=j.NUMBEROFTILES; i++){
            if(j.monopolyTiles[i].getTileType()==2){    //Is property
                OwnableTile t = (OwnableTile)j.monopolyTiles[i];
                if(t.getOwnerId()==j.curPlayer){    //Owned by current player
                    propertiesOwned[arrPointer++]=i;
                }
            }
        }

        //Begins user input to buy/sell houses 
        while(true){
            c.setColor(background);
            c.fillRect(0,0,720,600);
            c.setColor(text);
            c.setFont(textTitle);
            c.drawString("Name", 20,30);
            c.drawString("# of houses/hotels",500,30);
            c.setFont(textInfo);
            drawList(propertiesOwned, arrPointer, j);
            int choice;
            if(arrPointer==1){  //No properties owned
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window", "Please choose a valid option","Buy/Sell",0,arrPointer-1);
            }else{
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window\n"+
                                        "1-"+(arrPointer-1)+": Buy/Sell Houses", "Please choose a valid option","Buy/Sell",0,arrPointer-1);
            }
            if(choice==0)break;
            Property p = (Property)j.monopolyTiles[propertiesOwned[choice]];
            int buyOrSell = Util.optionDialog(p.getFullInfo(),p.getInfo(), new String[]{"Buy","Sell"});
            if(buyOrSell==0){   //Buy
                p.buyHouse(j);
            }else{  //Sell
                p.sellHouse(j);
            }
        }
    }
    
    /** 
     * Name: drawList
     * @param propertiesOwned
     * @param arrPointer
     * @param j
     * Draws list of properties owned by the player
     */
    void drawList(int[] propertiesOwned,int arrPointer,ISP_Joshua j){
        c.setColor(text);
        for(int i=1; i<arrPointer; i++){    //Loop through properties owned
            c.drawString(i+": "+j.monopolyTiles[propertiesOwned[i]].getInfo(), 20, 25*i+40);
            Property t = (Property)j.monopolyTiles[propertiesOwned[i]];
            c.drawString(t.getColourName(), 300, 25*i+40);
            c.drawString(t.getNumOfHouses()+" houses, "+t.getNumOfHotels()+" hotels",500,25*i+40);
        }
    }

    /**
     * Name: close
     * close window
     */
    void close(){
        c.close();
    }
}