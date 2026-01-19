package at.ac.hcw.blackjack_b_plus_plus.controller;

import at.ac.hcw.blackjack_b_plus_plus.model.Blackjack;
import at.ac.hcw.blackjack_b_plus_plus.model.Card;
import at.ac.hcw.blackjack_b_plus_plus.model.Dealer;
import at.ac.hcw.blackjack_b_plus_plus.model.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Stack;

public class BlackjackController {

    @FXML
    public Label playerBetLabel;
    @FXML
    public Label playerBetLabel2;
    @FXML
    public Label playerHandValue;
    @FXML
    public Label dealerHandValue;
    @FXML
    public VBox valueGroup;
    @FXML
    public ImageView dealer_skin;
    @FXML
    public ImageView btnChangeSkinRight;
    @FXML
    public ImageView btnChangeSkinLeft;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane rulesPane1;
    @FXML
    private AnchorPane rulesPane2;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Group bettingGroup;

    @FXML
    private Label playerBalanceLabel;
    @FXML
    private Label resultLabel;

    @FXML
    private HBox dealerHandBox;
    @FXML
    private HBox playerHandBox;

    @FXML
    private ImageView btnHit;
    @FXML
    private ImageView btnStand;

    @FXML
    private VBox gameOverGroup;
    @FXML
    private VBox actionGroup;

    @FXML
    private TextField playerNameInput;
    private int tempBetAmount = 0;
    private int currentDealerSkin = 1;


    @FXML
    private ImageView btnStartMenu, btnRulesMenu, btnExitMenu;
    @FXML
    private ImageView btnCloseRules1, btnNextRules, btnCloseRules2, btnBackRules;
    @FXML
    private ImageView btnPlayAgain, btnLeave;
    @FXML
    private ImageView btnAllIn, btnClear, btnStartGame;
    @FXML
    private ImageView chip50, chip100, chip200, chip500;
    @FXML
    private StackPane betStack;
    @FXML
    private Label playerNameLabel;

    //logik, falls fehler sind ändert einfach
    private Blackjack blackjack;
    private Player player;
    private Dealer dealer;

    private Stack<Integer> betHistory = new Stack<>();
    private String savedPlayerName = "Player";

    @FXML
    public void initialize() {
        rulesPane1.setVisible(false);
        menuPane.setVisible(true);
        gamePane.setVisible(false);

        bettingGroup.setVisible(true);
        gameOverGroup.setVisible(false);
        resultLabel.setVisible(false);


        // Menü
        btnStartMenu.setOnMouseClicked(e -> {
            savedPlayerName = playerNameInput.getText().isEmpty() ? "Player" : playerNameInput.getText();
            initGame();
        });
        btnRulesMenu.setOnMouseClicked(e -> switchToRulesMenu());
        btnExitMenu.setOnMouseClicked(e -> exitGame());

        // Regeln
        btnCloseRules1.setOnMouseClicked(e -> switchToStartMenu());
        btnNextRules.setOnMouseClicked(e -> {
            rulesPane1.setVisible(false);
            rulesPane2.setVisible(true);
        });
        btnCloseRules2.setOnMouseClicked(e -> switchToStartMenu());
        btnBackRules.setOnMouseClicked(e -> {
            rulesPane2.setVisible(false);
            rulesPane1.setVisible(true);
        });
        btnChangeSkinRight.setOnMouseClicked(e -> {
            if (currentDealerSkin < countDealerSkins()) {
                currentDealerSkin++;
                updateDealerSkin();
            }
        });

        btnChangeSkinLeft.setOnMouseClicked(e -> {
            if (currentDealerSkin > 1) {
                currentDealerSkin--;
                updateDealerSkin();
            }
        });

        // Chips
        setupChipEvent(chip50, 50);
        setupChipEvent(chip100, 100);
        setupChipEvent(chip200, 200);
        setupChipEvent(chip500, 500);

        btnClear.setOnMouseClicked(e -> onClearBetClicked());
        btnAllIn.setOnMouseClicked(e -> onAllInClicked());
        betStack.setOnMouseClicked(e -> removeTopChip());

        // Spiel Starten
        btnStartGame.setOnMouseClicked(e -> onDealButtonClicked());

        // In-Game
        btnHit.setOnMouseClicked(e -> hitButton());
        btnStand.setOnMouseClicked(e -> standButton());

        // Game Over
        btnPlayAgain.setOnMouseClicked(e -> {
            tempBetAmount = 0;
            onPlayAgainClicked();
        });
        btnLeave.setOnMouseClicked(e -> {
            tempBetAmount = 0;
            switchToStartMenu();
        });
    }

    private void initGame() {
        blackjack = new Blackjack(savedPlayerName, 1000);
        player = blackjack.getPlayer();
        dealer = blackjack.getDealer();

        menuPane.setVisible(false);
        gamePane.setVisible(true);

        onPlayAgainClicked();
        playerNameLabel.setText("Player: " + savedPlayerName);
        updateBalanceLabel();
    }


    @FXML
    protected void hitButton() {
        blackjack.playerHit();
        makeHandVisiblePlayer();
        updateHandValue();

        if (player.getScore() > 21) {
            endRound("BUSTED! Dealer wins.");
        } else if (player.getScore() == 21) {
            standButton();
        }
    }

    @FXML
    protected void standButton() {
        blackjack.playerStand();
        makeHandVisibleDealer();

        int playerVal = player.getScore();
        int dealerVal = dealer.getScore();
        String result;

        if (dealerVal > 21) {
            result = "DEALER BUSTS! You win!";
        } else if (playerVal > dealerVal) {
            result = "YOU WIN!";
        } else if (playerVal == dealerVal) {
            result = "PUSH (Draw)";
        } else {
            result = "DEALER WINS!";
        }

        endRound(result);
    }

    private void endRound(String message) {
        System.out.println("Runde zu Ende: " + message);
        actionGroup.setVisible(false);
        valueGroup.setVisible(false);
        //reihenfolge gewechselt weil haben wir sonst die verdeckte karte von dealer gar nicht mehr gesehen
        gameOverGroup.setVisible(true);
        resultLabel.setText(message);
        resultLabel.setVisible(true);
        makeHandVisibleDealer();
        updateBalanceLabel();
    }

    @FXML
    protected void onDealButtonClicked() {
        if (tempBetAmount > 0 && tempBetAmount <= player.getBalance()) {
            updateCurrentBet();
            blackjack.startRound(tempBetAmount);

            bettingGroup.setVisible(false);
            actionGroup.setVisible(true);
            valueGroup.setVisible(true);
            gameOverGroup.setVisible(false);
            resultLabel.setText("");

            makeHandVisiblePlayer();
            makeHandVisibleDealer();
            updateBalanceLabel();
            updateHandValue();

            if (player.getScore() == 21) {
                standButton();
            }
        } else {
            System.out.println("Invalid Bet!");
        }
    }

    @FXML
    protected void onPlayAgainClicked() {
        tempBetAmount = 0;
        updateCurrentBet();
        betHistory.clear();
        betStack.getChildren().clear();

        resultLabel.setText("");
        resultLabel.setVisible(false);
        dealerHandBox.getChildren().clear();
        playerHandBox.getChildren().clear();

        gameOverGroup.setVisible(false);
        actionGroup.setVisible(false);
        valueGroup.setVisible(false);

        bettingGroup.setVisible(true);
        updateBalanceLabel();

    }

    private void makeHandVisiblePlayer() {
        playerHandBox.getChildren().clear();
        for (Card c : player.getHand().getHandCards()) {
            playerHandBox.getChildren().add(createCardImageView(c.getImageName()));
        }
    }

    private void makeHandVisibleDealer() {
        dealerHandBox.getChildren().clear();
        boolean roundIsOver = gameOverGroup.isVisible();

        int i = 0;
        for (Card c : dealer.getHand().getHandCards()) {
            if (i == 1 && !roundIsOver) {
                dealerHandBox.getChildren().add(createCardImageView("back.png"));
            } else {
                dealerHandBox.getChildren().add(createCardImageView(c.getImageName()));
            }
            i++;
        }
    }

    private ImageView createCardImageView(String imageName) {
        String path = "/at/ac/hcw/blackjack_b_plus_plus/images/cards/" + imageName;
        if (getClass().getResourceAsStream(path) == null) path = "/at/ac/hcw/blackjack_b_plus_plus/images/" + imageName;
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            ImageView view = new ImageView(img);
            view.setFitWidth(65);
            view.setPreserveRatio(true);
            return view;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    private void setupChipEvent(ImageView chipView, int value) {
        chipView.setOnMouseClicked(event -> {
            int currentBal = (player != null) ? player.getBalance() : 1000;
            if ((currentBal - tempBetAmount) >= value) {
                tempBetAmount += value;
                betHistory.push(value);
                addChipToTableVisual(value);
            }
            updateCurrentBet();
        });
    }

    private void addChipToTableVisual(int value) {
        String imagePath = "/at/ac/hcw/blackjack_b_plus_plus/images/" + value + "_chip.png";
        try {
            ImageView placedChip = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            placedChip.setFitWidth(110);
            placedChip.setPreserveRatio(true);
            placedChip.setOnMouseClicked(e -> {
                e.consume();
                removeTopChip();
            });
            betStack.getChildren().add(placedChip);
            updateCurrentBet();
        } catch (Exception e) {
        }
    }

    @FXML
    protected void onAllInClicked() {
        onClearBetClicked();
        int rem = (player != null) ? player.getBalance() : 1000;
        while (rem >= 500) {
            addChipInternal(500);
            rem -= 500;
        }
        while (rem >= 200) {
            addChipInternal(200);
            rem -= 200;
        }
        while (rem >= 100) {
            addChipInternal(100);
            rem -= 100;
        }
        while (rem >= 50) {
            addChipInternal(50);
            rem -= 50;
        }
    }

    private void addChipInternal(int val) {
        tempBetAmount += val;
        betHistory.push(val);
        addChipToTableVisual(val);
    }

    @FXML
    protected void onClearBetClicked() {
        tempBetAmount = 0;
        betHistory.clear();
        betStack.getChildren().clear();
        updateCurrentBet();
    }

    private void removeTopChip() {
        if (!betHistory.isEmpty()) {
            tempBetAmount -= betHistory.pop();
            if (!betStack.getChildren().isEmpty()) betStack.getChildren().remove(betStack.getChildren().size() - 1);
        }
        updateCurrentBet();
    }

    public void updateBalanceLabel() {
        if (player != null) playerBalanceLabel.setText("Balance: " + player.getBalance() + "$");
    }

    public void switchToStartMenu() {
        rulesPane1.setVisible(false);
        rulesPane2.setVisible(false);
        menuPane.setVisible(true);
        gamePane.setVisible(false);
    }

    public void switchToRulesMenu() {
        menuPane.setVisible(false);
        rulesPane1.setVisible(true);
    }

    public void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    public void updateCurrentBet() {
        playerBetLabel.setText("Current Bet: " + tempBetAmount);
        playerBetLabel2.setText("Current Bet: " + tempBetAmount);
    }

    public void updateHandValue() {
        playerHandValue.setText("Player Hand: \n" + player.getValue());
        dealerHandValue.setText("Dealer Hand: \n" + (dealer.getValue() - dealer.getHand().getCardValue(1)));
    }

    public void updateDealerSkin() {
        if (currentDealerSkin <= countDealerSkins() && currentDealerSkin > 0) {
            String path = "/at/ac/hcw/blackjack_b_plus_plus/images/dealer_skins/dealer_" + currentDealerSkin + ".png";
            Image newSkin = new Image(getClass().getResourceAsStream(path));

            dealer_skin.setFitWidth(1200);
            dealer_skin.setFitHeight(1200);
            dealer_skin.setPreserveRatio(true);
            dealer_skin.setImage(newSkin);
        }
    }

    private int countDealerSkins() {
        int count = 0;
        while (true) {
            String path = "/at/ac/hcw/blackjack_b_plus_plus/images/dealer_skins/dealer_" + (count + 1) + ".png";
            if (getClass().getResource(path) != null) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}