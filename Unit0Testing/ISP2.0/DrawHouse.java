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
    DrawHouse(){
        c=new Console(30,90,"Buy/Sell Houses");
        //Load assets
    }
    //720x500
    void draw(ISP_Joshua j){
        int propertiesOwned[] = new int[j.NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=j.NUMBEROFTILES; i++){
            if(j.monopolyTiles[i].getTileType()==2){
                OwnableTile t = (OwnableTile)j.monopolyTiles[i];
                if(t.getOwnerId()==j.curPlayer){
                    propertiesOwned[arrPointer++]=i;
                }
            }
        }
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
            if(arrPointer==1){
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
    void drawList(int[] propertiesOwned,int arrPointer,ISP_Joshua j){
        //arrPointer can be as large as 22
        //Console window should hold nearly 22.
        //TODO
        c.setColor(text);
        for(int i=1; i<arrPointer; i++){
            //List with color TODO
            c.drawString(i+": "+j.monopolyTiles[propertiesOwned[i]].getInfo(), 20, 25*i+40);
            Property t = (Property)j.monopolyTiles[propertiesOwned[i]];
            c.drawString(t.getColourName(), 300, 25*i+40);
            c.drawString(t.getNumOfHouses()+" houses, "+t.getNumOfHotels()+" hotels",500,25*i+40);
        }
    }
    void close(){
        c.close();
    }
}
/*
Console houses = new Console("Buy/Sell Houses");
        int propertiesOwned[] = new int[NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=NUMBEROFTILES; i++){
            if(monopolyTiles[i].getTileType()==2){
                OwnableTile t = (OwnableTile)monopolyTiles[i];
                if(t.getOwnerId()==curPlayer){
                    propertiesOwned[arrPointer++]=i;
                }
            }
        }
        while(true){
            houses.clear();
            houses.println(nameOfPlayer[curPlayer]+" \'s balance: $"+balance[curPlayer]);
            houses.print("Name",30);
            houses.print("Is mortgaged",15);
            houses.print("Houses Owned",12);
            houses.println("Hotels Owned",12);
            houses.println("----------------------------------------------------");
            for(int i=1; i<arrPointer; i++){
                houses.print(i+": "+monopolyTiles[propertiesOwned[i]].getInfo(),30);
                Property t = (Property)monopolyTiles[propertiesOwned[i]];
                if(t.isMortgaged())houses.print("Mortgaged",15);
                else houses.print("Not Mortgaged",15);
                houses.print(t.getNumOfHouses(),12);
                houses.println(t.getNumOfHotels(),12);
            }
            int choice;
            if(arrPointer==1){
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window", "Please choose a valid option","Buy/Sell",0,arrPointer-1);
            }else{
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window\n"+
                                        "1-"+(arrPointer-1)+": Buy/Sell Houses", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }
            if(choice==0)break;
            Property p = (Property)monopolyTiles[propertiesOwned[choice]];
            int buyOrSell = Util.optionDialog(p.getFullInfo(),p.getInfo(), new String[]{"Buy","Sell"});
            if(buyOrSell==0){   //Buy
                p.buyHouse(this);
            }else{  //Sell
                p.sellHouse(this);
            }
        }
        houses.close();*/