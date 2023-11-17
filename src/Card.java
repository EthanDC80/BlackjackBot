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
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
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