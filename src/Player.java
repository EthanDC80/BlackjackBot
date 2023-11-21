import java.util.ArrayList;
import java.util.Arrays;
class Player {
    private ArrayList<Card> hand;
    private ArrayList<int[]> indexHistory;
    private double[][] pctChart = new double[36][20];
/*
 *         A 2 3 4 5 6 7 8 9 10 | sA s2 s3 s4 s5 s6 s7 s8 s9 s10
 * 3     |                      |
 * 4     |                      |
 * 5     |                      |
 * 6     |                      |
 * 7     |                      |
 * 8     |                      |
 * 9     |                      |
 * 10    |                      |
 * 11    |                      |
 * 12    |                      |
 * 13    |                      |
 * 14    |                      |
 * 15    |                      |
 * 16    |                      |
 * 17    |                      |
 * 18    |                      |
 * 19    |                      |
 * 20    |                      |
 * A-2   |                      |
 * A-3   |                      |
 * A-4   |                      |
 * A-5   |                      |
 * A-6   |                      |
 * A-7   |                      |
 * A-8   |                      |
 * A-9   |                      |
 * A-A   |                      |
 * 2-2   |                      |
 * 3-3   |                      |
 * 4-4   |                      |
 * 5-5   |                      |
 * 6-6   |                      |
 * 7-7   |                      |
 * 8-8   |                      |
 * 9-9   |                      |
 * 10-10 |                      |
 */
    private boolean hasAce;
    private ArrayList<int[]> seenHands;
    private ArrayList<Double> seenHandsValues;

    public Player() {
        hand = new ArrayList<Card>();
        for (int i = 0; i < pctChart.length; i++) {
            for (int j = 0; j < pctChart[i].length; j++) {
                pctChart[i][j] = 0.5;
            }
        }
        hasAce = false;
        indexHistory = new ArrayList<int[]>();
        seenHands = new ArrayList<int[]>();
        seenHandsValues = new ArrayList<Double>();
    }
    
    public int[] getIndex() throws IndexOutOfBoundsException {
        int[] index = {-1, -1};
        int handValue = getHandValue();
        int dealerCardIndex = dealerCardIndex();
        if (handValue >= 3 && handValue <= 21 && dealerCardIndex >= 0 && dealerCardIndex <= 21) {
            // Check if the hand has doubles
            if (hand.size() == 2 && hand.get(0).value == hand.get(1).value) {
                index[0] = hand.get(0).value + 25;
                index[1] = dealerCardIndex - 1;
            } else {
                index[0] = handValue - 3;
                index[1] = dealerCardIndex - 1;
            }
        } else {
            
        }
        // indexHistory.add(index);
        return index;
    }

    public boolean chooseMove() {
        int handValue = getHandValue();
        int dealerCardIndex = dealerCardIndex();
        double hitPercentage = 0.5;
        // Ensure the hand value and dealer card index are within bounds
        if (handValue >= 3 && handValue <= 20 && dealerCardIndex >= 0 && dealerCardIndex <= 21) {
            int[] index = getIndex();
            hitPercentage = pctChart[index[0]][index[1]];

            // Check if the random number is less than the hitPercentage
            System.out.println("Hit Percentage: " + hitPercentage);
            return Math.random() < hitPercentage;

        } else {
            // Handle out-of-bounds conditions (you may choose to always hit or always stand)
            return handValue < 17; // For example, always hit if out of bounds
        }
    }
    
    static void adjustPercentages(Player player, boolean playerWins) {
        double adjustment = 1000; // Fixed adjustment for demonstration purposes
        System.out.println("SIZE !!!!!!!!!!!!!" + player.indexHistory.size());
        for (int[] index : player.indexHistory){
            if (index[0] == -1){
                continue;
            }
            if (playerWins) {
                player.pctChart[index[0]][index[1]] += adjustment;
                player.pctChart[index[0]][index[1]] = Math.min(1.0, player.pctChart[index[0]][index[1]]); // Ensure it doesn't exceed 1.0
            } else {
                player.pctChart[index[0]][index[1]] -= adjustment;
                player.pctChart[index[0]][index[1]] = Math.max(0.0, player.pctChart[index[0]][index[1]]); // Ensure it doesn't go below 0.0
            }
        }
    }
    
    private int dealerCardIndex() {
        int dealerCard = getHand().get(0).value;
       /* if (dealerCard == 11) {
            dealerCard = 14; // Convert Ace to 14
        }
        */
        return dealerCard ; // Index starts from 0 for card values 2-14
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            value += card.value;

            if (card.value == 1) {
                numAces++;
            }
        }

        while (numAces > 0 && value + 10 <= 21) {
            value += 10;
            numAces--;
        }

        return value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public boolean hasAces() {
        return hasAce;
    }

    public boolean sawHand(int[] hand) {
        Arrays.sort(hand);
        for (int[] pastHand : seenHands){
            if (Arrays.equals(hand, pastHand)) {
                return false;
            }
        }
        seenHands.add(hand);
        seenHandsValues.add(0.5);
        return true;
    }
    public static String pctString(Player player) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < player.pctChart.length; i++) {
            for (int j = 0; j < player.pctChart[i].length; j++) {
                sb.append(String.format("%.2f ", player.pctChart[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

