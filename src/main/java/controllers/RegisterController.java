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

/**
 * Klasa obsługująca rejestrowanie nowego użytkownika w bazie danych
 */
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

    /**
     * @param path Ścieżka do pliku .fxml zawierającego layout do nowego okna, które chcemy pokazać
     * @param event obiekt klasy ActionEvent, dzięki któremu mamy możliwość ukrycia layoutu, w którym znajdował się przycisk
     * który wywołał tą metodę.
     */
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

    /**
     * @param event
     * Obsługa przycisku Login, który pozwala na powrót do ekranu logowania w przypadku, gdy użytkownik nie planuje
     * jednak zakładać nowego konta w bazie.
     */
    @FXML
    void onLoginBtnClicked(ActionEvent event) {
        showNewlayout("layout/login_screen.fxml", event);
    }

    /**
     * @param event
     * Metoda, która wywoływana jest po naciśnięciu na przycisk Register.
     * Weryfikuje poprawność danych wprowadzonych przez użytkownika; jeżeli wykryje gdzieś błąd - wyświetla o tym komunikat.
     * Jeżeli weryfikacja przebiegnie pomyślnie, dodaje nowy obiekt klasy Customer do bazy danych i przekierowuje
     * użytkownika do ekranu logowania, gdzie może on już zalogować się przy użyciu nowo utworzonego konta.
     */
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
