import hsa.Console;
import java.awt.*;

public class DrawMortgage{
    Console c;
    Color background=new Color(255,209,111);
    Color text=new Color(0,0,0);
    Color notMortgage=new Color(33,108,42);
    Color isMortgaged=new Color(207,20,43);
    Font textTitle=new Font("Cambria",Font.PLAIN,26);
    Font textInfo = new Font ("Times New Roman", Font.PLAIN, 20);
    DrawMortgage(){
        c=new Console(30,80,"Mortgage/Unmortgage");
        //Load assets
    }
    //720x500
    void draw(ISP_Joshua j){
        int tilesOwned[] = new int[j.NUMBEROFTILES+1];
        int arrPointer=1;
        for(int i=1; i<=j.NUMBEROFTILES; i++){
            if(j.isOwnableTile(i)){
                OwnableTile t = (OwnableTile)j.monopolyTiles[i];
                if(t.getOwnerId()==j.curPlayer){
                    tilesOwned[arrPointer++]=i;
                }
            }
        }
        while(true){
            c.setColor(background);
            c.fillRect(0,0,720,600);
            c.setColor(text);
            c.setFont(textTitle);
            c.drawString("Name", 20,30);
            c.drawString("Mortgage Status",500,30);
            c.setFont(textInfo);
            drawList(tilesOwned, arrPointer, j);
            int choice;
            if(arrPointer==1){
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }else{
                choice = Util.queryInt("Please choose an option:\n"+
                                        "0: Close window\n"+
                                        "1-"+(arrPointer-1)+": Mortgage/Unmortgage property", "Please choose a valid option","Mortgage/Unmortgage",0,arrPointer-1);
            }
            if(choice==0)break;
            OwnableTile t = (OwnableTile)j.monopolyTiles[tilesOwned[choice]];
            if(t.isMortgaged())t.unMortgage(j);
            else t.mortgage(j);
        }
    }
    void drawList(int[] tilesOwned,int arrPointer,ISP_Joshua j){
        //arrPointer can be as large as 28
        //Console window should hold nearly 28.
        //TODO
        for(int i=1; i<arrPointer; i++){
            c.setColor(text);
            c.drawString(i+": "+j.monopolyTiles[tilesOwned[i]].getInfo(), 20, 25*i+40);
            OwnableTile t = (OwnableTile)j.monopolyTiles[tilesOwned[i]];
            if(t.isMortgaged()){
                c.setColor(isMortgaged);
                c.fillRect(450, 25*i+20,270, 25);
                c.setColor(text);
                c.drawString("Mortgaged",500,25*i+40);
            }
            else{
                c.setColor(notMortgage);
                c.fillRect(450, 25*i+20,270, 25);
                c.setColor(text);
                c.drawString("Not Mortgaged",500,25*i+40);
            }
        }
    }
    void close(){
        c.close();
    }
}