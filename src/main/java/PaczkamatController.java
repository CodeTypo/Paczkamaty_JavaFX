import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //LOGIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SEND TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML // fx:id="loginTextArea"
    private TextArea loginTextArea;
    @FXML // fx:id="sharedConsoleLog"
    private TextArea sharedConsoleLog;
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

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //SEND TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ STATUS TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML // fx:id="sendPackageSize"
    private ChoiceBox<String> sendPackageSize; // Value injected by FXMLLoader
    @FXML
    private ChoiceBox<?> recipientChooser;
    @FXML
    private ChoiceBox<?> paczkamatChooser;
    @FXML // fx:id="statusTab"
    private Tab statusTab; // Value injected by FXMLLoader
    @FXML // fx:id="StatusCheckPckgNumberInput"
    private TextField StatusCheckPckgNumberInput; // Value injected by FXMLLoader
    @FXML // fx:id="StatusCheckCheckButton"
    private Button StatusCheckCheckButton; // Value injected by FXMLLoader

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //STATUS TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ADMIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML // fx:id="StatusCheckTextArea"
    private TextArea StatusCheckTextArea; // Value injected by FXMLLoader

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //ADMIN TAB ELEMENTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @FXML // fx:id="adminTab"
    private Tab adminTab; // Value injected by FXMLLoader
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONTROLLER CLASS VARIABLES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private PaczkamatService service;
    private List<Paczkamat> paczkamats = new ArrayList<>();
    private List<Order> order = new ArrayList<>();
    private List<Stash> stashes = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Customer loggedUser = null;
    private Console console;

    ObservableList<String> stashSizes = FXCollections.observableArrayList
            ("Small", "Medium", "Large");

    private WebViewConnector webViewConnector;// = new WebViewConnector();


    @FXML
    void onDBLoginClicked() {
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();

        service = new PaczkamatService(login, password);
        webViewConnector = new WebViewConnector(service);

        loginButtonDBLogin.setDisable(true);
        loginLoginField.setText("");
        loginPasswordField.setText("");

        paczkamats = service.getAllPaczkamats();
        order = service.getAllOrders();
        stashes = service.getAllStashes();
        customers = service.getAllCustomers();
    }

    @FXML
    void onUserLoginClicked() {
        String login = loginLoginField.getText();
        String password = loginPasswordField.getText();

        if (login.equals("admin") && password.equals("admin")) {
            enable(adminTab);
            setupWebView(addPaczkamatWebView, "admin_map.html");
        } else {
            loggedUser = service.getLoggedInUser(login, password);
            if (loggedUser == null) {
                loginTextArea.setText("Invalid login or password!");
            } else {
                loginTextArea.setText("Witamy na pokładzie " + customers.get(0).getName() + " " + loggedUser.getLastName());

                enable(statusTab);
                enable(sendTab);

                sendPackageSize.setItems(stashSizes);
                setupWebView(sendWebView, "customer_map.html");
            }
        }
    }

    void setupWebView(WebView webView, String htmlFile) {
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("app", webViewConnector);
                });

        webEngine.load(getClass().getResource("/webview/" + htmlFile).toString());
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        statusTab.setDisable(true);
        sendTab.setDisable(true);
        adminTab.setDisable(true);

        /* Uncomment in final version, for now I need verbose debugging in IntelliJ Window

        //Console config
        this.console = new Console(sharedConsoleLog);
        PrintStream ps = new PrintStream(new Console(sharedConsoleLog));
        System.setOut(ps);
        System.setErr(ps);
        //End of console config

        */

        // These lines are problematic
        // Because it looks like there is no tabs between when you log in as admin
//        disable(adminTab);
//        disable(statusTab);
//        disable(sendTab);
    }

    private void disable(Tab tab) {
        tab.getStyleClass().add("tab-disabled");
    }

    private void enable(Tab tab) {
        tab.setDisable(false);
        tab.getStyleClass().add("tab-enabled");
    }

    public void close(javafx.scene.input.MouseEvent mouseEvent) {
        ((Stage) (((HBox) mouseEvent.getSource()).getScene().getWindow())).close();
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
            appendText(String.valueOf((char) b));
        }
    }


}
