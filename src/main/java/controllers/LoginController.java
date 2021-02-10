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
import java.util.Objects;

/**
 * Klasa kontroler obsługująca logowanie do zhardcode'owanej w programie bazy testowej MockDB
 */
public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private Text msgText;

    @FXML
    void initialize(){
        registerBtn.setVisible(false);
    }

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
     * Metoda wywoływana w momencie naciśnięcia przez użytkownika przycisku Login, weryfikuje czy dane przez niego
     * wprowadzone są powiązane z jakimkolwiek kontem w bazie, jeżeli tak - loguje użytkownika i zmienia layout,
     * jeżeli nie - wyświetla stosowny komunikat i umożliwa przejście do ekranu rejestracji.
     */
    @FXML
    void onLoginBtnClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.equals("admin") && password.equals("admin")) {
            SessionStore.setAdmin(true);
            SessionStore.setLoggedIn(true);
            showNewlayout("layout/admin_screen.fxml", event);
        } else {
            try {
                Customer loggedUser = DataSource.getLoggedUser(login, password);
                SessionStore.setUser(loggedUser);
                System.out.println(loggedUser);

                SessionStore.setLoggedIn(true);
                showNewlayout("layout/customer_screen.fxml", event);
            } catch (Exception e) {
                msgText.setText("Invalid login or password!");
                msgText.setVisible(true);
                registerBtn.setVisible(true);
            }
        }
    }

    /**
     * @param event
     * Po wciśnięciu przycisku register, przechodzi do nowego ekranu, który umożliwia rejestrację nowego użytkownika,
     */
    @FXML
    void onRegisterBtnClicked(ActionEvent event) {
        showNewlayout("layout/register_screen.fxml", event);
    }
}
