/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author unauthored
 */
public class parserTest {
    
    public parserTest() {
    }
    
    parser p = new parser();
    String testString = "Step 17 24";
    
    @Test
    public void testGetCommand(){
        assertEquals("STEP", p.getCommandWord(testString));
    }
    
    @Test
    public void testGetY(){
        assertEquals(17, p.getX(testString));
    }
    
    @Test
    public void testGetX(){
        assertEquals(24, p.getY(testString));
    }
    
}
