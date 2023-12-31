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
                if (i == 11 || i == 12 || i == 13) {
                    cards.add(new Card(10));
                }
                if (i == 14) {
                    cards.add(new Card(11));
                }
                cards.add(new Card(i));
            }
        }
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            // Reinitialize the deck if it's empty
            initializeDeck();
        }

        int randomIndex = new Random().nextInt(cards.size());
        return cards.remove(randomIndex);
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        for (int i = 2; i <= 14; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 11 || i == 12 || i == 13) {
                    cards.add(new Card(10));
                }
                if (i == 14) {
                    cards.add(new Card(11));
                }
                cards.add(new Card(i));
            }
        }
    }

    
}