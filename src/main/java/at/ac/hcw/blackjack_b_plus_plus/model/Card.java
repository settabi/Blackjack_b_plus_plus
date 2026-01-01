package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.Objects;

public class Card {

    private String symbol; //called suits aswell (heart, diamond etc...)
    private String name; //called rank aswell (king, queen, 2, ace, 3 etc..)
    private int value;//value of the card we do have we need this for the point counting

    public Card(String symbol, String name, int value) {
        this.symbol = symbol;
        this.name = name;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setValue(int value){
        this.value = value;
    }

    @Override
    public String toString() {

        return name + " of " + symbol + " (has value " + value + ")";
    }
}