interface OwnableTile{
    public static final int NOTOWNED=0;
    public void mortgage();
    public void unMortgage();
    public void tranferOwnership(int toPlayer);
    public int getOwnerId();
    public String getFullInfo();
    public void buyProperty(ISP_Joshua j,int amount,int playerId);
    //Is this needed? I dont think so
}