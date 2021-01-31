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

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Text statusMsg;

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
        showNewlayout("layout/login_screen.fxml", event);
    }

    @FXML
    void onRegisterBtnClicked(ActionEvent event) {
        Customer newCustomer = new Customer();

        if (repeatPasswordField.getText().equals(passwordField.getText())) {
            newCustomer.setPassword(passwordField.getText());
        } else {
            statusMsg.setText("Password doesn't match repeated password!");
            return;
        }

        newCustomer.setEmail(emailField.getText());
        newCustomer.setPhoneNumber(phoneNumberField.getText());
        newCustomer.setName(nameField.getText());
        newCustomer.setLastName(lastNameField.getText());
        newCustomer.setLogin(loginField.getText());

        DataSource.addCustomer(newCustomer);
        System.out.println("New customer added, login:  " + newCustomer.getLogin() + " password: " + newCustomer.getPassword());
        showNewlayout("layout/login_screen.fxml", event);
    }

}
