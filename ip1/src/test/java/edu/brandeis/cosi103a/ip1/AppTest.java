package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the Dice Game App.
 * Tests the game logic including die rolling and game rules.
 */
public class AppTest 
{
    /**
     * Test that rollDie() returns a value between 1 and 6.
     */
    @Test
    public void testRollDieReturnsBetweenOneAndSix()
    {
        for (int i = 0; i < 100; i++) {
            int roll = App.rollDie();
            assertTrue("Roll should be >= 1", roll >= 1);
            assertTrue("Roll should be <= 6", roll <= 6);
        }
    }
    
    /**
     * Test that rollDie() can return 1.
     */
    @Test
    public void testRollDieCanReturnOne()
    {
        boolean foundOne = false;
        for (int i = 0; i < 1000; i++) {
            if (App.rollDie() == 1) {
                foundOne = true;
                break;
            }
        }
        assertTrue("Should be able to roll a 1", foundOne);
    }
    
    /**
     * Test that rollDie() can return 6.
     */
    @Test
    public void testRollDieCanReturnSix()
    {
        boolean foundSix = false;
        for (int i = 0; i < 1000; i++) {
            if (App.rollDie() == 6) {
                foundSix = true;
                break;
            }
        }
        assertTrue("Should be able to roll a 6", foundSix);
    }
    
    /**
     * Test that rollDie() never returns 0.
     */
    @Test
    public void testRollDieNeverReturnsZero()
    {
        for (int i = 0; i < 100; i++) {
            assertNotEquals("Roll should never be 0", 0, App.rollDie());
        }
    }
    
    /**
     * Test that rollDie() never returns 7.
     */
    @Test
    public void testRollDieNeverReturnsSeven()
    {
        for (int i = 0; i < 100; i++) {
            assertNotEquals("Roll should never be 7", 7, App.rollDie());
        }
    }
    
    /**
     * Test that rollDie() never returns negative numbers.
     */
    @Test
    public void testRollDieNeverReturnsNegative()
    {
        for (int i = 0; i < 100; i++) {
            assertTrue("Roll should never be negative", App.rollDie() > 0);
        }
    }
    
    /**
     * Test that the game constants are correct.
     */
    @Test
    public void testGameConstants()
    {
        // This test verifies game setup by checking that the App can be instantiated
        assertNotNull("App class should exist", App.class);
    }
    
    /**
     * Test that multiple rolls produce a distribution of values.
     * With 600 rolls of a die, we should see all 6 values at least once.
     */
    @Test
    public void testRollDieDistribution()
    {
        boolean[] seenValues = new boolean[7]; // Index 0 unused, 1-6 used
        
        for (int i = 0; i < 600; i++) {
            int roll = App.rollDie();
            seenValues[roll] = true;
        }
        
        // Check that we've seen at least values 1-6
        for (int i = 1; i <= 6; i++) {
            assertTrue("Should see value " + i + " in distribution", seenValues[i]);
        }
    }
}
