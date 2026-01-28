package edu.brandeis.cosi103a.ip2;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the shared supply of cards that players can purchase from.
 */
public class CardSupply {
    private Map<String, Integer> supply;

    public CardSupply() {
        supply = new HashMap<>();
        // Automation cards
        supply.put("Method", 14);
        supply.put("Module", 8);
        supply.put("Framework", 8);
        // Cryptocurrency cards
        supply.put("Bitcoin", 60);
        supply.put("Ethereum", 40);
        supply.put("Dogecoin", 30);
    }

    /**
     * Takes a card from the supply if available.
     * @param cardName the name of the card to take
     * @return the card, or null if not available
     */
    public Card takeCard(String cardName) {
        Integer count = supply.get(cardName);
        if (count == null || count <= 0) {
            return null;
        }
        supply.put(cardName, count - 1);
        return createCard(cardName);
    }

    /**
     * Creates a card instance by name.
     */
    private Card createCard(String cardName) {
        switch (cardName) {
            case "Method": return AutomationCard.createMethod();
            case "Module": return AutomationCard.createModule();
            case "Framework": return AutomationCard.createFramework();
            case "Bitcoin": return CryptocurrencyCard.createBitcoin();
            case "Ethereum": return CryptocurrencyCard.createEthereum();
            case "Dogecoin": return CryptocurrencyCard.createDogecoin();
            default: return null;
        }
    }

    /**
     * Returns the number of cards remaining for a given card type.
     */
    public int getCount(String cardName) {
        return supply.getOrDefault(cardName, 0);
    }

    /**
     * Returns true if all Framework cards have been purchased.
     */
    public boolean isGameOver() {
        return supply.get("Framework") <= 0;
    }

    /**
     * Gets the cost of a card by name.
     */
    public int getCost(String cardName) {
        switch (cardName) {
            case "Method": return 2;
            case "Module": return 5;
            case "Framework": return 8;
            case "Bitcoin": return 0;
            case "Ethereum": return 3;
            case "Dogecoin": return 6;
            default: return Integer.MAX_VALUE;
        }
    }

    /**
     * Returns all available card names that can be purchased with the given coins.
     */
    public String[] getAffordableCards(int coins) {
        String[] allCards = {"Bitcoin", "Ethereum", "Dogecoin", "Method", "Module", "Framework"};
        return java.util.Arrays.stream(allCards)
                .filter(name -> getCount(name) > 0 && getCost(name) <= coins)
                .toArray(String[]::new);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Supply: ");
        for (String card : new String[]{"Method", "Module", "Framework", "Bitcoin", "Ethereum", "Dogecoin"}) {
            sb.append(card).append("(").append(supply.get(card)).append(") ");
        }
        return sb.toString();
    }
}
