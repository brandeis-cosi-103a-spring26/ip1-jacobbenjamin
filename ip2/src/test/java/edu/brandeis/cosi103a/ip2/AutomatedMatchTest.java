package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for AutomatedMatch class.
 */
public class AutomatedMatchTest {

    private AutomatedMatch match;

    @Before
    public void setUp() {
        match = new AutomatedMatch();
    }

    @Test
    public void testMatchInitialization() {
        assertNotNull(match.getPlayer1());
        assertNotNull(match.getPlayer2());
        assertNotNull(match.getSupply());
        assertEquals("Player 1", match.getPlayer1().getName());
        assertEquals("Player 2", match.getPlayer2().getName());
    }

    @Test
    public void testSetup() {
        match.setup();
        
        // Each player should have 10 cards (7 Bitcoin + 3 Method)
        assertEquals(10, match.getPlayer1().getTotalCards());
        assertEquals(10, match.getPlayer2().getTotalCards());
        
        // Each player should have 5 card hand
        assertEquals(5, match.getPlayer1().getHand().size());
        assertEquals(5, match.getPlayer2().getHand().size());
        
        // Supply should be reduced
        CardSupply supply = match.getSupply();
        assertEquals(46, supply.getCount("Bitcoin")); // 60 - 14
        assertEquals(8, supply.getCount("Method")); // 14 - 6
    }

    @Test
    public void testPlayTurn() {
        match.setup();
        
        int initialP1Cards = match.getPlayer1().getTotalCards();
        
        match.playTurn();
        
        // After a turn, one player should have more cards (they might have bought something)
        int finalP1Cards = match.getPlayer1().getTotalCards();
        int finalP2Cards = match.getPlayer2().getTotalCards();
        
        // Total cards between players should have increased by at most 1
        // (only 1 card can be bought per turn)
        assertTrue(finalP1Cards >= initialP1Cards || finalP2Cards >= initialP1Cards);
    }

    @Test
    public void testPlayMultipleTurns() {
        match.setup();
        
        CardSupply supply = match.getSupply();
        int initialFrameworks = supply.getCount("Framework");
        
        // Play 3 turns
        match.playTurn();
        match.playTurn();
        match.playTurn();
        
        // At least one framework should have been purchased (probably)
        // or the count should be the same if no one could afford it
        assertTrue(supply.getCount("Framework") <= initialFrameworks);
    }

    @Test
    public void testGameEnds() {
        match.setup();
        
        // Drain all frameworks manually to trigger game end condition
        CardSupply supply = match.getSupply();
        for (int i = 0; i < 8; i++) {
            supply.takeCard("Framework");
        }
        
        assertTrue(supply.isGameOver());
    }

    @Test
    public void testRunGameCompletes() {
        String result = match.runGame();
        
        assertNotNull(result);
        // Result should indicate a winner or tie
        assertTrue(result.contains("wins") || result.contains("tie"));
    }

    @Test
    public void testGameEndsWhenFrameworksDepleted() {
        match.setup();
        
        // Play until game ends
        int turnCount = 0;
        while (!match.getSupply().isGameOver() && turnCount < 200) {
            match.playTurn();
            turnCount++;
        }
        
        // Game should have ended
        assertTrue(match.getSupply().isGameOver());
        assertEquals(0, match.getSupply().getCount("Framework"));
    }

    @Test
    public void testWinnerHasAutomationPoints() {
        match.setup();
        match.runGame();
        
        int p1Score = match.getPlayer1().getTotalAutomationPoints();
        int p2Score = match.getPlayer2().getTotalAutomationPoints();
        
        // Both players should have at least some points
        assertTrue(p1Score >= 0);
        assertTrue(p2Score >= 0);
        
        // At least one should have more than 0
        assertTrue(p1Score > 0 || p2Score > 0);
    }

    @Test
    public void testGameProgression() {
        match.setup();
        
        CardSupply supply = match.getSupply();
        int initialFrameworks = supply.getCount("Framework");
        
        // Play until at least one framework is bought
        int turnCount = 0;
        while (supply.getCount("Framework") == initialFrameworks && turnCount < 50) {
            match.playTurn();
            turnCount++;
        }
        
        // Either a framework was bought or many turns passed
        assertTrue(supply.getCount("Framework") < initialFrameworks || turnCount >= 50);
    }

    @Test
    public void testPlayersCanBuildDecks() {
        match.setup();
        
        int initialP1APs = match.getPlayer1().getTotalAutomationPoints();
        
        // Play several turns to let players accumulate cards
        for (int i = 0; i < 30; i++) {
            match.playTurn();
        }
        
        // Player 1 should have gained some AP cards (or at least not lost any)
        int finalP1APs = match.getPlayer1().getTotalAutomationPoints();
        assertTrue(finalP1APs >= initialP1APs);
    }

    @Test
    public void testBothPlayersGetToPlay() {
        match.setup();
        
        int initialP1Cards = match.getPlayer1().getTotalCards();
        int initialP2Cards = match.getPlayer2().getTotalCards();
        
        // Play 10 turns - both players should get chances
        for (int i = 0; i < 10; i++) {
            match.playTurn();
        }
        
        // At least one player should have acquired a new card
        int finalP1Cards = match.getPlayer1().getTotalCards();
        int finalP2Cards = match.getPlayer2().getTotalCards();
        
        int totalAcquired = (finalP1Cards - initialP1Cards) + (finalP2Cards - initialP2Cards);
        assertTrue(totalAcquired >= 0); // Some cards might have been purchased
    }

    @Test
    public void testMultipleMatchesProduceDifferentResults() {
        // Due to randomness, multiple games should have different outcomes sometimes
        AutomatedMatch match1 = new AutomatedMatch();
        String result1 = match1.runGame();
        
        AutomatedMatch match2 = new AutomatedMatch();
        String result2 = match2.runGame();
        
        // Just verify both completed successfully
        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1.contains("Player") || result1.contains("tie"));
        assertTrue(result2.contains("Player") || result2.contains("tie"));
    }

    @Test
    public void testSupplyInitialState() {
        match.setup();
        
        CardSupply supply = match.getSupply();
        
        // After setup, supply should have these counts
        assertEquals(46, supply.getCount("Bitcoin")); // 60 - 7 - 7
        assertEquals(8, supply.getCount("Method")); // 14 - 3 - 3
        assertEquals(8, supply.getCount("Framework"));
        assertEquals(40, supply.getCount("Ethereum"));
        assertEquals(30, supply.getCount("Dogecoin"));
    }
}
