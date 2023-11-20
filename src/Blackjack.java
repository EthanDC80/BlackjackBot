public class Blackjack {
    public static void main(String[] args) {
    	 int numSimulations = 10000;
         int playerWins = 0;
         int dealerWins = 0;

         for (int i = 0; i < numSimulations; i++) {
             boolean playerWin = simulateGame();
             if (playerWin) {
                 playerWins++;
             } else {
                 dealerWins++;
             }
         }

         System.out.println("Player Wins: " + playerWins);
         System.out.println("Dealer Wins: " + dealerWins);
         System.out.println("Total Win Percentage: " + (double) playerWins / (playerWins + dealerWins) * 100);
     }

    private static boolean simulateGame() {
        Deck deck = new Deck();
        Player player = new Player();

        // Deal initial cards
        player.addToHand(deck.drawCard());
        player.addToHand(deck.drawCard());

        int maxHits = 3; // Adjust the maximum number of hits as needed
        int hitCount = 0;

        // Player's turn
        while (player.chooseMove() && hitCount < maxHits) {
            player.addToHand(deck.drawCard());
            hitCount++;
        }

        // Dealer's turn
        Player dealer = new Player();
        dealer.addToHand(deck.drawCard());

        while (dealer.getHandValue() < 17) {
            dealer.addToHand(deck.drawCard());
        }

        // Determine the winner
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        // Adjust percentages based on the game outcome
        if (playerValue > 21 || (dealerValue <= 21 && dealerValue >= playerValue)) {
            // Dealer wins or player busts
            System.out.println("Player busts or dealer wins.");
            player.adjustPercentages(dealer, false);
            return false;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            // Player wins
            System.out.println("Player wins.");
            player.adjustPercentages(dealer, true);
            return true;
        } else {
            // Draw
            System.out.println("Game is a draw.");
            return false;
        }
    }
}
    
