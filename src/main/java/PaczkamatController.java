/**
 * Sample Skeleton for 'paczkamatFX.fxml' Controller Class
 */

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mapy.Customer;
import mapy.Order;
import mapy.Paczkamat;
import mapy.Stash;

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

    private PaczkamatService service;

    private List<Paczkamat> paczkamats = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Stash> stashes = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    private Customer loggedUser = null;



    /*
        This method should be called only once
        independent of user flow. It is needed to make
        real connection with remote server.
        Given login and password must be secret and cannot be stored in table.
        Beside this, there is separate login for normal user and we check
        his identity based on list of users returned in this method at the beginning.
     */
    @FXML
    void onLoginClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        service = new PaczkamatService(login, password);
        loginButton.setDisable(true);
        loginField.setText("");
        passwordField.setText("");

        paczkamats = service.getAllPaczkamats();
        orders = service.getAllOrders();
        stashes = service.getAllStashes();
        customers = service.getAllCustomers();

        textArea.setText(paczkamats.get(0).getAddress());
//        System.out.println(orders.get(0).getOrderStatus());
//        System.out.println(stashes.get(0).getDimension());
//        System.out.println(customers.get(0).getEmail());


        //create connection for a server installed in localhost, with a user "root" with no password
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://dbpaczkamat.clabexdogems.us-east-1.rds.amazonaws.com", login, password)) {
//            System.out.println("success");
//            try (Statement stmt = conn.createStatement()) {
//                //execute query
//                try (ResultSet rs = stmt.executeQuery("SELECT * from paczkamatDB.paczkamats;")) {
//                    //position result to first
//                    rs.next();
//                    String test = (rs.getString(1) + "|\t" + rs.getString(2)); //result is "Hello World!"
//                    textArea.setText(test);
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }

    @FXML
    void onUserLoginClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        loggedUser = service.getLoggedInUser(login, password);
        if (loggedUser == null) {
            System.out.println("Invalid login or password!");
        } else {
            System.out.println(loggedUser.getName() + " " + loggedUser.getLastName() + " logged in!");
        }

    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'paczkamatFX.fxml'.";

    }
}
