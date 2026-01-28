package edu.brandeis.cosi103a.ip2;

/**
 * Cryptocurrency cards provide coins when played during a turn.
 * Types: Bitcoin (cost: 0, value: 1), Ethereum (cost: 3, value: 2), Dogecoin (cost: 6, value: 3)
 */
public class CryptocurrencyCard extends Card {
    private int coinValue;

    public CryptocurrencyCard(String name, int cost, int coinValue) {
        super(name, cost);
        this.coinValue = coinValue;
    }

    @Override
    public int getAutomationPoints() {
        return 0; // Cryptocurrency cards don't provide APs
    }

    @Override
    public int getCoinValue() {
        return coinValue;
    }

    /**
     * Creates a Bitcoin card (cost: 0, value: 1 coin)
     */
    public static CryptocurrencyCard createBitcoin() {
        return new CryptocurrencyCard("Bitcoin", 0, 1);
    }

    /**
     * Creates an Ethereum card (cost: 3, value: 2 coins)
     */
    public static CryptocurrencyCard createEthereum() {
        return new CryptocurrencyCard("Ethereum", 3, 2);
    }

    /**
     * Creates a Dogecoin card (cost: 6, value: 3 coins)
     */
    public static CryptocurrencyCard createDogecoin() {
        return new CryptocurrencyCard("Dogecoin", 6, 3);
    }
}
