package edu.brandeis.cosi103a.ip2;

/**
 * Abstract base class for all cards in the game.
 */
public abstract class Card {
    protected String name;
    protected int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    /**
     * Returns the number of Automation Points this card is worth.
     */
    public abstract int getAutomationPoints();

    /**
     * Returns the number of cryptocoins this card provides when played.
     */
    public abstract int getCoinValue();

    @Override
    public String toString() {
        return name;
    }
}
