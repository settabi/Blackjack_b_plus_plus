package at.ac.hcw.blackjack_b_plus_plus.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RulesController {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnNext; // Pfeil nach rechts

    @FXML
    public void initialize() {

        // CLOSE -> Menü
        btnClose.setOnMouseClicked(event -> {
            switchScene("/at/ac/hcw/blackjack_b_plus_plus/menu-view.fxml");
        });

        // NEXT -> Gehe zu SEITE 2 (rules-page2.fxml)
        btnNext.setOnMouseClicked(event -> {
            switchScene("/at/ac/hcw/blackjack_b_plus_plus/rules-page2.fxml");
        });

    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
            scene.setFill(Color.BLACK);

            Stage stage = (Stage) btnClose.getScene().getWindow(); // oder btnNext, ist egal
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}