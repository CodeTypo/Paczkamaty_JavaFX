package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    void onLoginBtnClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = loginField.getText();



    }

    @FXML
    void onRegisterBtnClicked(ActionEvent event) {

    }

}
