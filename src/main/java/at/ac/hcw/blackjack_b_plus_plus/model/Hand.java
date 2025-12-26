package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Hand {
    private ArrayList<Card> handCards = new ArrayList<>();

    public Hand(Card card1, Card card2){
        addCard(card1);
        addCard(card2);
    }
//d
    //fügt Karte hinzu wird dann in Blackjack App, Player und Dealer benötigt
    public void addCard(Card card){
        if(card.getName().equals("Ace")){
            do{
                System.out.println("Which Value should the Ace be? 1: 1 | 2: 11: ");
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                if(input == 1){
                    card.setValue(1);
                    break;
                }else if(input == 11){
                    card.setValue(11);
                    break;
                }else{
                    System.out.println("Invalid value");
                }
            }while(true);
        }
        handCards.add(card);
    }

    //löscht die hand
    public void clearHand(){
        handCards.clear();
    }

    //kalkuliert Wert der Hand (Ace muss noch beachtet werden)
    public int calculateHand(){
        int sum = 0;
        for(Card card:handCards){
            sum += card.getValue();
        }
        return sum;
    }

}
