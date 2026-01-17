package at.ac.hcw.blackjack_b_plus_plus.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color; // WICHTIG: Neuer Import!
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private ImageView btnRules;

    @FXML
    private ImageView btnExit;

    @FXML
    private ImageView btnStart;

    @FXML
    public void initialize() {

        // 1. Logik für Exit-Button
        btnExit.setOnMouseClicked(event -> {
            Platform.exit();
            System.exit(0);
        });

        // 2. Logik für den Rules-Button
        btnRules.setOnMouseClicked(event -> {
            try {
                // Wir laden die neue FXML Datei
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/hcw/blackjack_b_plus_plus/rules-view.fxml"));

                Scene rulesScene = new Scene(fxmlLoader.load(), 1280, 800);

                // --- HIER IST DER TRICK ---
                // Hintergrund auf Schwarz setzen, damit es nicht weiß blitzt
                rulesScene.setFill(Color.BLACK);

                // Das aktuelle Fenster (Stage) holen
                Stage stage = (Stage) btnRules.getScene().getWindow();

                // Die Szene wechseln
                stage.setScene(rulesScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}