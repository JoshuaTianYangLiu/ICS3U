/**
 * This class is for any tiles that belong in the game of Monopoly
 * Use for simplification of code without many many if statements each time to separate which tile is which
 */

interface Tile{
    public void executeTile(ISP_Joshua j);
    public int getTileType();
    public String getInfo();
}