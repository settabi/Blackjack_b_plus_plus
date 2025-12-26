package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Deck {
    private List<Card> cards = new ArrayList<Card>();
    private int topCard;
//    private final int numberOfCardsOneDeck = 52; Unnötig da ein deck wenn es fertig erstellt ist eh automatisch 52 karten hat

    private final String[] SUITE = {"Spades", "Diamonds", "Hearts", "Clubs"};
    private final int[] VALUE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    private final String[] NAMES = { "Ace", "2","3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack"};
    // i typed in the int array the value 10 4 times for each name that it could be.
    // the reason is I wanted these arrays to be parallel (same length) so we can use one
    // index going up by 1 and hit all the cards
    // ps du musst nicht auf englisch schreiben ich verstehe deutsch



    public Deck() {
        for (String curSymbol : SUITE) {
            for (int i = 0; i < NAMES.length; i++) {
                int value = VALUE[i];
                String name = NAMES[i];

                if (Objects.equals(name, "Ace")) {
                    Card ace_card = new Card(curSymbol, name, 1, 11);
                    cards.add(ace_card);
                } else {
                    Card card = new Card(curSymbol, name, value);
                    cards.add(card);
                }
            }

        }

    }

    public void removeCardfromDeck(Card card){
        cards.remove(card);
    }

    @Override
    public String toString() {
        return "Cards: " + '\n' + cards;
    }
}
