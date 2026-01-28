package edu.brandeis.cosi103a.ip2;

import java.util.Random;

/**
 * Runs an automated match between two computer players.
 */
public class AutomatedMatch {
    private PlayerState player1;
    private PlayerState player2;
    private CardSupply supply;
    private Random random;
    private int currentPlayerIndex;

    public AutomatedMatch() {
        this.supply = new CardSupply();
        this.player1 = new PlayerState("Player 1");
        this.player2 = new PlayerState("Player 2");
        this.random = new Random();
    }

    /**
     * Sets up the game: distribute starter decks, draw initial hands, choose starting player.
     */
    public void setup() {
        // Setup starter decks from supply
        player1.setupStarterDeck(supply);
        player2.setupStarterDeck(supply);

        // Draw initial hands
        player1.drawHand();
        player2.drawHand();

        // Randomly choose starting player
        currentPlayerIndex = random.nextInt(2);
        System.out.println("Game setup complete. " + getCurrentPlayer().getName() + " goes first.");
        System.out.println(supply);
    }

    /**
     * Gets the current player.
     */
    private PlayerState getCurrentPlayer() {
        return currentPlayerIndex == 0 ? player1 : player2;
    }

    /**
     * Executes a single turn for the current player.
     */
    public void playTurn() {
        PlayerState player = getCurrentPlayer();
        System.out.println("\n--- " + player.getName() + "'s turn ---");
        System.out.println("Hand: " + player.getHand());

        // Buy phase: play all coins and buy a card
        int coins = player.playAllCoins();
        System.out.println("Coins available: " + coins);

        // Simple AI: buy the most expensive affordable card that makes sense
        String cardToBuy = chooseBestPurchase(coins);
        if (cardToBuy != null) {
            Card purchased = supply.takeCard(cardToBuy);
            if (purchased != null) {
                player.gainCard(purchased);
                System.out.println("Bought: " + purchased.getName());
            }
        } else {
            System.out.println("No purchase made.");
        }

        // Cleanup phase
        player.cleanup();
        System.out.println(player);
        System.out.println(supply);

        // Switch to other player
        currentPlayerIndex = 1 - currentPlayerIndex;
    }

    /**
     * Simple AI strategy to choose what card to buy.
     * Prioritizes: Framework > Module > Dogecoin > Ethereum > Method > Bitcoin
     */
    private String chooseBestPurchase(int coins) {
        String[] priorities = {"Framework", "Module", "Dogecoin", "Ethereum", "Method"};
        
        for (String cardName : priorities) {
            if (supply.getCount(cardName) > 0 && supply.getCost(cardName) <= coins) {
                return cardName;
            }
        }
        
        // Don't buy Bitcoin (it's not worth it - cost 0 but wastes a buy)
        return null;
    }

    /**
     * Runs the game until completion and returns the result.
     */
    public String runGame() {
        setup();
        
        int turnCount = 0;
        while (!supply.isGameOver()) {
            playTurn();
            turnCount++;
            
            // Safety check to prevent infinite loops
            if (turnCount > 1000) {
                System.out.println("Game exceeded maximum turns!");
                break;
            }
        }

        // Determine winner
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Total turns: " + turnCount);
        
        int p1Score = player1.getTotalAutomationPoints();
        int p2Score = player2.getTotalAutomationPoints();
        
        System.out.println(player1.getName() + " final score: " + p1Score + " APs");
        System.out.println(player2.getName() + " final score: " + p2Score + " APs");
        
        String result;
        if (p1Score > p2Score) {
            result = player1.getName() + " wins!";
        } else if (p2Score > p1Score) {
            result = player2.getName() + " wins!";
        } else {
            result = "It's a tie!";
        }
        
        System.out.println(result);
        return result;
    }

    public PlayerState getPlayer1() {
        return player1;
    }

    public PlayerState getPlayer2() {
        return player2;
    }

    public CardSupply getSupply() {
        return supply;
    }
}
