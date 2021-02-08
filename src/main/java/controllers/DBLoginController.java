package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.DataSource;

import java.io.IOException;

// This screen appears as soon as we launch the app
public class DBLoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private void showNewLayout(String path, Event event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMockClicked(MouseEvent event) {
        DataSource.setMockService();
        showNewLayout("layout/login_screen.fxml", event);
    }

    @FXML
    void onLoginBtnClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        DataSource.setDBService(login, password);
        showNewLayout("layout/login_screen.fxml", event);
    }

    @FXML
    void initialize() { }
}
