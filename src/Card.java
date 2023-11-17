import java.util.ArrayList;
import java.util.Random;

class Card {
    int value;
    
    public Card(int value) {
        this.value = value;
    }
}

class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (int i = 2; i <= 14; i++) {
            for (int j = 0; j < 4; j++) {
               
            if(i == 11 || i == 12 || i == 13) {
            	cards.add(new Card(10));
            }
            if(i == 14) {
            	cards.add(new Card(11));
            	
            }
            	cards.add(new Card(i));
        }
    }
    }
    public Card drawCard() {
        if (!cards.isEmpty()) {
            shuffle();
        }

        int randomIndex = new Random().nextInt(cards.size());
        return cards.remove(randomIndex);
    }

    private void shuffle() {
        // Simple shuffle logic for demonstration purposes
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = new Random().nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
}

class Player {
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<>();
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
}

