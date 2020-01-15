import hsa.Console;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class DrawGame{
    Console boardGame;
    BufferedImage boardImage,getOutOfJailImg;
    Font playerTitle=new Font("Cambria",Font.PLAIN,26);
    Font playerInfo=new Font("Cambria",Font.PLAIN,16);
    Color playerListBG=new Color(255,209,111);
    Color defaultBG=new Color(0,0,0);
    Color gainMoney= new Color(33,108,42);
    Color loseMoney=new Color(207,20,43);

    /**
     * Name: DrawGame
     * Loads images and any draws any starting images
     */
    DrawGame(ISP_Joshua j){
        boardGame=new Console(38,125,"Monopoly");
        try{
            boardImage=ImageIO.read(new File("assets\\Images\\MonopolyBoard.jpg"));
            //999x760
            getOutOfJailImg=ImageIO.read(new File("assets\\Images\\GetOutOfJail.png"));
        }catch(Exception e){
            boardGame.close();
            Util.exception("Missing MonopolyBoard.jpg or GetOutOfJail.png", e);
        }
        drawBoard();
        for(int i=1; i<j.numOfPlayers; i++){
            drawBottom(1,i,j);
        }
    }

    /**
     * Name: drawBoard
     * Draws board game
     */
    void drawBoard(){
        boardGame.drawImage(boardImage, 0, 0,null);
    }
    
    /** 
     * Name: drawRight
     * @param n
     * @param playerId
     * @param j
     * Draws player if on the right side of the board
     */
    void drawRight(int n,int playerId,ISP_Joshua j){
        //31-40
        n-=30;
        int xOffset=0,yOffset=0;
        if(playerId==2||playerId==4){
            yOffset=-30;
        }
        if(playerId==3||playerId==4){
            xOffset=30;
        }
        boardGame.drawImage(j.getMonopolyPiece(playerId,3), 685+xOffset, 90-20+(int)(560*(n-1)/9.0)+yOffset,null);
    }
    
    /** 
     * Name: drawTop
     * @param n
     * @param playerId
     * @param j
     * draws player if on the top of the board
     */
    void drawTop(int n,int playerId,ISP_Joshua j){
        //21-30
        n-=20;
        int xOffset=0,yOffset=0;
        if(playerId==2||playerId==4){
            xOffset=-30;
        }
        if(playerId==3||playerId==4){
            xOffset=-30;
        }
        boardGame.drawImage(j.getMonopolyPiece(playerId,2), 90-20+(int)(560*(n-1)/9.0)+xOffset, 75-20+yOffset,null);
    }
    
    /** 
     * Name drawLeft
     * @param n
     * @param playerId
     * @param j
     * Draws players if on left side of the board
     */
    void drawLeft(int n,int playerId,ISP_Joshua j){
        //11-20
        n-=10;
        int xOffset=0,yOffset=0;
        if(playerId==2||playerId==4){
            yOffset=30;
        }
        if(playerId==3||playerId==4){
            xOffset=-30;
        }
        boardGame.drawImage(j.getMonopolyPiece(playerId,1), 75-20+xOffset, 670-(int)(560*(n-1)/9.0)+yOffset,null);
    }
    
    /** 
     * Name: drawBottom
     * @param n
     * @param playerId
     * @param j
     * draws players if on bottom of the board
     */
    void drawBottom(int n,int playerId,ISP_Joshua j){
        //1-10
        int xOffset=0,yOffset=0;
        if(playerId==2||playerId==4){
            xOffset=30;
        }
        if(playerId==3||playerId==4){
            yOffset=30;
        }
        boardGame.drawImage(j.getMonopolyPiece(playerId,0),670-(int)(560*(n-1)/9.0)+xOffset,685+yOffset,null);   
    }
    
    /** 
     * Name: drawPiece
     * @param n
     * @param playerId
     * @param j
     * Draws the players on the appropriate side of the board
     */
    void drawPiece(int n,int playerId,ISP_Joshua j){
        if(n<11)drawBottom(n, playerId, j);
        else if(n<21)drawLeft(n,playerId,j);
        else if(n<31)drawTop(n, playerId, j);
        else if(n<41)drawRight(n, playerId, j);
    }
    
    /** 
     * Name: ownedBottom
     * @param n
     * @param j
     * Draws owned icon on the bottom side of the board
     */
    void ownedBottom(int n,ISP_Joshua j){
        if(j.tileOwnedBy(n)!=0){
            int id=j.tileOwnedBy(n);
            boardGame.drawImage(j.getMonopolyPiece(id,0),670-(int)(560*(n-1)/9.0)+20,685-25-20,null);
        }
    }
    
    /** 
     * Name: ownedLeft
     * @param n
     * @param j
     * Draws owned icon on the left side of the board
     */
    void ownedLeft(int n,ISP_Joshua j){
        if(j.tileOwnedBy(n)!=0){
            int id=j.tileOwnedBy(n);
            n-=10;
            boardGame.drawImage(j.getMonopolyPiece(id,1), 75+25, 670-(int)(560*(n-1)/9.0)+20,null);
        }
    }
    
    /** 
     * Name: ownedTop
     * @param n
     * @param j
     * Draws owned icon on the top side of the board
     */
    void ownedTop(int n,ISP_Joshua j){
        if(j.tileOwnedBy(n)!=0){
            int id=j.tileOwnedBy(n);
            n-=20;
            boardGame.drawImage(j.getMonopolyPiece(id,2), 90-20+(int)(560*(n-1)/9.0)-20, 75+25,null);
        }
    }
    
    /** 
     * Name: ownedRight
     * @param n
     * @param j
     * Draws owned icon on the right side of the board
     */
    void ownedRight(int n,ISP_Joshua j){
        if(j.tileOwnedBy(n)!=0){
            int id=j.tileOwnedBy(n);
            n-=30;
            boardGame.drawImage(j.getMonopolyPiece(id,3), 685-25-20, 90-20+(int)(560*(n-1)/9.0)-20,null);
        }
    }
    
    /** 
     * Name: drawOwnedTiles
     * @param j
     * draws owned tiles of the appropriate side
     */
    void drawOwnedTiles(ISP_Joshua j){
        for(int i=1; i<=j.NUMBEROFTILES; i++){
            if(i<11)ownedBottom(i,j);
            else if(i<21)ownedLeft(i,j);
            else if(i<31)ownedTop(i,j);
            else if(i<41)ownedRight(i,j);
        }
    }
    
    /** 
     * Name: modifyTwoBalance
     * @param amount
     * @param player1
     * @param player2
     * @param j
     * Animates the chance of the balance of two players (A transaction)
     */
    void modifyTwoBalance(int amount, int player1,int player2,ISP_Joshua j){
        drawOwnedTiles(j);
        drawPlayerList(j);
        boardGame.setFont(playerInfo);
        boardGame.setColor(loseMoney);
        boardGame.drawString("-"+amount, 900, 150*(player1-1)+90);
        boardGame.setColor(gainMoney);
        boardGame.drawString("+"+amount, 900, 150*(player2-1)+90);
        try{
            Thread.sleep(j.pauseTime);
        }catch(Exception e){
            boardGame.close();
            Util.exception("Thread Interrupted", e);
        }
        boardGame.setColor(defaultBG);
        drawPlayerList(j);
    }
    
    /** 
     * Name: modifyBalance
     * @param amount
     * @param playerId
     * @param j
     * Animates the modification of the balance of one player
     */
    void modifyBalance(int amount,int playerId,ISP_Joshua j){
        drawOwnedTiles(j);
        drawPlayerList(j);
        boardGame.setFont(playerInfo);
        //boardGame.drawString("$"+j.balance[i], 840, 150*(i-1)+90);
        if(amount<0){
            boardGame.setColor(loseMoney);
            boardGame.drawString(amount+"", 900, 150*(playerId-1)+90);
        }else{
            boardGame.setColor(gainMoney);
            boardGame.drawString("+"+amount, 900, 150*(playerId-1)+90);
        }
        try{
            Thread.sleep(j.pauseTime);
        }catch(Exception e){
            boardGame.close();
            Util.exception("Thread Interrupted", e);
        }
        boardGame.setColor(defaultBG);
        drawPlayerList(j);
    }
    
    /** 
     * Name: modifyBalance
     * @param amount
     * @param j
     * Modifies the balance of current player
     */
    void modifyBalance(int amount,ISP_Joshua j){
        modifyBalance(amount, j.curPlayer,j);
    }

    
    /** 
     * Name: moveBack
     * @param pos
     * @param newPos
     * @param j
     * Animates the player moving pack n spaces
     */
    void moveBack(int pos,int newPos,ISP_Joshua j){
        while(pos>newPos){
            try{
                Thread.sleep(j.pauseTime);
            }catch(Exception e){
                boardGame.close();
                Util.exception("Thread Interrupted", e);
            }
            pos--;
            drawScreenExcCurPlayer(j);
            drawPiece(pos,j.curPlayer,j);
        }
    }
    
    /** 
     * Name: moveFromTo
     * @param pos
     * @param newPos
     * @param j
     * Animates the player moving from one position to another
     */
    void moveFromTo(int pos,int newPos,ISP_Joshua j){
        while(pos!=newPos){
            try{
                Thread.sleep(j.pauseTime);
            }catch(Exception e){
                boardGame.close();
                Util.exception("Thread Interrupted", e);
            }
            pos++;
            if(pos>j.NUMBEROFTILES){
                pos-=j.NUMBEROFTILES;
            }
            drawScreenExcCurPlayer(j);
            drawPiece(pos,j.curPlayer,j);
        }
    }
    
    /** 
     * Name: moveToJail
     * @param newPos
     * @param j
     * Animates the player moving to jail
     */
    void moveToJail(int newPos,ISP_Joshua j){
        drawScreenExcCurPlayer(j);
        drawPiece(j.getPosOfCurPlayer(),j.curPlayer, j);
    }
    
    /** 
     * Name: drawScreenExcCurPlayer
     * @param j
     * Draws every player's position excluding the current player
     */
    void drawScreenExcCurPlayer(ISP_Joshua j){
        drawBoard();
        drawOwnedTiles(j);
        drawPlayerList(j);
        for(int i=1; i<j.numOfPlayers; i++){
            if(i!=j.curPlayer){
                drawPiece(j.positionOfPlayers[i],i,j);
            }
        }
    }
    
    /** 
     * Name: drawPlayerList
     * @param j
     * Draws the sidebar of list of players and other information
     */
    void drawPlayerList(ISP_Joshua j){
        boardGame.setColor(playerListBG);
        boardGame.fillRect(760,0,500,1000);
        boardGame.setColor(defaultBG);
        for(int i=1; i<j.numOfPlayers; i++){
            BufferedImage icon = resize(j.monopolyPieces[i][0],3,3);
            boardGame.drawImage(icon, 780,150*(i-1)+80, null);
            boardGame.setFont(playerTitle);
            if(i==j.curPlayer){
                boardGame.drawString(j.nameOfPlayer[i]+" <-", 780, 150*(i-1)+50);
            }else{
                boardGame.drawString(j.nameOfPlayer[i], 780, 150*(i-1)+50);
            }
            boardGame.setFont(playerInfo);
            boardGame.drawString("$"+j.balance[i], 840, 150*(i-1)+90);
            if(j.hasGetOutOfJail[i]){
                drawGetOutOfJail(i);
            }
        }
    }
    
    /** 
     * Name: resize
     * @param img
     * @param scaleW
     * @param scaleH
     * @return BufferedImage
     * Resizes images with a scale factor
     */
    //Player section: at x=760
    //Corners ~100x100
    //Edges ~60x75
    //Tiles 20x20
    //Taken from https://memorynotfound.com/java-resize-image-fixed-width-height-example/
    BufferedImage resize(BufferedImage img,int scaleW,int scaleH){
        int width = img.getWidth()*scaleW;
        int height = img.getHeight()*scaleH;
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resize = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resize.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resize;
    }
    
    /** 
     * Name: drawGetOutOfJail
     * @param playedId
     * draws get out of jail free card
     */
    void drawGetOutOfJail(int playedId){
        boardGame.drawImage(getOutOfJailImg, 860, 150*(playedId-1)+100,null);
    }
    
    /** 
     * Name: hideGetOutOfJail
     * @param playedId
     * hides get out of jail free card
     */
    void hideGetOutOfJail(int playedId){
        boardGame.setColor(playerListBG);
        boardGame.fillRect(860, 150*(playedId-1)+100,100, 60);
        boardGame.setColor(defaultBG);
    }

    /**
     * Name: close
     * closes window
     */
    void close(){
        boardGame.close();
    }
}