package edu.brandeis.cosi103a.ip2;

/**
 * Automation cards contribute Automation Points (APs) at the end of the game.
 * Types: Method (cost: 2, value: 1), Module (cost: 5, value: 3), Framework (cost: 8, value: 6)
 */
public class AutomationCard extends Card {
    private int automationPoints;

    public AutomationCard(String name, int cost, int automationPoints) {
        super(name, cost);
        this.automationPoints = automationPoints;
    }

    @Override
    public int getAutomationPoints() {
        return automationPoints;
    }

    @Override
    public int getCoinValue() {
        return 0; // Automation cards don't provide coins
    }

    /**
     * Creates a Method card (cost: 2, value: 1 AP)
     */
    public static AutomationCard createMethod() {
        return new AutomationCard("Method", 2, 1);
    }

    /**
     * Creates a Module card (cost: 5, value: 3 AP)
     */
    public static AutomationCard createModule() {
        return new AutomationCard("Module", 5, 3);
    }

    /**
     * Creates a Framework card (cost: 8, value: 6 AP)
     */
    public static AutomationCard createFramework() {
        return new AutomationCard("Framework", 8, 6);
    }
}
