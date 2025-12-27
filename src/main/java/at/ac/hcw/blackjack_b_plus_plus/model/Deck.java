package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    private int topCard;
//    private final int numberOfCardsOneDeck = 52; Unnötig da ein deck wenn es fertig erstellt ist eh automatisch 52 karten hat

    private final String[] SUITE = {"Spades", "Diamonds", "Hearts", "Clubs"};
    private final int[] VALUE = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private final String[] NAMES = {"2","3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack", "Ace"};
    // i typed in the int array the value 10 4 times for each name that it could be.
    // the reason is I wanted these arrays to be parallel (same length) so we can use one
    // index going up by 1 and hit all the cards
    // ps du musst nicht auf englisch schreiben ich verstehe deutsch



    public Deck(int numberOfDecks) {
        for (int i=0; i<numberOfDecks; i++){ //für anzahl der decks die zusammengemischt werden soll
            for (String suiteCurrent:SUITE){ //rest ist ziemlich gleich dann durch alle suite für jede kombination
                for (int j=0; j<NAMES.length; j++){
                    String currentName = NAMES[j];
                    int currentValue=VALUE[j];
                    Card newCard = new Card(suiteCurrent, currentName, currentValue);//karten objekt erstellen mit den aktuellen werten,zeichen
                    cards.add(newCard); //jede karte die wir erstellen packen wir direkt in die liste bevor wir eine andere erstellen.
                }
            }
        }
//        for (String curSymbol : SUITE) {
//            for (int i = 0; i < NAMES.length; i++) {
//                int value = VALUE[i];
//                String name = NAMES[i];
//                if (Objects.equals(name, "Ace")) {
//                    Card ace_card = new Card(curSymbol, name, 1, 11);
//                    cards.add(ace_card);
//                } else {
//                    Card card = new Card(curSymbol, name, value);
//                    cards.add(card);
//                }
//            }
//
//        }
        Collections.shuffle(cards); //karten direkt mischen nachdem wir die erstellt haben.
    }

    public Card dealCards(){
        if(cards.isEmpty()){
            return null;
        }
        return cards.remove(0);  //wir können einfach die karte an der position 0
                                        // entfernen also die oberste und brauchen keine extra methode dafür
    }
//    public void removeCardfromDeck(Card card){
//        cards.remove(card);
//    }

    @Override
    public String toString() {
        return cards.size() + "amount of cards are left in the Deck";
    }
}
