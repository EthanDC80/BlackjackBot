import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        if (rank.equals("A")) {
            return 11; // Ace can be 1 or 11, default to 11
        } else if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
            return 10; // Face cards are worth 10
        } else {
            return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(0);
    }
}

class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getScore() {
        int score = cards.stream().mapToInt(Card::getValue).sum();

        // Handle Ace as 1 if the score is over 21
        for (Card card : cards) {
            if (card.getValue() == 11 && score > 21) {
                score -= 10;
            }
        }

        return score;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}

class BlackjackGame {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    int money = 100;
    
    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        
    }

    public void startGame() {
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        // Player's turn
        playerTurn();

        // Dealer's turn
        dealerTurn();

        // Determine the winner
        determineWinner();
    }

    private void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Your hand: " + playerHand + " (Score: " + playerHand.getScore() + ")");
            System.out.println("Dealer's face-up card: " + dealerHand.getCards().get(0));

            System.out.print("Do you want to hit (h) or stand (s)? ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("h")) {
                playerHand.addCard(deck.drawCard());
                if (playerHand.getScore() > 21) {
                    System.out.println("Bust! Your hand is over 21. You lose.");
                    System.exit(0);
                }
            } else if (choice.equals("s")) {
                break;
            }
        }
    }

    private void dealerTurn() {
        while (dealerHand.getScore() < 17) {
            dealerHand.addCard(deck.drawCard());
        }

        System.out.println("Dealer's hand: " + dealerHand + " (Score: " + dealerHand.getScore() + ")");
    }

    private int determineWinner() {
        int playerScore = playerHand.getScore();
        int dealerScore = dealerHand.getScore();

        System.out.println("Your hand: " + playerHand + " (Score: " + playerScore + ")");
        System.out.println("Dealer's hand: " + dealerHand + " (Score: " + dealerScore + ")");

        if (playerScore > 21) {
            money -= 10;
            System.out.println("Bust! Your hand is over 21. You lose.   " + money);
        } else if (dealerScore > 21) {
        	money += 10;
            System.out.println("Dealer busts! You win.       " + money);
        } else if (playerScore > dealerScore) {
            money += 10;
        	System.out.println("You win!    " + money);
        } else if (playerScore < dealerScore) {
           money -= 10;
        	System.out.println("You lose.");
        } else {
            System.out.println("It's a tie!");
        }
        return money;
    }
}


