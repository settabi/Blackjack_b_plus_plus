package at.ac.hcw.blackjack_b_plus_plus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hand {
    private List<Card> handCards = new ArrayList<>();

    public Hand(Card card1, Card card2){
        addCard(card1);
        addCard(card2);
    }
//d
    //fügt Karte hinzu wird dann in Blackjack App, Player und Dealer benötigt
    //habe das mal ausgeblendet schau dir mal diese version an
    public void addCard(Card card){
//        if(card.getName().equals("Ace")){
//            do{
//                System.out.println("Which Value should the Ace be? 1: 1 | 2: 11: ");
//                Scanner scanner = new Scanner(System.in);
//                int input = scanner.nextInt();
//                if(input == 1){
//                    card.setValue(1);
//                    break;
//                }else if(input == 11){
//                    card.setValue(11);
//                    break;
//                }else{
//                    System.out.println("Invalid value");
//                }
//            }while(true);
//        }
        handCards.add(card);
    }

    //löscht die hand
    public void clearHand(){
        handCards.clear();
    }

    //kalkuliert Wert der Hand (Ace muss noch beachtet werden)
    public int calculateHand(){
        int sum=0;
        int aceCount=0;

        for (Card card:handCards){
            sum+=card.getValue();
            if (card.getValue()==11){
                aceCount++;     //um zu sehen wie viele aces wir grad in der hand haben
                //aces haben automatisch den Wert 11 wir müssen aber optimalste ace wert nehmen also wenn die
                // summe über 21 wird nehme wir 1 wenn nicht dann 11
            }
        }
        while(sum>21&&aceCount>0){
            //wenn wir über 21 sind und ace in der hand
            // haben möchten wir -10 rechnen damit die 11 zu einer 1 wird. das wollen wir solange
            // machen bis wir unter 21 sind oder keine ace count mehr haben
            // das können wir aber nur dann machen wenn wir
            // aces in der hand haben also daher brauchen wir ace count
            sum-=10;
            aceCount--;
        }
        return sum;
    }

    @Override
    public String toString() {
        return "current hand value: "+calculateHand();
    }
}
