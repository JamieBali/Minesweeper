package minesweeper;

/**
 * @version 2
 * @since 2
 * 
 * @author jb862
 * 
 * parser is a class that has the only function of processing input data and splitting it into the 3 needed substrings (the command word, x, and y values);
 */
public class parser {
    
    /**
     * converts command phrase into just the command word
     * @param inp command phrase
     * @return command word
     */
    public String getCommandWord(String inp){
        String split[] = inp.split(" ", 0);
        split[0] = split[0].toUpperCase();
        // System.out.println(split[0]); //for debugging purposes
        return split[0];
    }
    
    /**
     * converts command phrase into Y value
     * @param inp command phrase
     * @return Y value. Returns -1 if command phrase is invalid
     */
    public int getX(String inp){
        try {
        String split[] = inp.split(" ", 0);
        return Integer.parseInt(split[1]);
        }
        catch (Exception e){
            return -1;
        }
    }
    
    /**
     * converts command phrase into X value
     * @param inp command phrase
     * @return X value. Returns -1 if command phrase is invalid
     */
    public int getY(String inp){
        try {
        String split[] = inp.split(" ", 0);
        return Integer.parseInt(split[2]);
        }
        catch (Exception e){
            return -1;
        }
    }
    
    /**
     * converts metadata of GUI button into an int value
     * @param inp metadata of a button from the GUI
     * @return x value
     */
    public int parseX(Object inp){
        String temp = String.valueOf(inp);
        String split[] = temp.split(" ", 0);
        return Integer.parseInt(split[1]);
    }
    
    /**
     * converts metadata of GUI button into an int value
     * @param inp metadata of a button from the GUI
     * @return y value
     */
    public int parseY(Object inp){
        String temp = String.valueOf(inp);
        String split[] = temp.split(" ", 0);
        return Integer.parseInt(split[2]);
    }
}
