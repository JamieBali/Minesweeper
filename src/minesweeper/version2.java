/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.Random;

/**
 * @author jb862
 * @version 3
 * @since 2
 */
public class version2 {
    /** 
     * main board. While the specification requested 2 separate arrays to hold 
     * mine location and mine counts, but since a location can only have 8 mines
     * around it, i will be using the number 9 to mark out a mine location.
     */
    MineTile[][] board;
     
    /**
     * a debugging tool. This should have been deleted before submission.
     */
    int recursionTracker;
    
    /**
     * width of board by default.
     */
    int horizontalSize = 10;
    
    /**
     * height of board by default.
     */
    int verticalSize = 10;
    
    /**
     * number of mines at start of game by default.
     */
    int numberOfMines = 20;
    
    /**
     * constructor
     * 
     * @param hor the horizontal size of the board to be passed
     * @param ver the vertical size of the board to be passed
     * @param max the number of mines to be placed on the board to be passed
     */ 
    public version2(int hor, int ver, int max){
        horizontalSize = hor;
        verticalSize = ver;
        numberOfMines = max;
        createBoard();
        populateBoard();
        displayBoard();
    } 
    
    /**
     * alternative constructor that uses the 10, 10, 20 default or whatever the defaults are changed to
     */
    public version2(){
        createBoard();
        populateBoard();
        displayBoard();
    }
    
    /**
     * initialises the board
     */
    public void createBoard(){
        board = new MineTile[horizontalSize][verticalSize];
        for (int i = 0; i < horizontalSize; i++){
            for (int j = 0; j < verticalSize; j++){
                board[i][j] = new MineTile();
            }
        }
    }
    
    /**
     * places mines onto the board and adds numbers to surrounding tiles
     */
    public void populateBoard(){
        if (numberOfMines < (horizontalSize * verticalSize)){
            Random rand = new Random();
            int Xcoord = 0;
            int Ycoord = 0;
            int itt = 0;
            while (itt < numberOfMines){
                Xcoord = rand.nextInt(horizontalSize);
                Ycoord = rand.nextInt(verticalSize);
                System.out.println(Xcoord + "and" + Ycoord); //for debugging purposes
                if (placeMine(Xcoord,Ycoord)){
                    itt++;
                }
            }
        }
        else{
            System.out.println("Too many mines for that size of board.");
        }
    }
   
    /**
     * attempts to place a mine and, if successful, increments neighbours
     * 
     * @param x coordinate to place mine at
     * @param y coordinate to place mine at
     * @return boolean whether mine was hit or not (false means a mine was hit)
     */
    public boolean placeMine(int x, int y){
        if (board[x][y].isPopulated())
        {
            return false;
        }
        else
        {   
            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    try{ //this means there is a tile here, so incrementing will function.
                        board[x+i][y+j].increment();
                    }
                    catch (Exception e){
                        //exception will only occur if there is no tile in the location being searched. This will happen on every edge case and the catch will stop any errors.
                    }
                }
            }
            board[x][y].populate();
            return true;
        }
    }
    
    /**
     * converts the integer board to a string to be printed.
     * 
     * @return str a string form of the board 
     */
    public String boardToString(){
        String str = "";
        for (int i = 0; i < horizontalSize; i++)
        {
            for (int j = 0; j < verticalSize; j++)
            {
                str = str + board[i][j].toString();
            }
            str = str + "\n";
        }
        return str;
    }
    
    /**
     * displays the board
     */
    public void displayBoard(){
        String str = boardToString();
        System.out.println(str);
    }
    
    /**
     * checks for the win condition
     * 
     * @return boolean if the player has won or not
     */
    public boolean areAllMinesRevealed(){
        boolean result = true;
        for (int x = 0; x < horizontalSize; x++){
            for (int y = 0; y < verticalSize; y++){
                if (board[x][y].isPopulated() && !board[x][y].isMarked()){
                    result = false;
                }
                if (!board[x][y].isPopulated() && board[x][y].isMarked()){
                    result = false;
                }
            }
        }
        return result;
    }
    
    /**
     * steps on a tile to reveal the correct tiles in response
     * @param x x coordinate of stepped tile
     * @param y y coordinate of stepped tile
     * @return bool if tile or mine hit.
     */
    public boolean step(int x, int y){
        if (board[x][y].isPopulated()) //tile is a mine
        {
            return false; 
        }
        else if (board[x][y].getNeighbours() > 0){ //tile has at least 1 mine next to it, so don't recurse from this point
            board[x][y].mine();
            return true;
        }
        else{
            board[x][y].mine();
            for (int i = -1; i < 2; i++){
                for (int j = -1; j < 2; j++){
                    try{
                        if (board[x+i][y+j].isMined()){
                            //do nothing here
                        }
                        else{
                            recursionTracker++;
                            System.out.println(recursionTracker);
                            step(x+i,y+j);
                        }
                    }
                    catch (Exception e){
                        //this exception should only occur if it tries to seek an out of bounds tile to step on
                    }
                }     
            }
            return true;
        }
    }
    
    /**
     * creates a link between the GUI and the board
     * @param i x coordinate
     * @param j y coordinate
     */
    public void mark(int i, int j){
        board[i][j].mark();
    }
    
    /**
     * creates a link between the GUI and the status of each tile on the board
     * @param i x coordinate
     * @param j y coordinate
     * @return integer value associated to a different possibility for the tiles status
     */
    public int getBoardStatus(int i, int j){
        if (board[i][j].isMarked()){
            return 10;
        }
        else if (board[i][j].isPopulated()){
            return 11;
        }
        else if (board[i][j].isMined()){
            return board[i][j].getNeighbours();
        }
        else{
            return 9;
        }
    }
    
    /**
     * resets all the tiles on the board to their defaults so the game can be repeated on the same board.
     */
    public void resetBoard(){
        for (int x = 0; x < horizontalSize; x++){
            for (int y = 0; y < verticalSize; y++){
                board[x][y].resetTile(false);
            }
        }
    }
    
    /**
     * resets the board to it's empty state so the game can be replayed on a new board. This also allows the board to be a new size.
     */
    public void newGame(){
        createBoard();
        for (int x = 0; x < horizontalSize; x++){
            for (int y = 0; y < verticalSize; y++){
                board[x][y].resetTile(true);
            }
        }
        populateBoard();
    }
    
    /**
     * allows the user to change the size of the board.
     * @param x horizontal (x) size
     * @param y vertical (y) size
     * @param bombs the number of mines wanted on the board
     */
    public void setValues(int x, int y, int bombs){
        horizontalSize = x;
        verticalSize = y;
        numberOfMines = bombs;
    }
}
