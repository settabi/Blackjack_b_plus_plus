package at.ac.hcw.blackjack_b_plus_plus.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RulesPage2Controller {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnBack; // Pfeil nach links

    @FXML
    public void initialize() {

        // CLOSE -> Zurück zum Hauptmenü
        btnClose.setOnMouseClicked(event -> {
            switchScene("/at/ac/hcw/blackjack_b_plus_plus/menu-view.fxml");
        });

        // BACK -> Zurück zur Seite 1 (Rules View)
        btnBack.setOnMouseClicked(event -> {
            switchScene("/at/ac/hcw/blackjack_b_plus_plus/rules-view.fxml");
        });
    }

    // Hilfsmethode für den Szenenwechsel (mit Anti-Blitz-Trick)
    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
            scene.setFill(Color.BLACK); // Kein weißes Aufblitzen

            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Laden von: " + fxmlPath);
        }
    }
}