package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player's state including their deck, hand, and discard pile.
 */
public class PlayerState {
    private String name;
    private List<Card> drawPile;
    private List<Card> hand;
    private List<Card> discardPile;
    private List<Card> playedCards;

    public PlayerState(String name) {
        this.name = name;
        this.drawPile = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.playedCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Sets up the player's initial deck with 7 Bitcoins and 3 Methods from the supply.
     */
    public void setupStarterDeck(CardSupply supply) {
        // Get 7 Bitcoins
        for (int i = 0; i < 7; i++) {
            Card card = supply.takeCard("Bitcoin");
            if (card != null) {
                drawPile.add(card);
            }
        }
        // Get 3 Methods
        for (int i = 0; i < 3; i++) {
            Card card = supply.takeCard("Method");
            if (card != null) {
                drawPile.add(card);
            }
        }
        // Shuffle the starter deck
        Collections.shuffle(drawPile);
    }

    /**
     * Draws cards into the hand until the hand has 5 cards.
     */
    public void drawHand() {
        while (hand.size() < 5) {
            if (drawPile.isEmpty()) {
                if (discardPile.isEmpty()) {
                    break; // No more cards to draw
                }
                // Shuffle discard pile into draw pile
                drawPile.addAll(discardPile);
                discardPile.clear();
                Collections.shuffle(drawPile);
            }
            hand.add(drawPile.remove(0));
        }
    }

    /**
     * Plays all cryptocurrency cards from hand and returns total coin value.
     */
    public int playAllCoins() {
        int totalCoins = 0;
        List<Card> toPlay = new ArrayList<>();
        
        for (Card card : hand) {
            if (card instanceof CryptocurrencyCard) {
                totalCoins += card.getCoinValue();
                toPlay.add(card);
            }
        }
        
        hand.removeAll(toPlay);
        playedCards.addAll(toPlay);
        
        return totalCoins;
    }

    /**
     * Adds a purchased card to the discard pile.
     */
    public void gainCard(Card card) {
        discardPile.add(card);
    }

    /**
     * Cleanup phase: discard hand and played cards, then draw a new hand.
     */
    public void cleanup() {
        discardPile.addAll(hand);
        discardPile.addAll(playedCards);
        hand.clear();
        playedCards.clear();
        drawHand();
    }

    /**
     * Returns the total Automation Points in the player's entire deck.
     */
    public int getTotalAutomationPoints() {
        int total = 0;
        for (Card card : drawPile) {
            total += card.getAutomationPoints();
        }
        for (Card card : hand) {
            total += card.getAutomationPoints();
        }
        for (Card card : discardPile) {
            total += card.getAutomationPoints();
        }
        for (Card card : playedCards) {
            total += card.getAutomationPoints();
        }
        return total;
    }

    /**
     * Returns the total number of cards the player has.
     */
    public int getTotalCards() {
        return drawPile.size() + hand.size() + discardPile.size() + playedCards.size();
    }

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return name + " [Hand: " + hand.size() + ", Draw: " + drawPile.size() + 
               ", Discard: " + discardPile.size() + ", APs: " + getTotalAutomationPoints() + "]";
    }
}
