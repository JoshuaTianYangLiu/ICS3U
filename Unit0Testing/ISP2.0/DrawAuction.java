import hsa.Console;
import java.awt.*;

public class DrawAuction{
    Console c;
    Color background=new Color(255,209,111);
    Color text=new Color(0,0,0);
    Color notMortgage=new Color(33,108,42);
    Color isMortgaged=new Color(207,20,43);
    Font textTitle=new Font("Cambria",Font.PLAIN,26);
    Font textInfo = new Font ("Times New Roman", Font.PLAIN, 20);
    DrawAuction(){
        c=new Console(30,90,"Trade");
        c.setColor(background);
        c.fillRect(0,0,720,600);
    }
    void draw(ISP_Joshua j){
        String output="";
        int cnt=1;
        int choiceToId[] = new int[j.numOfPlayers];
        for(int i=1; i<j.numOfPlayers; i++){
            if(i!=j.curPlayer){
                choiceToId[cnt]=i;
                output+=(cnt++)+j.nameOfPlayer[i]+'\n';
            }
        }
        //Choose who to trade with
        while(true){
            int choice = Util.queryInt("Please choose an option\n"+
                                        "0: Back to auction\n"+
                                        output,"Please choose an option","Trade",0,cnt-1);
            //Show inventories of both players and status of each mortgaged/unmortgaged
            if(choice==0)return;
            drawList(choiceToId[choice],j);
        }
        //Error trap for ones which have houses
    }
    void drawList(int id,ISP_Joshua j){
        c.setColor(background);
        c.fillRect(0,0,720,600);
        c.setColor(text);
        c.setFont(textTitle);
        c.drawString("Name", 20,30);
        c.drawString(j.nameOfPlayer[id],300,30);
        int propertiesOwned[] = new int[j.NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=j.NUMBEROFTILES; i++){
            if(j.isOwnableTile(i)){
                OwnableTile t = (OwnableTile)j.monopolyTiles[i];
                if(t.getOwnerId()==id){
                    propertiesOwned[arrPointer++]=i;
                }
            }
        }
        c.setColor(text);
        for(int i=1; i<arrPointer; i++){
            c.drawString(i+": "+j.monopolyTiles[propertiesOwned[i]].getInfo(), 20, 25*i+40);
            Property t = (Property)j.monopolyTiles[propertiesOwned[i]];
            c.drawString(t.getColourName(), 300, 25*i+40);
            c.drawString(t.getNumOfHouses()+" houses, "+t.getNumOfHotels()+" hotels",500,25*i+40);
        }
        //List those currently selected
        int choice=Util.queryInt("Please choose an option\n"+
                                "0: Cancel trade\n"+
                                "1-"+(arrPointer-1)+": Select/Deselect property\n"+
                                arrPointer+": Trade money","Please select an option","Trade",0,arrPointer);
        //Use another array to keep track of what each are trading
        boolean trading[][] = new boolean[j.NUMBEROFTILES][2];
        int amount[]=new int[2];
        if(choice==0)return;
        if(choice==arrPointer){
            int balance = j.getBalance();
            choice = Util.queryInt("Current balance: "+balance+"\n"+
                                    "Please specify the amount of money you want to trade.","Please choose between 0 and "+balance,"Trade",0,balance);
            amount[0]=choice;
        }else{
            trading[propertiesOwned[choice]][0]=!trading[propertiesOwned[choice]][0];
        }
    }
    void close(){
        c.close();
    }
}