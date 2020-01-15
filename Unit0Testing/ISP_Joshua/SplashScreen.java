import hsa.Console;

public class SplashScreen extends Thread{
    Console c;
    int x,y;
    int waitTime[] = new int[]{100,150,250,500,1000,1500};
    SplashScreen(Console c,int col,int row){
        this.c=c;
        this.x=20*col;
        this.y=20*row;
    }

    /**
     * Name: rollDice
     * Roll dice, and draw
     */
    public void rollDice(){
        for(int i=0; i<waitTime.length; i++){
            DrawDice.drawDice(random(1,6), x, y);
            try{
                Thread.sleep(waitTime[i]);
            }catch(Exception e){}
        }
    }
    
    /** 
     * Name random
     * @param a
     * @param b
     * @return int
     * returns an int between a-b inclusive
     */
    public int random(int a,int b){
        return (int)((b-a+1)*Math.random()+1);
    }
    /**
     * Name: run
     * thread method
     */
    public void run(){
        rollDice();
    }
}