package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for CardSupply class.
 */
public class CardSupplyTest {

    private CardSupply supply;

    @Before
    public void setUp() {
        supply = new CardSupply();
    }

    @Test
    public void testInitialSupplyCounts() {
        assertEquals(14, supply.getCount("Method"));
        assertEquals(8, supply.getCount("Module"));
        assertEquals(8, supply.getCount("Framework"));
        assertEquals(60, supply.getCount("Bitcoin"));
        assertEquals(40, supply.getCount("Ethereum"));
        assertEquals(30, supply.getCount("Dogecoin"));
    }

    @Test
    public void testTakeCardDecrementsCounts() {
        int initialCount = supply.getCount("Method");
        Card card = supply.takeCard("Method");
        assertEquals(initialCount - 1, supply.getCount("Method"));
        assertNotNull(card);
    }

    @Test
    public void testTakeCardReturnsCorrectCard() {
        Card card = supply.takeCard("Bitcoin");
        assertTrue(card instanceof CryptocurrencyCard);
        assertEquals("Bitcoin", card.getName());
    }

    @Test
    public void testTakeCardReturnsNullWhenEmpty() {
        // Drain all frameworks
        for (int i = 0; i < 8; i++) {
            supply.takeCard("Framework");
        }
        
        Card card = supply.takeCard("Framework");
        assertNull(card);
    }

    @Test
    public void testGetCardCost() {
        assertEquals(2, supply.getCost("Method"));
        assertEquals(5, supply.getCost("Module"));
        assertEquals(8, supply.getCost("Framework"));
        assertEquals(0, supply.getCost("Bitcoin"));
        assertEquals(3, supply.getCost("Ethereum"));
        assertEquals(6, supply.getCost("Dogecoin"));
    }

    @Test
    public void testGetAffordableCards() {
        String[] affordable = supply.getAffordableCards(5);
        
        // With 5 coins, should afford: Bitcoin (0), Method (2), Ethereum (3), Module (5)
        // Should NOT afford: Framework (8), Dogecoin (6)
        assertTrue(contains(affordable, "Bitcoin"));
        assertTrue(contains(affordable, "Method"));
        assertTrue(contains(affordable, "Ethereum"));
        assertTrue(contains(affordable, "Module"));
        assertFalse(contains(affordable, "Framework"));
        assertFalse(contains(affordable, "Dogecoin"));
    }

    @Test
    public void testGetAffordableCardsNoCash() {
        String[] affordable = supply.getAffordableCards(0);
        
        // With 0 coins, only Bitcoin (0 cost) affordable
        assertEquals(1, affordable.length);
        assertEquals("Bitcoin", affordable[0]);
    }

    @Test
    public void testGetAffordableCardsMaxCash() {
        String[] affordable = supply.getAffordableCards(100);
        
        // With 100 coins, all cards should be affordable
        assertEquals(6, affordable.length);
    }

    @Test
    public void testGameOverWhenFrameworksDepleted() {
        assertFalse(supply.isGameOver());
        
        // Drain all frameworks
        for (int i = 0; i < 8; i++) {
            supply.takeCard("Framework");
        }
        
        assertTrue(supply.isGameOver());
    }

    @Test
    public void testGameNotOverWhenOtherCardsEmpty() {
        // Drain all Methods
        for (int i = 0; i < 14; i++) {
            supply.takeCard("Method");
        }
        
        // Game should not be over
        assertFalse(supply.isGameOver());
        assertEquals(0, supply.getCount("Method"));
    }

    @Test
    public void testGetAffordableDoesNotIncludeOutOfStock() {
        // Drain all Ethereums
        for (int i = 0; i < 40; i++) {
            supply.takeCard("Ethereum");
        }
        
        String[] affordable = supply.getAffordableCards(5);
        
        // Even though Ethereum costs 3, it should not be available
        assertFalse(contains(affordable, "Ethereum"));
    }

    @Test
    public void testMultipleTakeCalls() {
        Card card1 = supply.takeCard("Module");
        Card card2 = supply.takeCard("Module");
        Card card3 = supply.takeCard("Module");
        
        assertNotNull(card1);
        assertNotNull(card2);
        assertNotNull(card3);
        assertEquals(5, supply.getCount("Module"));
    }

    @Test
    public void testSupplyToString() {
        String str = supply.toString();
        assertTrue(str.contains("Supply"));
        assertTrue(str.contains("Method"));
        assertTrue(str.contains("Framework"));
    }

    // Helper method
    private boolean contains(String[] arr, String value) {
        for (String s : arr) {
            if (s.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
