package edu.brandeis.cosi103a.ip2;

/**
 * Main application - runs an automated card game match.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== Automation Card Game ===\n");
        
        AutomatedMatch match = new AutomatedMatch();
        match.runGame();
    }
}
