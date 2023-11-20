public class Blackjack {
    public static void main(String[] args) {
        int numSimulations = 100000;
        int playerWins = 0;
        int dealerWins = 0;
      //  int draws = 0;

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
       // System.out.println("Draws: " + draws);
        System.out.println("Total Win Percentage: " + Double.valueOf(playerWins)/Double.valueOf(dealerWins+playerWins)*100);
    }

    private static boolean simulateGame() {
        Deck deck = new Deck();
        Player player = new Player();

        // Deal initial cards
        player.addToHand(deck.drawCard());
        player.addToHand(deck.drawCard());

        // Player's turn
        while (player.getHandValue() <= 15) {
            player.addToHand(deck.drawCard());
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

        if (playerValue > 21 || (dealerValue <= 21 && dealerValue >= playerValue)) {
            // Dealer wins or player busts
            return false;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            // Player wins
            return true;
        } else {
            // Draw
            return false;
        }
    }
}
