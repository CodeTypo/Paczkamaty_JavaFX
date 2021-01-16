/**
 * Sample Skeleton for 'paczkamatFX.fxml' Controller Class
 */

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PaczkamatController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="loginField"
    private TextField loginField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML
    private TextArea textArea;

    @FXML
    void onLoginClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://dbpaczkamat.clabexdogems.us-east-1.rds.amazonaws.com", login, password)) {
            System.out.println("success");
            try (Statement stmt = conn.createStatement()) {
                //execute query
                try (ResultSet rs = stmt.executeQuery("SELECT * from paczkamatDB.paczkamats;")) {
                    //position result to first
                    rs.next();
                    String test = (rs.getString(1) + "|\t" + rs.getString(2)); //result is "Hello World!"
                    textArea.setText(test);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'paczkamatFX.fxml'.";

    }
}
