import java.util.ArrayList;
import java.util.Arrays;
class Player {
    private ArrayList<Card> hand;
    private double[][] pctChart = new double[20][36];
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
 * 2-2   |                      |
 * 3-3   |                      |
 * 4-4   |                      |
 * 5-5   |                      |
 * 6-6   |                      |
 * 7-7   |                      |
 * 8-8   |                      |
 * 9-9   |                      |
 * 10-10 |                      |
 * A-A   |                      |
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
        seenHands = new ArrayList<int[]>();
        seenHandsValues = new ArrayList<Double>();
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
}

