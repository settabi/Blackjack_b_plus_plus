module at.ac.hcw.blackjack_b_plus_plus {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.ac.hcw.blackjack_b_plus_plus to javafx.fxml;
    exports at.ac.hcw.blackjack_b_plus_plus;
    exports at.ac.hcw.blackjack_b_plus_plus.controller;
    opens at.ac.hcw.blackjack_b_plus_plus.controller to javafx.fxml;
}