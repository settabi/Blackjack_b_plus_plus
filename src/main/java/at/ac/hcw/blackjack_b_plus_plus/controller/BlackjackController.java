package at.ac.hcw.blackjack_b_plus_plus.controller;

import at.ac.hcw.blackjack_b_plus_plus.model.Blackjack;
import at.ac.hcw.blackjack_b_plus_plus.model.Card;
import at.ac.hcw.blackjack_b_plus_plus.model.Dealer;
import at.ac.hcw.blackjack_b_plus_plus.model.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BlackjackController {
    public VBox PreGameMenu;
    public Label betLabel;
    public Label balanceLabel;
    public VBox RulesMenu;
    public VBox StartMenu;
    public VBox GameMenu;
    public HBox dealerHand;
    public HBox playerHand;
    public Label asd;
    public Label playerHandValueLabel;
    public Label betLabel2;

    @FXML
    private HBox gameOverBox;
    @FXML
    private VBox endGameContainer;
    @FXML
    private Label winnerLabel; // Verknüpft mit fx:id="winnerLabel"
    @FXML
    private Button btnHit;     // Verknüpft mit fx:id="btnHit"
    @FXML
    private Button btnStand;   // Verknüpft mit fx:id="btnStand"
    @FXML
    private Player player;
    private Blackjack blackjack;
    //public TextField betInput;  Wird durhc Chips ersetzt
    private final Dealer dealer = new Dealer();
    @FXML
    private Label dealerValueLabel;
    @FXML
    private Label currentBetLabel; // Das musst du im SceneBuilder noch verknüpfen!
    private int tempBetAmount = 0; // Speichert den Einsatz, während man Chips klickt

    @FXML
    public void initialize() {
        RulesMenu.setVisible(false);
        StartMenu.setVisible(true);
        PreGameMenu.setVisible(false);
        GameMenu.setVisible(false);

        if (gameOverBox != null) gameOverBox.setVisible(false);
        if (endGameContainer != null) endGameContainer.setVisible(false);

        // Winner Label am Anfang verstecken, falls es existiert
        if (winnerLabel != null) {
            if (winnerLabel != null) {
                winnerLabel.setVisible(false);
                winnerLabel.setStyle(
                        "-fx-background-color: rgba(0, 0, 0, 0.8); " +
                                "-fx-background-radius: 20; " +
                                "-fx-padding: 20; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 40px; " +
                                "-fx-font-weight: bold;"
                );
            }
        }

        player = new Player("Umut", 1000);
        blackjack = new Blackjack(player, dealer);
        updateBalanceLabel();

        //Werden wir ncht mehr bruachen da wir Chips verwenden
//        betInput.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                betInput.setText(oldValue);
//            }
//        });
    }

    @FXML
    protected void hitButton() {
        // 1. Logik ausführen (Karte ziehen)
        blackjack.playerHit();

        // 2. WICHTIG: Das Bild aktualisieren!
        makeHandVisiblePlayer();
        updatePlayerHandValue();

        // (Optional: Prüfen ob über 21, dann ist die Runde vorbei)
        if (player.getValue() > 21) {
            endRound("BUSTED! Dealer wins.");

            //Macht endRound
            // updateBalanceLabel(); // Geld aktualisieren falls verloren
            // Hier könnten wir "Verloren" anzeigen oder sowas in der art
        }
    }

    @FXML
    protected void standButton() {
        // 1. Logik ausführen (Dealer ist dran)
        blackjack.playerStand();
        // 2. Dealer Karten aufdecken und neu malen
        makeHandVisibleDealer();

        // 3. Werte vergleichen und Gewinner ermitteln
        int playerVal = player.getValue();
        // Achtung: Hier greifen wir auf den Wert des Dealers zu.
        // Falls dealer.getValue() nicht geht, nimm dealer.getHand().getValue()
        int dealerVal = dealer.getValue();

        String result;

        if (dealerVal > 21) {
            result = "DEALER BUSTS! You win!";
            // Einsatz x 2 zurück
            player.setBalance(player.getBalance() + (player.getBet() * 2));
        } else if (playerVal > dealerVal) {
            result = "YOU WIN!";
            player.setBalance(player.getBalance() + (player.getBet() * 2));
        } else if (playerVal == dealerVal) {
            result = "PUSH (Draw)";
            // Einsatz zurück
            player.setBalance(player.getBalance() + player.getBet());
        } else {
            result = "DEALER WINS!";
        }

        endRound(result);
        // 3. Kontostand aktualisieren (falls gewonnen/verloren)
        //updateBalanceLabel();
    }

    // HILFSMETHODE: Beendet die Runde sauber
    private void endRound(String message) {

        System.out.println("Runde zu Ende! Nachricht: " + message);


        // Buttons sperren (damit man nicht weiter klicken kann!)
        if (btnHit != null) btnHit.setDisable(true);
        if (btnStand != null) btnStand.setDisable(true);

        // Da die Buttons jetzt gesperrt sind, wird die erste Karte aufgedeckt.
        makeHandVisibleDealer();

        // Den ganzen Container anzeigen und nach vorne holen!
        if (endGameContainer != null) {
            System.out.println("Container gefunden! Zeige ihn an.");
            endGameContainer.setVisible(true);
            endGameContainer.toFront();
        }

        // Text setzen (Label muss sichtbar sein innerhalb des Containers)
        if (winnerLabel != null) {
            winnerLabel.setText(message);
            winnerLabel.setVisible(true); // Nicht mehr zwingend nötig, wenn der Container sichtbar wird
        }

        // 3. Buttons SICHTBAR MACHEN (Neu!)
        if (gameOverBox != null) {
            gameOverBox.setVisible(true);
        }

        // 4. Spiel-Buttons sperren
        if (btnHit != null) btnHit.setDisable(true);
        if (btnStand != null) btnStand.setDisable(true);

        // Kontostand updaten (falls sich was geändert hat)
        updateBalanceLabel();
    }

    //Durch Chips ersetzt
//    @FXML
//    protected void setBet(){
//        try{
//            player.clearBet();
//            String text = betInput.getText();
//            if(!text.isEmpty()){
//                int amount = Integer.parseInt(text);
//                if(player.getBalance() >= amount){
//                    player.setBet(amount);
//                    updateBet();
//                    updateBalanceLabel();
//                }else{
//                    betLabel.setText("Bet exceeds Savings");
//                }
//            }
//        }catch (NumberFormatException e){
//            betLabel.setText("No Valid Number given");
//        }
//    }
//    @FXML
//    protected void startRound(){
//        PreGameMenu.setVisible(false);
//        GameMenu.setVisible(true);
//        blackjack.startRound(player.getBet());
//        makeHandVisiblePlayer();
//        makeHandVisibleDealer();
//        updatePlayerHandValue();
//        updateBet();
//
//    }


    //    @FXML
//    protected void allIn(){
//        if(player != null) {
//            int balance = player.getBalance();
//            player.setBet(balance);
//            updateBet();
//            updateBalanceLabel();
//        }
//    }
//    @FXML
//    protected void clearBet(){
//        player.clearBet();
//        updateBet();
//        updateBalanceLabel();
//    }
// Hilfsmethode: Aktualisiert den Text in der Mitte ("0")
    private void updateCurrentBetLabel() {
        if (currentBetLabel != null) {
            currentBetLabel.setText(String.valueOf(tempBetAmount));
        }
    }

    @FXML
    protected void onChip50Clicked() {
        if (player.getBalance() >= tempBetAmount + 50) {
            tempBetAmount += 50;
            updateCurrentBetLabel();
        }
    }

    @FXML
    protected void onChip100Clicked() {
        if (player.getBalance() >= tempBetAmount + 100) {
            tempBetAmount += 100;
            updateCurrentBetLabel();
        }
    }

    @FXML
    protected void onChip200Clicked() {
        if (player.getBalance() >= tempBetAmount + 200) {
            tempBetAmount += 200;
            updateCurrentBetLabel();
        }
    }

    @FXML
    protected void onChip500Clicked() {
        if (player.getBalance() >= tempBetAmount + 500) {
            tempBetAmount += 500;
            updateCurrentBetLabel();
        }
    }

    @FXML
    protected void onAllInClicked() {
        // Setzt alles, was der Spieler hat, als vorläufigen Einsatz
        tempBetAmount = player.getBalance();
        updateCurrentBetLabel();
    }

    @FXML
    protected void onClearBetClicked() {
        // Setzt den vorläufigen Einsatz auf 0 zurück
        tempBetAmount = 0;
        updateCurrentBetLabel();
    }

    @FXML
    protected void onDealButtonClicked() {
        // 1. Prüfen, ob Einsatz > 0 und ob Spieler genug Geld hat
        if (tempBetAmount > 0 && tempBetAmount <= player.getBalance()) {

            // 2. Den Einsatz jetzt fest beim Spieler speichern

            player.setBet(tempBetAmount);

            // 3. Menüs umschalten
            PreGameMenu.setVisible(false);
            GameMenu.setVisible(true);


            // 4. Spiel starten (mit dem gesetzten Betrag)
            blackjack.startRound(player.getBet());

            // 5. UI ZURÜCKSETZEN FÜR NEUE RUNDE
            if (winnerLabel != null) winnerLabel.setVisible(false);
            if (gameOverBox != null) gameOverBox.setVisible(false);
            if (btnHit != null) btnHit.setDisable(false);
            if (btnStand != null) btnStand.setDisable(false);

            // 6. Grafiken und Labels aktualisieren
            makeHandVisiblePlayer();
            makeHandVisibleDealer();
            updatePlayerHandValue();
            updateBet();
            updateBalanceLabel();

            // Wichtig: Temp wieder auf 0 für die nächste Runde
            // tempBetAmount = 0;
        } else {
            // Falls 0 gewettet wurde:
            if (currentBetLabel != null) {
                currentBetLabel.setText("Place a bet!");
            }
        }
    }

    public void updateBalanceLabel() {
        if (player != null && balanceLabel != null) {
            balanceLabel.setText("Balance: " + player.getBalance());
        }
    }

    public void updatePlayerHandValue() {
        if (player != null && playerHandValueLabel != null) {
            playerHandValueLabel.setText("Value: " + player.getValue());
        }
    }

    public void updateBet() {
//        if (player != null && betLabel2 != null && betLabel != null) {
//            betLabel2.setText("Bet: " + player.getBet());
//            betLabel.setText("Bet: " + player.getBet());
//        }

        //Diese Version prüft jedes Label einzeln selbst wenn du betLabel2 nicht benutzt, wird betLabel jetzt trotzdem aktualisiert
        if (player != null) {
            // Prüft einzeln: Ist das Label da? Wenn ja, Update!
            if (betLabel != null) {
                betLabel.setText(String.valueOf(player.getBet()));
            }
            if (betLabel2 != null) {
                betLabel2.setText(String.valueOf(player.getBet()));
            }
        }
    }

    public void switchToStartMenu() {
        RulesMenu.setVisible(false);
        StartMenu.setVisible(true);
        PreGameMenu.setVisible(false);
    }

    public void switchToRulesMenu() {
        RulesMenu.setVisible(true);
        StartMenu.setVisible(false);
        PreGameMenu.setVisible(false);
    }

    public void startPreGameMenu() {
        RulesMenu.setVisible(false);
        StartMenu.setVisible(false);
        PreGameMenu.setVisible(true);
    }

    public void exitGame() {
        Platform.exit();
    }

    private void addCardToPlayer(String imagePath) {
        Image cardImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(cardImage);

        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        playerHand.getChildren().add(imageView);
    }

    private void addCardToDealer(String imagePath) {
        Image cardImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(cardImage);

        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        dealerHand.getChildren().add(imageView);
    }

    private void makeHandVisiblePlayer() {
        playerHand.getChildren().clear();
        for (Card card : player.getHand().getCards()) {
            String imageName = card.getName() + "_of_" + card.getSymbol() + ".png";
            addCardToPlayer("/Images/Cards/" + imageName);
        }
    }

    private void makeHandVisibleDealer() {
        dealerHand.getChildren().clear();

        var cards = dealer.getHand().getCards();

        //Wir prüfen, ob der "Stand"-Button deaktiviert ist.
        //Wenn er deaktiviert ist (grau), bedeutet das: Die Runde ist vorbei!
        boolean roundIsOver = (btnStand != null && btnStand.isDisable());

        int visibleValue = 0; // Wir rechnen hier den sichtbaren Wert aus

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            if (i == 0 && !roundIsOver) {
                addCardToDealer("/Images/Cards/Back.png");
            } else {
                String imageName = card.getName() + "_of_" + card.getSymbol() + ".png";
                addCardToDealer("/Images/Cards/" + imageName);

                // Wir addieren den Wert der Karte (Vereinfacht: Ass ist hier immer 11)
                // Falls du eine Methode getRankValue() oder ähnlich hast, nutze die.
                // Ansonsten schätzen wir hier grob für die Anzeige:
                visibleValue += getCardValueSimple(card);
            }
        }
        // Wenn die Runde vorbei ist, nehmen wir den ECHTEN exakten Wert vom Dealer (mit Ass-Berechnung)
        if (roundIsOver) {
            visibleValue = dealer.getValue();
        }

        // Label updaten
        if (dealerValueLabel != null) {
            dealerValueLabel.setText("Value: " + visibleValue);
        }
    }

    // Kleine Hilfsmethode, um den Wert einer einzelnen Karte zu holen
    // (Da dealer.getValue() immer die ganze Hand berechnet)
    private int getCardValueSimple(Card card) {
        // Wir machen den Namen komplett klein, damit Groß/Klein egal ist
        String name = card.getName().toLowerCase().trim();

        // Prüfen auf Bildkarten (Namen oder Abkürzungen)
        if (name.contains("ace") || name.equals("a")) return 11;
        if (name.contains("king") || name.contains("queen") || name.contains("jack")) return 10;
        if (name.equals("k") || name.equals("q") || name.equals("j")) return 10;

        // Versuchen, die Zahl zu lesen (2, 3, ... 10)
        try {
            return Integer.parseInt(name);
        } catch (NumberFormatException e) {
            // Falls etwas Komisches passiert, schreiben wir es in die Konsole
            System.out.println("ACHTUNG: Konnte Kartenwert nicht lesen für: " + name);
            return 0;
        }
    }

    @FXML
    protected void onPlayAgainClicked() {
        System.out.println("Play Again gedrückt!");

        // 1. Zurück zum Wett-Menü
        GameMenu.setVisible(false);
        PreGameMenu.setVisible(true);

        // 2. Aufräumen
        if (endGameContainer != null) endGameContainer.setVisible(false);
        if (winnerLabel != null) winnerLabel.setVisible(false);
        if (gameOverBox != null) gameOverBox.setVisible(false);

        // 3. Variablen resetten
        tempBetAmount = 0;
        updateCurrentBetLabel();

        // (Optional: Karten vom Tisch nehmen, passiert aber eh beim nächsten Deal)
    }
}
