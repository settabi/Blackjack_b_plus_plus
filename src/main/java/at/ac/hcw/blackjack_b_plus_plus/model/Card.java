package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.Objects;

public class Card {

    private String symbol; //called suits aswell (heart, diamond etc...)
    private String name; // called rank aswell (king, queen, 2, ace, 3 etc..)
    private int value; //value of the card we do have we need this for the point counting
    private int sec_value;

    public Card(String symbol, String name, int value) {
        this.symbol = symbol;
        this.name = name;
        this.value = value;
    }

    public Card(String symbol, String name, int value, int sec_value) {
        this.symbol = symbol;
        this.name = name;
        this.value = value;
        this.sec_value = sec_value;
    }

    public int getValue() {
        return value;
    }

    public int getSec_value() {
        return sec_value;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        if (Objects.equals(name, "Ace")) {
            return name + " of " + symbol + " (has value " + value + " and " + sec_value + ")" + '\n';
        }
        return name + " of " + symbol + " (has value " + value + ")" + '\n';
    }
}