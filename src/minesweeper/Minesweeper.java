package minesweeper;

import java.util.Scanner;

/**
 * @author jb862
 * @version 2
 * @since 1
 */
public class Minesweeper {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  //in which args 0 is horizontal size, 1 is vertical size and 2 is max number of mines.
        version2 v;
        parser p = new parser();
        System.out.println("begin"); //for debugging purposes
        try{
            v = new version2(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        catch(Exception e){
            v = new version2();
        }    
        //the game is then played here
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        while (!gameOver){
            String inp = scanner.nextLine();
            String command = p.getCommandWord(inp);
            int x = p.getX(inp);
            int y = p.getY(inp);
            switch (command){
                case "QUIT":
                    gameOver = true;
                    System.exit(0);
                    break;
                case "MARK":
                    if (x < v.horizontalSize && x >= 0 && y < v.verticalSize && y >= 0){
                        v.board[x][y].mark();
                        System.out.println("Debug code 3");
                    }
                    if (v.areAllMinesRevealed()){
                        gameOver = true;
                    }
                    v.displayBoard();
                    break;
                case "STEP":
                    if (x < v.horizontalSize && x >= 0 && y < v.verticalSize && y >= 0){
                        gameOver = !v.step(x,y);
                    }
                    if (v.areAllMinesRevealed()){
                        gameOver = true;
                    }
                    v.displayBoard();
                    break;
                case "HELP":
                    System.out.println("STEP [x] [y] : Step on a tile to reveal if it is safe or has a mine on it");
                    System.out.println("MARK [x] [y] : Mark a tile you believe to contain a mine");
                    System.out.println("x and y are board coordinates, starting at 0,0 in the top left");
                    System.out.println("QUIT : Exit the game");
                    break;
                default:
                    System.out.println("Command not recognised. type 'help' for command list");
                    break;
            }
            
        }
        if (v.areAllMinesRevealed()){
            System.out.println("You win!");
        }
        else
        {
            System.out.println("That was a mine :(");
        }
        System.out.println("Press any key to exit ... ");
        String inp = scanner.next(); 
        System.exit(0);
    }    
}
