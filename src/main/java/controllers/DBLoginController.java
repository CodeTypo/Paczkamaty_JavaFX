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

/**
 * Kontroler do ekranu logowania, obsługuje dwa pola przypisane do połączonego z nim layoutu.
 */
// This screen appears as soon as we launch the app
public class DBLoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;


    /**
     * @param path Ścieżka do pliku .fxml zawierającego layout do nowego okna, które chcemy pokazać
     * @param event obiekt klasy ActionEvent, dzięki któremu mamy możliwość ukrycia layoutu, w którym znajdował się przycisk
     * który wywołał tą metodę.
     */
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

    /**
     * @param event Metoda wywoływana w momencie przyciśnięcia przycisku MockDB, pwoduje przełączenie źródła danych
     * na zhardocoe'owaną bazę danych wbuowaną w program celem ułatwienia pracy nad aplikacją bez podłączenia do bazy danych
     */
    @FXML
    void onMockClicked(MouseEvent event) {
        DataSource.setMockService();
        showNewLayout("layout/login_screen.fxml", event);
    }

    /**
     * @param event
     * Metoda która pozwala na zalogowanie się do bazy danych, wywołuje metodę setDBService klasy DataSource
     * powodując ustawienie źródła danych na DB i wykonuje próbę połączenia się z nią za pomocą Hibernate.
     */
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
