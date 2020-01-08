interface OwnableTile{
    public static final int NOTOWNED=0;
    public void mortgage(ISP_Joshua j);
    public void unMortgage(ISP_Joshua j);
    public void tranferOwnership(int toPlayer);
    public int getOwnerId();
    public String getFullInfo();
    public void buyProperty(ISP_Joshua j,int amount,int playerId);
    public boolean isMortgaged();
    public void reset();
    //Is this needed? I dont think so
}