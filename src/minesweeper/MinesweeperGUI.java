package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javax.swing.*;
import javafx.stage.Window;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * @author jb862
 * @version 3
 * @since 3
 */
public class MinesweeperGUI extends Application {
    
   //Links to required classes
    version2 v;
    parser p = new parser();
    
    //Board dimensions and bomb counts
    int x = 15;
    int y = 10;
    int bombs = 20;
    
    /**
     * array holding pushable buttons
     */
    Button[][] btn;
    
    //Holding variables
    boolean running = true; 
    String currentDifficulty = "easy";
    
    //Loads all of the required image files
    Image OneNeighbour = new Image("file:1.png",30,30,true,true);
    Image TwoNeighbours = new Image("file:2.png",30,30,true,true);
    Image ThreeNeighbours = new Image("file:3.png",30,30,true,true);
    Image FourNeighbours = new Image("file:4.png",30,30,true,true);
    Image FiveNeighbours = new Image("file:5.png",30,30,true,true);
    Image SixNeighbours = new Image("file:6.png",30,30,true,true);
    Image SevenNeighbours = new Image("file:7.png",30,30,true,true);
    Image EightNeighbours = new Image("file:8.png",30,30,true,true);
    Image Mine = new Image("file:bomb.png",30,30,true,true);
    Image Flag = new Image("file:flag.png",30,30,true,true);
    Image Empty = new Image("file:empty.png",30,30,true,true);
    Image Default = new Image("file:default.png",30,30,true,true);
    
    //Display variables
    GridPane minefield;
    Scene scene;
    GridPane funcs;
        
    @Override
    public void start(Stage primaryStage) {
        displayBoard(primaryStage);
    }

    /**
     * main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * creates and displays the stage. This is a separate method to the start method so it can be more easily repeated when needed
     * @param primaryStage the stage to be displayed
     */
    public void displayBoard(Stage primaryStage){
        running = true;                     //So buttons can be pressed
        v = new version2(x,y,bombs);        //constructor
        btn = new Button[x][y];             //creates array

        initBtnArray();                     //initialises array
                
        minefield = new GridPane();
        
        for(int i = 0; i < x; i++) {
           for (int j = 0; j < y; j++){
                minefield.add(btn[i][j],i,j);        //adds buttons to gridpane
           }
        }
        
        //aesthetics
        minefield.setAlignment(Pos.CENTER);
        minefield.setPadding(new Insets(10,0,0,0));
        
        /**
         * 4 buttons along the bottom of the window to reset, start new game, change difficulty, and quit.
         * All done with separate lambda expressions.
         */
        Button[] funcsBtn = new Button[4];
        funcsBtn[0] = new Button("Reset Grid");
        funcsBtn[0].setOnMouseClicked(event ->
        {
            v.resetBoard();
            updateBoardDisplay();
            running = true;             //in case teh game is lost and someone is resetting to play the same board
        });
        funcsBtn[1] = new Button("New Game");
        funcsBtn[1].setOnMouseClicked(event ->
        {
            v.newGame();
            running = true;             //ordering is important here so the bombs don't display upon creating a new game
            updateBoardDisplay();
        });
        funcsBtn[2] = new Button("Difficulty");
        funcsBtn[2].setOnMouseClicked(event ->
        {
            String[] choices = {"easy", "medium", "hard", "expert"};
            String input = (String) JOptionPane.showInputDialog(null,
                "Select Diffculty:",
                "Difficulty", 
                JOptionPane.QUESTION_MESSAGE, 
                null,
                choices, 
                currentDifficulty
            );
            currentDifficulty = input;
            
            //System.out.println(currentDifficulty); //for development purposes
            
            switch(currentDifficulty){
                case "easy":
                    x = 15;
                    y = 10;
                    bombs = 20;
                    break;
                case "medium":
                    x = 20;
                    y = 15;
                    bombs = 55;
                    break;
                case "hard":
                    x = 30;
                    y = 16;
                    bombs = 99;
                    break;
                case "expert":
                    x = 40;
                    y = 20;
                    bombs = 220;
                    break;
            }
            
            v.setValues(x,y,bombs);
            v.newGame();
            displayBoard(primaryStage); //This is technically a recursion and could cause issues in the long run, but the chances of someone repeating the game enough times to crash this is so negligable i believe this is a usable technique
        });
        funcsBtn[3] = new Button("Quit Game");
        funcsBtn[3].setOnMouseClicked(event ->
        {
            System.exit(0); //exit with no error message
        });
        
        //cerates gridpane of these 4 new buttons
        funcs = new GridPane();
        for (int i = 0; i < 4; i++){
            funcsBtn[i].setPrefSize(100,20);
            funcsBtn[i].setMinWidth(100);
            funcs.add(funcsBtn[i],i,0);
        }
        
        //aesthetics
        funcs.setAlignment(Pos.BOTTOM_CENTER);
        funcs.setPadding(new Insets(0,100,50,100));
        funcs.setHgap(50);
        
        //"scene" is the window iteself. It's dynamically created so the user won't need to resize the window manually if the board is too big for the current window.
        scene = new Scene(generateBorderPane(), (40*x)+200, (40*y) +150);
        
        //generates and shows board
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //lambda expressions for each of the buttons. Systematically generated for efficiency
        for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++){
                final Button myButton = btn[i][j];
                btn[i][j].setOnMouseClicked(event ->
                {
                    if (running){
                        if (event.getButton() == MouseButton.PRIMARY){
                            String temp = String.valueOf(myButton.getUserData());
                            int cx = p.getX(temp);
                            int cy = p.getY(temp);
                            //System.out.println("mined : " + cx + " " + cy);
                            boolean hit = v.step(cx,cy);
                            if (!hit) lose();
                            else winCheck();
                            updateBoardDisplay();
                    }
                        else if (event.getButton() == MouseButton.SECONDARY){
                            String temp = String.valueOf(myButton.getUserData());
                            int cx = p.getX(temp);
                            int cy = p.getY(temp);
                            //System.out.println("marked : " + cx + " " + cy);
                            v.mark(cx,cy);
                            winCheck();
                            updateBoardDisplay();
                        }
                    }
                });
            }
        }
        
        //due to a graphical issue regarding the 4 funcs buttons, the smallest size the board can be is (40*15)+200 pixels wide. Height doesn't cause this issue.        
        if (x < 15){
            changeStageSize(scene.getWindow(), (40*15)+200, (40*y)+150);
        }
        else{
            changeStageSize(scene.getWindow(), (40*x)+200, (40*y)+150);
        }
    }
    
    /**
     * initialises all the buttons
     */
    public void initBtnArray(){
        for(int i = 0; i < x; i++) {
           for (int j = 0; j < y; j++){
               btn[i][j] = new Button();
               btn[i][j].setGraphic(new ImageView(Default));
               btn[i][j].setUserData("btn " + i + " " + j);
               btn[i][j].setMaxWidth(40);
               btn[i][j].setMinWidth(40);
               btn[i][j].setMaxHeight(40);
               btn[i][j].setMinHeight(40);
           }
        }
    }
    
    
    /**
     * when the user clicks a mine
     */
    public void lose(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Uh oh!");
        dialog.setContentText("BOOM! You lose!");
        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.showAndWait();
        running = false;
    }
    
    /**
     * performs a check to see if the user has won the game and, if so, displays a congratulations and stops the board from being clicked further 
     */
    public void winCheck(){
       if (v.areAllMinesRevealed()){
           Dialog<String> dialog = new Dialog<>();
           dialog.setTitle("Congratulations");
           dialog.setContentText("All mines successfully cleared!");
           ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
           dialog.getDialogPane().getButtonTypes().add(okButton);
           dialog.showAndWait();
           running = false;
       }
    }
    
    /**
     * after every click, updates the board to accurately display the current state.
     */
    public void updateBoardDisplay(){
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                int temp = v.getBoardStatus(i,j); // 0 = blank; 1 > 8 = neighbours; 9 = unrevealed; 10 = flag; 11 = mine;
                //System.out.println(temp);
                switch(temp){
                    case 0:
                        btn[i][j].setGraphic(new ImageView(Empty));
                        break;
                    case 1:
                        btn[i][j].setGraphic(new ImageView(OneNeighbour));
                        break;
                    case 2:
                        btn[i][j].setGraphic(new ImageView(TwoNeighbours));
                        break;
                    case 3:
                        btn[i][j].setGraphic(new ImageView(ThreeNeighbours));
                        break;
                    case 4:
                        btn[i][j].setGraphic(new ImageView(FourNeighbours));
                        break;
                    case 5:
                        btn[i][j].setGraphic(new ImageView(FiveNeighbours));
                        break;
                    case 6:
                        btn[i][j].setGraphic(new ImageView(SixNeighbours));
                        break;
                    case 7:
                        btn[i][j].setGraphic(new ImageView(SevenNeighbours));
                        break;
                    case 8:
                        btn[i][j].setGraphic(new ImageView(EightNeighbours));
                        break;
                    case 9:
                        btn[i][j].setGraphic(new ImageView(Default));
                        break;
                    case 10:
                        btn[i][j].setGraphic(new ImageView(Flag));
                        break;
                    case 11:
                        if (running){
                            btn[i][j].setGraphic(new ImageView(Default));
                        }
                        else btn[i][j].setGraphic(new ImageView(Mine));
                }
            }
        }
    }
    
    //creates a border pane so the window is correctly layed out
    public BorderPane generateBorderPane(){
        BorderPane root = new BorderPane();
        root.setBottom(funcs);
        root.setCenter(minefield);
        return root;
    }
    
    /**
     * changes the size of the window
     * @param stage window to change size of
     * @param width new width
     * @param height new height
     */
    public void changeStageSize(Window stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }
}
