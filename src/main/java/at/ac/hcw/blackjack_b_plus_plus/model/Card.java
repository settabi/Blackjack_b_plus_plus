package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.Objects;

public class Card {

    private String symbol; //called suits aswell (heart, diamond etc...)
    private String name; // called rank aswell (king, queen, 2, ace, 3 etc..)
    private int value;//value of the card we do have we need this for the point counting
    private int aceValue1;
    private int aceValue2;

    public Card(String symbol, String name, int value) {
        this.symbol = symbol;
        this.name = name;
        this.value = value;
    }

    public Card(String symbol, String name, int aceValue1, int aceValue2) {
        this.symbol = symbol;
        this.name = name;
        this.aceValue1 = aceValue1;
        this.aceValue2 = aceValue2;
    }

    public int getValue() {
        return value;
    }

    public int getAceValue1(){
        return aceValue1;
    }

    public int getAceValue2() {
        return aceValue2;
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
        if (Objects.equals(name, "Ace")) {
            return name + " of " + symbol + " (has value " + aceValue1 + " and " + aceValue2 + ")" + '\n';
        }
        return name + " of " + symbol + " (has value " + value + ")" + '\n';
    }
}