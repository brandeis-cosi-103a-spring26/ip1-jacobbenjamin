package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for PlayerState class.
 */
public class PlayerStateTest {

    private PlayerState player;
    private CardSupply supply;

    @Before
    public void setUp() {
        player = new PlayerState("Test Player");
        supply = new CardSupply();
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals("Test Player", player.getName());
        assertEquals(0, player.getTotalCards());
        assertEquals(0, player.getTotalAutomationPoints());
    }

    @Test
    public void testSetupStarterDeck() {
        player.setupStarterDeck(supply);
        
        // Should have 10 cards total (7 Bitcoins + 3 Methods)
        assertEquals(10, player.getTotalCards());
        
        // Supply should be reduced
        assertEquals(53, supply.getCount("Bitcoin")); // 60 - 7 = 53
        assertEquals(11, supply.getCount("Method")); // 14 - 3 = 11
    }

    @Test
    public void testDrawHand() {
        player.setupStarterDeck(supply);
        player.drawHand();
        
        // Should have 5 cards in hand
        List<Card> hand = player.getHand();
        assertEquals(5, hand.size());
    }

    @Test
    public void testDrawHandMoreThanAvailable() {
        // Add just 3 cards to the draw pile
        player.gainCard(CryptocurrencyCard.createBitcoin());
        player.gainCard(CryptocurrencyCard.createBitcoin());
        player.gainCard(CryptocurrencyCard.createBitcoin());
        
        // Drawing should not fail, just get what's available
        player.drawHand();
        assertTrue(player.getHand().size() <= 5);
        assertTrue(player.getHand().size() > 0);
    }

    @Test
    public void testPlayAllCoins() {
        player.gainCard(CryptocurrencyCard.createBitcoin()); // 1 coin
        player.gainCard(CryptocurrencyCard.createEthereum()); // 2 coins
        player.gainCard(CryptocurrencyCard.createBitcoin()); // 1 coin
        player.drawHand();
        
        int coins = player.playAllCoins();
        assertEquals(4, coins); // 1 + 2 + 1
        
        // All cryptocurrency cards should be removed from hand
        for (Card card : player.getHand()) {
            assertFalse(card instanceof CryptocurrencyCard);
        }
    }

    @Test
    public void testPlayCoinsIgnoresAutomationCards() {
        player.gainCard(AutomationCard.createMethod());
        player.gainCard(CryptocurrencyCard.createBitcoin());
        player.gainCard(AutomationCard.createModule());
        player.drawHand();
        
        int coins = player.playAllCoins();
        assertEquals(1, coins); // Only Bitcoin's 1 coin
        
        // Hand should still have the 2 automation cards
        List<Card> hand = player.getHand();
        assertEquals(2, hand.size());
    }

    @Test
    public void testGainCard() {
        player.gainCard(AutomationCard.createMethod());
        player.gainCard(CryptocurrencyCard.createBitcoin());
        
        assertEquals(2, player.getTotalCards());
    }

    @Test
    public void testGetTotalAutomationPoints() {
        player.gainCard(AutomationCard.createMethod()); // 1 AP
        player.gainCard(AutomationCard.createModule()); // 3 APs
        player.gainCard(AutomationCard.createFramework()); // 6 APs
        player.gainCard(CryptocurrencyCard.createBitcoin()); // 0 APs
        
        assertEquals(10, player.getTotalAutomationPoints());
    }

    @Test
    public void testCleanup() {
        // Setup: add cards and draw a hand
        player.setupStarterDeck(supply);
        player.drawHand();
        
        // Play all coins
        player.playAllCoins();
        
        // Cleanup
        player.cleanup();
        
        // Hand should be reset to 5 (or less if deck is small)
        int newHandSize = player.getHand().size();
        assertEquals(5, newHandSize);
    }

    @Test
    public void testGetTotalCardsAcrossAllPiles() {
        player.gainCard(CryptocurrencyCard.createBitcoin());
        player.gainCard(CryptocurrencyCard.createBitcoin());
        player.gainCard(AutomationCard.createMethod());
        
        assertEquals(3, player.getTotalCards());
    }

    @Test
    public void testMultiplePlayers() {
        PlayerState player1 = new PlayerState("Player 1");
        PlayerState player2 = new PlayerState("Player 2");
        
        player1.gainCard(AutomationCard.createFramework());
        player2.gainCard(AutomationCard.createMethod());
        
        assertEquals(6, player1.getTotalAutomationPoints());
        assertEquals(1, player2.getTotalAutomationPoints());
        assertNotEquals(player1.getTotalAutomationPoints(), player2.getTotalAutomationPoints());
    }

    @Test
    public void testPlayerToString() {
        player.setupStarterDeck(supply);
        player.drawHand();
        
        String str = player.toString();
        assertTrue(str.contains("Test Player"));
        assertTrue(str.contains("Hand"));
        assertTrue(str.contains("APs"));
    }

    @Test
    public void testHandShuffling() {
        // Add cards to player
        for (int i = 0; i < 10; i++) {
            player.gainCard(CryptocurrencyCard.createBitcoin());
        }
        
        player.drawHand();
        
        // Draw multiple hands and check they can be different
        // (Note: this test is probabilistic, but should pass most of the time)
        player.cleanup();
        player.cleanup();
        
        // Just verify the game mechanics work
        assertEquals(10, player.getTotalCards());
    }

    @Test
    public void testDiscardPileReshuffling() {
        // Add 5 cards
        for (int i = 0; i < 5; i++) {
            player.gainCard(CryptocurrencyCard.createBitcoin());
        }
        
        // Draw them
        player.drawHand();
        assertEquals(5, player.getHand().size());
        
        // Cleanup moves cards to discard pile
        player.cleanup();
        
        // Draw again - should reshuffle the discard pile
        player.drawHand();
        assertTrue(player.getHand().size() > 0);
    }

    @Test
    public void testPlayAllCoinsWithEmptyHand() {
        int coins = player.playAllCoins();
        assertEquals(0, coins);
    }
}
