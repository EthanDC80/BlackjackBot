public class Blackjack {
    public static void main(String[] args) {
    	 int numSimulations = 1000000;
         int playerWins = 0;
         int dealerWins = 0;
         double[][] pctChart = new double[36][20];
         for (int i = 0; i < pctChart.length; i++) {
            for (int j = 0; j < pctChart[i].length; j++) {
                pctChart[i][j] = 0.5;
            }
        }
         for (int i = 0; i < numSimulations; i++) {
             boolean playerWin = simulateGame(pctChart);
             if (playerWin) {
                 playerWins++;
                } else {
                    dealerWins++;
                }
                // System.out.println("END");
         }

        // System.out.println(Player.pctString(pctChart));

         System.out.println("Player Wins: " + playerWins);
         System.out.println("Dealer Wins: " + dealerWins);
         System.out.println("Total Win Percentage: " + (double) playerWins / (playerWins + dealerWins) * 100);
        }
        
    private static boolean simulateGame(double[][] pctChart) {
        Player player = new Player(pctChart);
        Deck deck = new Deck();
            

        // Deal initial cards
        player.addToHand(deck.drawCard());
        player.addToHand(deck.drawCard());
        
        player.printHand();
        
        Player dealer = new Player(pctChart);
        dealer.addToHand(deck.drawCard());
        
        // System.out.println("Dealer has " + dealer.getHandValue());
        player.dealerValue = dealer.getHandValue();

        int maxHits = 100; // Adjust the maximum number of hits as needed
        int hitCount = 0;

        // Player's turn
        while (player.chooseMove(pctChart) && hitCount < maxHits && player.getHandValue() < 21) {
            // System.out.println("Player Hand Value: " + player.getHandValue());
            player.addToHand(deck.drawCard());
            hitCount++;
            player.printHand();
        }
        if (player.getHandValue() > 21) {
            // Dealer wins or player busts
            // System.out.println("Player busts");
            player.printHand();
            Player.adjustPercentages(player, false, pctChart);


            return false;
        }

        // System.out.println("DONE TURN");

        // Dealer's turn
        

        while (dealer.getHandValue() < 17) {
            dealer.addToHand(deck.drawCard());
        }

        // Determine the winner
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();
        
        // for (Card c : player.getHand()) {
        //     System.out.println(c.value);
        // }

        // Adjust percentages based on the game outcome
        if (playerValue > 21 || (dealerValue <= 21 && dealerValue >= playerValue)) {
            // Dealer wins or player busts
            // System.out.println("Player busts or dealer wins.");
            player.printHand();
            Player.adjustPercentages(player, false, pctChart);
            // System.out.println(Player.pctString(player, pctChart));
            return false;
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            // Player wins
            // System.out.println("Player wins.");
            player.printHand();
            Player.adjustPercentages(player, true, pctChart);
            // System.out.println(Player.pctString(player, pctChart));
            return true;
        } else {
            // Draw
            // System.out.println("Game is a draw.");
            return false;
        }
    }
}
    
