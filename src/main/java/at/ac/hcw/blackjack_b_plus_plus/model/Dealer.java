package at.ac.hcw.blackjack_b_plus_plus.model;

public class Dealer {
    private Hand hand;

    private double badLuckFactor = 0.0; // Beeinflusst durch Trinkgeld

    public void giveTip() {
        // Erhöht die Chance auf eine schlechte Hand des Dealers
        this.badLuckFactor += 0.05;
    }

    public boolean mustHit() {
        return hand.calculateHand() < 17;
    }

    public Hand getHand() { return hand; }


}
