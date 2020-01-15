/**
 * This is an interface, OwnableTile is ment for tiles which can be owned by player
 * More specifically Property.java, Railroad.java and Utility.java
 */

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
    public int getMortgage();
    public int getUnMortgage();
}