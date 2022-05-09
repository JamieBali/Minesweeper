package minesweeper;

/**
 * @author jb862
 * @version 2
 * @since 2
 * MineTile is the class that represents a tile in the board. This is to be declared in an array to represent a board.
 */
public class MineTile {
    
    /**
     * boolean determining whether a given tile has been mined by the user
     */
    private boolean mined;
    
    /**
     * boolean determining whether a given tile has a mine on it
     */
    private boolean populated;
    
    /**
     * boolean determining whether a given tile has been marked by the user
     */
    private boolean marked;
    
    /**
     * the number of mines neighbouring a given tile. 0 to 8.
     */
    private int neighbours;
    
    /**
     * unused constructor
     */
    public MineTile(){
        mined = false;
        marked = false;
        neighbours = 0;
        populated = false;
    }
    
    /**
     * resets the tile so the game can be repeated.
     * @param yn 
     */
    public void resetTile(boolean yn){
        mined = false;
        marked = false;
        if (yn){
            neighbours = 0;
            populated = false;
        }
    }
    
    /**
     * increments the neighbours int - called when there is a mine neighbouring the given tile
     */
    public void increment(){
        neighbours++;
    }
    
    /**
     * toggles whether or not the tile has been marked by the user so they are able to unmark a tile if they change their mind
     */
    public void mark(){
        marked = !marked;
    }
    
    /**
     * mines a tile, simply inverting a boolean
     */
    public void mine(){
        mined = true;
    }
    
    /**
     * places a mine on a given tile
     */
    public void populate(){
        populated = true;
    }
    
    /**
     * getter
     * 
     * @return boolean if the tile is populated
     */
    public boolean isPopulated(){
        return populated;
    }
    
    /**
     * getter
     * 
     * @return boolean if the tile is marked
     */
    public boolean isMarked(){
        return marked;
    }
    
    /**
     * getter
     * 
     * @return boolean if the tile is mined
     */
    public boolean isMined(){
        return mined;
    }
    
    /**
     * getter
     * 
     * @return int number of neighbours a tile has
     */
    public int getNeighbours(){
        return neighbours;
    }
    
    //options for return include:
    //number 1-8 if tile has been mined, doesn't contain a mine, and has neighbours
    //blank space if tile has been mined, doesn't contain a mine, and doesn't have any neighbours
    //astrix(*) if tile has been mined and there was a mine there
    //cover if tile hasn't been mined yet, regardless
    /**
     * returns a single character to print the state of the board
     * 
     * @return string consisting of a number, blank space, mine, or cover, dependant on what state the tile is in
     */
    @Override
    public String toString(){
        if(isMined()){
            if(isPopulated()){
                return "* "; //representing a mine. If this shows, the game has been lost.
            }
            else{
                return Integer.toString(neighbours) + " "; //since a blank space will make the board hard to read, the number 0 will be displayed if there is no mine in a revealed tile.
            }
        }
        else if (isMarked()){
                return "? ";
            }
        else
            return "# "; //representing unmined tile since ■ is uncomprehended by netBeans console. This will be changed to function better in future versions.
    }
    
//    @Override
//    public String toString(){ //debugging version that shows mine locations and numbers
//        if(isPopulated()){
//            return "*"; //representing a mine. If this shows, the game has been lost.
//        }
//        else{
//             return Integer.toString(neighbours); //since a blank space will make the board hard to read, the number 0 will be displayed if there is no mine in a revealed tile.
//        }
//    }
}
