package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * A two-player dice game where players alternate turns,
 * rolling a die and deciding whether to re-roll up to 2 times.
 */
public class App 
{
    private static final int NUM_TURNS = 10;
    private static final int MAX_REROLLS = 2;
    private static final int DIE_SIDES = 6;
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    public static void main(String[] args)
    {
        System.out.println("=== Welcome to the Dice Game ===");
        System.out.println("Two players will alternate turns.");
        System.out.println("Each player gets " + NUM_TURNS + " turns.");
        System.out.println("Each turn: roll a die, then decide to keep or re-roll (up to " + MAX_REROLLS + " times).");
        System.out.println("Highest score wins!\n");
        
        // Get player names
        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine().trim();
        System.out.print("Enter Player 2 name: ");
        String player2Name = scanner.nextLine().trim();
        
        // Initialize scores
        int player1Score = 0;
        int player2Score = 0;
        
        // Play 10 rounds (each player gets a turn per round)
        for (int round = 1; round <= NUM_TURNS; round++) {
            System.out.println("\n=== Round " + round + " ===");
            
            // Player 1's turn
            System.out.println(player1Name + "'s turn:");
            int player1Roll = playTurn(player1Name);
            player1Score += player1Roll;
            System.out.println(player1Name + " gains " + player1Roll + " points. Total: " + player1Score);
            
            // Player 2's turn
            System.out.println("\n" + player2Name + "'s turn:");
            int player2Roll = playTurn(player2Name);
            player2Score += player2Roll;
            System.out.println(player2Name + " gains " + player2Roll + " points. Total: " + player2Score);
        }
        
        // Determine winner
        System.out.println("\n=== Game Over ===");
        System.out.println(player1Name + " final score: " + player1Score);
        System.out.println(player2Name + " final score: " + player2Score);
        
        if (player1Score > player2Score) {
            System.out.println("\n" + player1Name + " wins!");
        } else if (player2Score > player1Score) {
            System.out.println("\n" + player2Name + " wins!");
        } else {
            System.out.println("\nIt's a tie!");
        }
        
        scanner.close();
    }
    
    /**
     * Plays a single turn for a player.
     * Rolls a die and allows the player to re-roll up to 2 times.
     * Returns the final die value to be added to the score.
     */
    private static int playTurn(String playerName) {
        int currentRoll = rollDie();
        System.out.println("You rolled: " + currentRoll);
        
        int rerollsUsed = 0;
        
        while (rerollsUsed < MAX_REROLLS) {
            System.out.print("Keep this roll or re-roll? (keep/re-roll): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("keep")) {
                break;
            } else if (choice.equals("re-roll")) {
                rerollsUsed++;
                currentRoll = rollDie();
                System.out.println("You rolled: " + currentRoll);
                
                if (rerollsUsed < MAX_REROLLS) {
                    System.out.println("Re-rolls remaining: " + (MAX_REROLLS - rerollsUsed));
                } else {
                    System.out.println("No more re-rolls available. Your final roll is: " + currentRoll);
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter 'keep' or 're-roll'.");
            }
        }
        
        return currentRoll;
    }
    
    /**
     * Rolls a 6-sided die.
     * Returns a value between 1 and 6.
     */
    public static int rollDie() {
        return random.nextInt(DIE_SIDES) + 1;
    }
}
