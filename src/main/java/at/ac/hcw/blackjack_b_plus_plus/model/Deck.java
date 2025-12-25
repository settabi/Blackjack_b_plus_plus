package at.ac.hcw.blackjack_b_plus_plus.model;

public class Deck {
    private Card[] cards = new Card[52];
    private int topCard;
    private final int numberOfCardsOneDeck=52;

    private final String[]  SYMBOL = {"Spades", "Diamonds", "Hearts", "Clubs"};
    private final String[]  NAME = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack", "Ace"};
    private final int[]    VALUE = { 2,3,4,5,6,7,8,9,10,10,10,10,11};
    // i typed in the int array the value 10 4 times for each name that it could be.
    // the reason is I wanted these arrays to be parallel (same length) so we can use one
    // index going up by 1 and hit all the cards

    int cardCounter=0;

    //creating a single deck.
    public Deck(){
        //now i want to print all every single combination of cards for each symbol,
        // same cards but 4 times because we have 4 symbols.
        for (int i=0; i<SYMBOL.length; i++){
            String symbolCurrent = SYMBOL[i];
            for (int j=0; j< NAME.length; j++){
                String nameCurrent = NAME[j];
                int valueCurrent = VALUE[j];

                this.cards[cardCounter] = new Card(symbolCurrent, nameCurrent, valueCurrent);
                cardCounter++;
            }
        }
    }
}
