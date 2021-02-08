package controllers;

import entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.DataSource;

import java.io.IOException;
import java.util.Objects;

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
    private Text statusMsg;

    private void showNewlayout(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
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

       if(emailField.getText().trim().isEmpty()){
           statusMsg.setText("Please provide Your e-mail address.");
           return;
       }
        if(nameField.getText().trim().isEmpty()){
            statusMsg.setText("What's Your name?");
            return;
        }
        if(lastNameField.getText().trim().isEmpty()){
            statusMsg.setText("Please enter Your last name.");
            return;
        }
        if(phoneNumberField.getText().trim().isEmpty()){
            statusMsg.setText("Please honor us with Your phone number.");
            return;
        }
        if(loginField.getText().trim().isEmpty()){
            statusMsg.setText("Login cannot be empty!");
            return;
        }
        if (repeatPasswordField.getText().equals(passwordField.getText()) && !passwordField.getText().trim().isEmpty() ) {
            newCustomer.setPassword(passwordField.getText());
        } else {
            statusMsg.setText("Password doesn't match repeated password or You simply did not input it!");
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
