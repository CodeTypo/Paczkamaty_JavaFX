/**
 * Sample Skeleton for 'paczkamatFX.fxml' Controller Class
 */

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mapy.Customer;
import mapy.Order;
import mapy.Paczkamat;
import mapy.Stash;

public class PaczkamatController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ LOGIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML // fx:id="loginTab"
    private Tab loginTab; // Value injected by FXMLLoader

    @FXML // fx:id="loginLoginField"
    private TextField loginLoginField;

    @FXML // fx:id="loginloginPasswordField"
    private PasswordField loginPasswordField;

    @FXML // fx:id="loginButtonDBLogin"
    private Button loginButtonDBLogin;

    @FXML // fx:id="loginButtonCustomerLogin"
    private Button loginButtonCustomerLogin;

    @FXML // fx:id="loginTextArea"
    private TextArea loginTextArea;

    @FXML // fx:id="sharedConsoleLog"
    private TextArea sharedConsoleLog;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //LOGIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SEND TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @FXML // fx:id="sendTab"
    private Tab sendTab; // Value injected by FXMLLoader

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //SEND TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ STATUS TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @FXML // fx:id="statusTab"
    private Tab statusTab; // Value injected by FXMLLoader

    @FXML // fx:id="StatusCheckPckgNumberInput"
    private TextField StatusCheckPckgNumberInput; // Value injected by FXMLLoader

    @FXML // fx:id="StatusCheckCheckButton"
    private Button StatusCheckCheckButton; // Value injected by FXMLLoader

    @FXML // fx:id="StatusCheckTextArea"
    private TextArea StatusCheckTextArea; // Value injected by FXMLLoader

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //STATUS TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ADMIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @FXML // fx:id="adminTab"
    private Tab adminTab; // Value injected by FXMLLoader

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //ADMIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




    private PaczkamatService service;

    private List<Paczkamat> paczkamats = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Stash> stashes = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    private Customer loggedUser = null;
    private Console console;


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
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();

        service = new PaczkamatService(login, password);
        loginButtonDBLogin.setDisable(true);
        loginLoginField.setText("");
        loginPasswordField.setText("");

        paczkamats = service.getAllPaczkamats();
        orders = service.getAllOrders();
        stashes = service.getAllStashes();
        customers = service.getAllCustomers();

        loginTextArea.setText(paczkamats.get(0).getAddress());

        statusTab.setDisable(false);
        sendTab.setDisable(false);

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
//                    loginTextArea.setText(test);
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }

    @FXML
    void onUserLoginClicked(ActionEvent event) {
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();

        loggedUser = service.getLoggedInUser(login, password);
        if (loggedUser == null) {
            System.out.println("Invalid login or password!");
        } else {
            System.out.println(loggedUser.getName() + " " + loggedUser.getLastName() + " logged in!");
        }

    }





    //Klasa która pozwala na wykorzystanie textFieldu jako console output, dzięki czemu można wyświetlać w nim logi
    //Przy pomocy sout

    private static class Console extends OutputStream {
        private final TextArea console;
        public Console(TextArea console) {
            this.console = console;
        }
        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }
        public void write(int b) {
            appendText(String.valueOf((char)b));
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert sharedConsoleLog             != null : "fx:id=\"consoleLog\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginLoginField              != null : "fx:id=\"loginLoginField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginPasswordField           != null : "fx:id=\"loginPasswordField\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginButtonDBLogin           != null : "fx:id=\"loginButtonDBLogin\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginButtonCustomerLogin     != null : "fx:id=\"loginButtonCustomerLogin\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert sendTab                      != null : "fx:id=\"sendTab\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert statusTab                    != null : "fx:id=\"statusTab\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert adminTab                     != null : "fx:id=\"adminTab\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert loginTab                     != null : "fx:id=\"loginTab\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert StatusCheckPckgNumberInput   != null : "fx:id=\"StatusCheckPckgNumberInput\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert StatusCheckCheckButton       != null : "fx:id=\"StatusCheckCheckButton\" was not injected: check your FXML file 'paczkamatFX.fxml'.";
        assert StatusCheckTextArea          != null : "fx:id=\"StatusCheckTextArea\" was not injected: check your FXML file 'paczkamatFX.fxml'.";

        //Console config
        this.console = new Console(sharedConsoleLog);
        PrintStream ps = new PrintStream(new Console(sharedConsoleLog));
        System.setOut(ps);
        System.setErr(ps);
        statusTab.setDisable(true);
        sendTab.setDisable(true);
        adminTab.setDisable(true);
        //End of console config


    }
}
