package edu.brandeis.cosi103a.ip2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for CryptocurrencyCard class.
 */
public class CryptocurrencyCardTest {

    @Test
    public void testBitcoinCardProperties() {
        CryptocurrencyCard bitcoin = CryptocurrencyCard.createBitcoin();
        assertEquals("Bitcoin", bitcoin.getName());
        assertEquals(0, bitcoin.getCost());
        assertEquals(1, bitcoin.getCoinValue());
        assertEquals(0, bitcoin.getAutomationPoints());
    }

    @Test
    public void testEthereumCardProperties() {
        CryptocurrencyCard ethereum = CryptocurrencyCard.createEthereum();
        assertEquals("Ethereum", ethereum.getName());
        assertEquals(3, ethereum.getCost());
        assertEquals(2, ethereum.getCoinValue());
        assertEquals(0, ethereum.getAutomationPoints());
    }

    @Test
    public void testDogecoinCardProperties() {
        CryptocurrencyCard dogecoin = CryptocurrencyCard.createDogecoin();
        assertEquals("Dogecoin", dogecoin.getName());
        assertEquals(6, dogecoin.getCost());
        assertEquals(3, dogecoin.getCoinValue());
        assertEquals(0, dogecoin.getAutomationPoints());
    }

    @Test
    public void testCryptocurrencyCardToString() {
        CryptocurrencyCard ethereum = CryptocurrencyCard.createEthereum();
        assertEquals("Ethereum", ethereum.toString());
    }

    @Test
    public void testCryptocurrencyCardInheritance() {
        CryptocurrencyCard card = CryptocurrencyCard.createDogecoin();
        assertTrue(card instanceof Card);
    }

    @Test
    public void testCustomCryptocurrencyCard() {
        CryptocurrencyCard custom = new CryptocurrencyCard("TestCoin", 5, 4);
        assertEquals("TestCoin", custom.getName());
        assertEquals(5, custom.getCost());
        assertEquals(4, custom.getCoinValue());
        assertEquals(0, custom.getAutomationPoints());
    }

    @Test
    public void testCryptocurrencyCardsCostMoreThanValue() {
        // Verify the game design: coins cost more than they're worth
        CryptocurrencyCard ethereum = CryptocurrencyCard.createEthereum();
        assertTrue(ethereum.getCost() > ethereum.getCoinValue());
        
        CryptocurrencyCard dogecoin = CryptocurrencyCard.createDogecoin();
        assertTrue(dogecoin.getCost() > dogecoin.getCoinValue());
    }

    @Test
    public void testBitcoinCostZero() {
        CryptocurrencyCard bitcoin = CryptocurrencyCard.createBitcoin();
        assertEquals(0, bitcoin.getCost());
    }
}
