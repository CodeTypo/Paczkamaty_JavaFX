import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import netscape.javascript.JSObject;

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

    @FXML
    private WebView sendWebView;

    @FXML
    public WebView addPaczkamatWebView;

    @FXML // fx:id="sendTab"
    private Tab sendTab; // Value injected by FXMLLoader

    @FXML // fx:id="sendSenderDetails"
    private TextField sendSenderDetails; // Value injected by FXMLLoader

    @FXML // fx:id="sendRecipientDetails"
    private TextField sendRecipientDetails; // Value injected by FXMLLoader

    @FXML // fx:id="sendPackageSize"
    private ChoiceBox<String> sendPackageSize; // Value injected by FXMLLoader

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



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONTROLLER CLASS VARIABLES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private PaczkamatService service;

    private List<Paczkamat> paczkamats = new ArrayList<>();
    private List<Order> order     = new ArrayList<>();
    private List<Stash> stashes    = new ArrayList<>();
    private List<Customer> customers  = new ArrayList<>();

    ObservableList<String> stashSizes = FXCollections.observableArrayList
            ("Small","Medium", "Large");

    private Customer loggedUser = null;
    private Console  console;

    // These two objects are exposed to javascript in window
    // It is the simplest way to get access to java object within javascript
    private Order newOrder = new Order();
    private Paczkamat newPaczkamat = new Paczkamat();


    /*
        This method should be called only once
        independent of user flow. It is needed to make
        real connection with remote server.
        Given login and password must be secret and cannot be stored in table.
        Beside this, there is separate login for normal user and we check
        his identity based on list of users returned in this method at the beginning.
     */
    @FXML
    void onLoginClicked() {
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();

        service = new PaczkamatService(login, password);
        loginButtonDBLogin.setDisable(true);
        loginLoginField.setText("");
        loginPasswordField.setText("");

        paczkamats  = service.getAllPaczkamats();
        order      = service.getAllOrders();
        stashes     = service.getAllStashes();
        customers   = service.getAllCustomers();

        loginTextArea.setText(customers.get(0).getName());

    }

    private void enable(Tab tab) {
        tab.setDisable(false);
        tab.getStyleClass().add("tab-enabled");
    }

    @FXML
    void onUserLoginClicked() {
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();
        System.out.println(login);
        System.out.println(password);

        if (login.equals("admin") && password.equals("admin")){
            enable(adminTab);
        } else {
            loggedUser = service.getLoggedInUser(login, password);
            if (loggedUser == null) {
                System.out.println("Invalid login or password!");
            } else {
                System.out.println(loggedUser.getName() + " " + loggedUser.getLastName() + " logged in!");
                enable(statusTab);
                enable(sendTab);
            }

        }
    }


    public void close(javafx.scene.input.MouseEvent mouseEvent) {
        ((Stage)(((HBox)mouseEvent.getSource()).getScene().getWindow())).close();
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
        //Console config

        /* Uncomment in final version, for now I need verbose debugging in IntelliJ Window
        this.console = new Console(sharedConsoleLog);
        PrintStream ps = new PrintStream(new Console(sharedConsoleLog));
        System.setOut(ps);
        System.setErr(ps);
        */
        statusTab.setDisable(true);
        sendTab.setDisable(true);
        adminTab.setDisable(true);

        //End of console config

        disable(adminTab); disable(statusTab); disable(sendTab);

        sendPackageSize.setItems(stashSizes);

        sendWebView.getEngine().load( getClass().getResource("/web.html").toString() );
        addPaczkamatWebView.getEngine().load(getClass().getResource("/web.html").toString());

        sendWebView.getEngine().getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != Worker.State.SUCCEEDED) { return; }

                    JSObject window = (JSObject) sendWebView.getEngine().executeScript("window");
                    window.setMember("newOrder", newOrder);
        });

        addPaczkamatWebView.getEngine().getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != Worker.State.SUCCEEDED) { return; }

                    JSObject window = (JSObject) sendWebView.getEngine().executeScript("window");
                    window.setMember("service", service);
                });
    }

    private void disable(Tab tab) {
        tab.getStyleClass().add("tab-disabled");
    }
}
