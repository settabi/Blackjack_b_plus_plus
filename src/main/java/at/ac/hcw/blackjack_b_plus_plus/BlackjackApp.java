package at.ac.hcw.blackjack_b_plus_plus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackjackApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackApp.class.getResource("menu-view.fxml"));

        // Deine Wunsch-Größe für das Fenster
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        stage.setTitle("Blackjack B++");

        // 1. Start-Zustand: Fenster ist "abgeschlossen" (man kann den Rand nicht ziehen)
        stage.setResizable(false);

        // 2. Tasten-Steuerung (F für Fullscreen)
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F) {
                if (!stage.isFullScreen()) {
                    // TRICK: Bevor wir in Fullscreen gehen, erlauben wir das Vergrößern!
                    // Sonst bekommen wir die hässlichen schwarzen Balken.
                    stage.setResizable(true);
                    stage.setFullScreen(true);
                } else {
                    stage.setFullScreen(false);
                }
            }
        });

        // 3. Automatische Überwachung (Für Taste F und auch ESC)
        // Sobald der Fullscreen verlassen wird, schließen wir das Fenster wieder ab.
        stage.fullScreenProperty().addListener((obs, wasFull, isFull) -> {
            if (!isFull) {
                // Wir sind wieder im Fenstermodus -> SOFORT wieder sperren!
                stage.setResizable(false);

                // Fenster wieder perfekt an den Inhalt anpassen und zentrieren
                stage.sizeToScene();
                stage.centerOnScreen();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}