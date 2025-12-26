package at.ac.hcw.blackjack_b_plus_plus.model;

public class Player {
    private String name;
    private int balance;
    private Hand hand;

    public Player(String name, int balance, Hand hand){
        this.name = name;
        this.balance = balance;
        this.hand = hand;
    }

    public void takeTurn(){
        //Hit stand usw.
    }


}
