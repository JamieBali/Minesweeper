/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author unauthored
 */
public class version2Test {
    
    version2 v;
    
    public version2Test() {
        v = new version2(10,10,20);
    }
    
    @Test
    public void testBoardCreation(){
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                if (v.board[x][y] == null){fail("board not declared");}
            }
        }
    }
    
    @Test
    public void testPopulateBoard(){
        int counter = 0;
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                if (v.board[x][y].isPopulated()){counter++;}
            }
        }
        assertEquals(counter,v.numberOfMines);
    }
    
    @Test
    public void testNumbers(){
        int counter = 0;
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                counter = 0;
                if (x != 0){
                    if (v.board[x-1][y].isPopulated()){
                        counter++;
                    }
                    if (y != 0){
                        if (v.board[x-1][y-1].isPopulated()){
                            counter++;
                        }
                    }
                    if (y != v.verticalSize - 1){
                        if (v.board[x-1][y+1].isPopulated()){
                            counter++;
                        }
                    }
                }
                if (x!= v.horizontalSize - 1){
                    if (v.board[x+1][y].isPopulated()){
                        counter++;
                    }
                    if (y != 0){
                        if (v.board[x+1][y-1].isPopulated()){
                            counter++;
                        }
                    }
                    if (y != v.verticalSize - 1){
                        if (v.board[x+1][y+1].isPopulated()){
                            counter++;
                        }
                    }
                }
                if (y != 0)
                {
                    if (v.board[x][y-1].isPopulated()){
                        counter++;
                    }
                }
                if (y!= v.verticalSize - 1)
                {
                    if (v.board[x][y+1].isPopulated()){
                        counter++;
                    }
                }
                if (v.board[x][y].getNeighbours() != counter && !v.board[x][y].isPopulated()){
                    fail("error placing numbers");
                }
            }
        }
        v.displayBoard();
    }
    
    @Test
    public void testDisplayBoard(){
        try{
            v.displayBoard();
        }
        catch (Exception e){
            fail("could not display board");
        }
    }
    
    @Test
    public void testStep(){
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                if (v.board[x][y].isPopulated() && v.step(x,y)){ //if the board is populated then step will return false
                    fail("returned true on populated tile");
                }
                else if (!v.board[x][y].isPopulated() && !v.step(x,y)){ //if the board is unpopulated then step will return true
                    fail("returned false on unpopulated tile");
                }
            }
        }
    }
    
    @Test
    public void testMark(){
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                boolean temp = v.board[x][y].isMarked();
                v.board[x][y].mark();
                if (v.board[x][y].isMarked() == temp)  //tests that it marks correctly
                {
                    fail("Doesn't correctly mark tiles");
                }
                v.board[x][y].mark();
                if (v.board[x][y].isMarked() != temp) //test that it successfully unmarks
                {
                    fail("Doesn't correctly unmark tiles");
                }
            }
        }
    }
    
    @Test
    public void testAreAllMinesRevealed(){
        completeBoard();
        boolean funcResult = v.areAllMinesRevealed();
        boolean expected = true;
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                if (v.board[x][y].isPopulated() && !v.board[x][y].isMarked()){
                    expected = false;
                }
                if (!v.board[x][y].isPopulated() && v.board[x][y].isMarked()){
                    expected = false;
                }
            }
        }
        assertEquals(funcResult, expected);
    }
    
    public void completeBoard(){ //a debugging tool which will put the board in a winning state
        for (int x = 0; x < v.horizontalSize; x++){
            for (int y = 0; y < v.verticalSize; y++){
                if (v.board[x][y].isPopulated()){
                    v.board[x][y].mark();
                }
                else{
                    v.board[x][y].mine();
                }
            }
        }
    }
    
    /**
     * ADDITIONAL UNLOGGED TESTS:
     * 
     * -playtesting : You can definitely lose
     *                Winning the game is possible. The game allows you to win if all mines are marked, but not all tiles are revealed which is correct
     */

   
    
    
}
