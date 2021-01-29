package controllers;

import entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.DataSource;
import services.SessionStore;

import java.io.IOException;

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
    private Text msgText;

    private void showNewlayout(String path, ActionEvent event) {
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
    void onLoginBtnClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = loginField.getText();

        if (login.equals("admin") && password.equals("admin")) {
            SessionStore.setAdmin(true);
            showNewlayout("layout/admin_screen.fxml", event);
        } else {
            try {
                Customer loggedUser = DataSource.getLoggedUser(login, password);
                SessionStore.setUser(loggedUser);
                showNewlayout("layout/customer_screen.fxml", event);
            } catch (Exception e) {
                msgText.setText("Invalid login or password!");
                msgText.setVisible(true);
            }
            
            //                sendPackageSize.setItems(stashSizes);
//                setupWebView(sendWebView, "customer_map.html");

        }



    }

    @FXML
    void onRegisterBtnClicked(ActionEvent event) {

    }

}
